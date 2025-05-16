package Backend.UserService.DTO;

import Backend.UserService.Model.AlertType;
import lombok.Data;



@Data
public class PortfolioRequest {
    private String stockSymbol;
    private AlertType conditionType; // GT, LT, EQ
    private Double targetPrice;
}
