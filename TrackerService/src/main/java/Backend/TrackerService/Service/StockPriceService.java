package Backend.TrackerService.Service;

import Backend.TrackerService.Model.StockPrice;
import reactor.core.publisher.Flux;

public interface StockPriceService {
    Flux<StockPrice> getPrices();
}
