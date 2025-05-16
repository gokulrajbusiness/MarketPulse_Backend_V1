package Backend.TrackerService.Service;


import Backend.TrackerService.Model.StockPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MockStockPriceService implements StockPriceService {

    private final Random random = new Random();
    private final List<String> symbols = List.of("IBM", "AAPL", "MSFT");

    @Override
    public Flux<StockPrice> getPrices() {
        return Flux.interval(Duration.ofSeconds(10))
                .flatMap(tick -> Flux.fromIterable(symbols)
                        .map(symbol -> {
                            StockPrice sp = new StockPrice();
                            sp.setSymbol(symbol);
                            sp.setPrice(100 + random.nextDouble() * 50);
                            sp.setTimestamp(Instant.now());
                            return sp;
                        }));
    }
}

