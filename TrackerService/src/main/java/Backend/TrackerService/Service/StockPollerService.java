//package Backend.TrackerService.Service;
//
//
//import Backend.TrackerService.Model.StockPrice;
//import lombok.RequiredArgsConstructor;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import jakarta.annotation.PostConstruct;
//
//@Service
//@RequiredArgsConstructor
//public class StockPricePublisher {
//
//    private final StockPriceService stockPriceService;
//    private final KafkaTemplate<String, StockPrice> kafkaTemplate;
//
//    @PostConstruct
//    public void start() {
//        stockPriceService.getPrices()
//                .subscribe(stock -> {
//                    kafkaTemplate.send("price-updates", stock.getSymbol(), stock);
//                    System.out.println("📦 Published: " + stock);
//                });
//    }
//}
