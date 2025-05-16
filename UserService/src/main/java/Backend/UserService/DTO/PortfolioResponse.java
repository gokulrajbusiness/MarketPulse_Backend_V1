package Backend.UserService.DTO;


import Backend.UserService.Model.AlertType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PortfolioResponse {
    private String stockSymbol;
    private AlertType conditionType;
    private Double targetPrice;
}

