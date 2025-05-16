package Backend.UserService.Controller;


import Backend.UserService.DTO.PortfolioRequest;
import Backend.UserService.DTO.PortfolioResponse;
import Backend.UserService.Model.PortfolioItem;
import Backend.UserService.Model.User;
import Backend.UserService.Repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioRepository portfolioRepo;

    @PostMapping
    public ResponseEntity<String> addStockToPortfolio(@AuthenticationPrincipal User user,
                                                      @RequestBody PortfolioRequest request) {
        PortfolioItem item = new PortfolioItem();
        item.setUser(user);
        item.setStockSymbol(request.getStockSymbol());
        item.setConditionType(request.getConditionType());
        item.setTargetPrice(request.getTargetPrice());
        portfolioRepo.save(item);

        return ResponseEntity.ok("Stock added to portfolio.");
    }

    @GetMapping
    public ResponseEntity<List<PortfolioResponse>> getPortfolio(@AuthenticationPrincipal User user) {
        List<PortfolioItem> items = portfolioRepo.findByUser(user);
        List<PortfolioResponse> response = items.stream().map(item ->
                new PortfolioResponse(
                        item.getStockSymbol(),
                        item.getConditionType(),
                        item.getTargetPrice()
                )
        ).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}

