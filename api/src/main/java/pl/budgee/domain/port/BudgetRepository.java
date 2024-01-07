package pl.budgee.domain.port;

import pl.budgee.domain.model.Budget;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.User.UserId;

import java.math.BigDecimal;
import java.util.Optional;

public interface BudgetRepository {

  void save(Budget budget);

  Optional<Budget> findOneById(BudgetId id);

  Optional<Budget> findOneByUser(UserId id);

  void subtractBalance(Budget budget, BigDecimal amount);

  void addBalance(Budget budget, BigDecimal amount);
}
