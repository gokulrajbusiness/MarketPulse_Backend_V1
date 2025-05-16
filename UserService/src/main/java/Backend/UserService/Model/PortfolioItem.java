package Backend.UserService.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "portfolio")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stockSymbol;

    @Enumerated(EnumType.STRING)
    private AlertType conditionType;  // GT, LT, EQ

    private Double targetPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}

