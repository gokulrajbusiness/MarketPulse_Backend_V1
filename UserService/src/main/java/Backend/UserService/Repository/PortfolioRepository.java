package Backend.UserService.Repository;


import Backend.UserService.Model.PortfolioItem;
import Backend.UserService.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<PortfolioItem, Long> {
    List<PortfolioItem> findByUser(User user);
}

