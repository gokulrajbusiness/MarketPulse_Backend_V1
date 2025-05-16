package Backend.TrackerService.Service;

import Backend.TrackerService.Model.StockPrice;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Primary
public class SmartStockPriceService implements StockPriceService {

    private final RealStockPriceService realService;
    private final MockStockPriceService mockService;

    private volatile boolean useMock = false;

    public SmartStockPriceService(RealStockPriceService real, MockStockPriceService mock) {
        this.realService = real;
        this.mockService = mock;
    }

    @Override
    public Flux<StockPrice> getPrices() {
        if (useMock) return mockService.getPrices();

        return realService.getPrices()
                .onErrorResume(ex -> {
                    System.err.println("⚠️ Switched to mock due to: " + ex.getMessage());
                    useMock = true;
                    return mockService.getPrices();
                });
    }

    @Scheduled(cron = "0 0 0 * * ?") // Every midnight
    public void resetToReal() {
        useMock = false;
        System.out.println("🔄 Resetting to real API mode");
    }
}

