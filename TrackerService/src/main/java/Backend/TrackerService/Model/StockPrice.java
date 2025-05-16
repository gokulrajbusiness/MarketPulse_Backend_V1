package Backend.TrackerService.Model;

import lombok.Data;
import java.time.Instant;

@Data
public class StockPrice {
    private String symbol;
    private double price;
    private Instant timestamp;
}

