package Backend.TrackerService.Service;

import Backend.TrackerService.Model.StockPrice;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RealStockPriceService implements StockPriceService {

    private final WebClient webClient = WebClient.create("https://www.alphavantage.co");

    private final List<String> stockSymbols = List.of("IBM", "AAPL", "MSFT");

    @Value("${alphavantage.api.key}")
    private String apiKey;

    @Override
    public Flux<StockPrice> getPrices() {
        return Flux.interval(Duration.ofSeconds(15))
                .flatMap(i -> Flux.fromIterable(stockSymbols))
                .flatMap(this::fetchPrice);
    }

    private Flux<StockPrice> fetchPrice(String symbol) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/query")
                        .queryParam("function", "GLOBAL_QUOTE")
                        .queryParam("symbol", symbol)
                        .queryParam("apikey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    if (response.contains("\"Information\"")) {
                        return Mono.error(new RuntimeException("🔁 API limit hit"));
                    }
                    return parseStockPriceFromJson(response, symbol);
                })
                .flux();
    }

    private Mono<StockPrice> parseStockPriceFromJson(String json, String symbol) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode quote = root.path("Global Quote");

            if (quote.isMissingNode() || quote.get("05. price") == null) {
                return Mono.error(new RuntimeException("❌ Invalid or missing stock quote"));
            }

            StockPrice sp = new StockPrice();
            sp.setSymbol(quote.get("01. symbol").asText(symbol));
            sp.setPrice(Double.parseDouble(quote.get("05. price").asText()));
            sp.setTimestamp(Instant.now());

            return Mono.just(sp);
        } catch (Exception e) {
            return Mono.error(new RuntimeException("❌ Failed to parse stock price JSON", e));
        }
    }
}

