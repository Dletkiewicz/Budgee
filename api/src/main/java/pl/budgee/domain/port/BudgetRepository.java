package pl.budgee.domain.port;

import pl.budgee.domain.model.Budget;

public interface BudgetRepository {

  void save(Budget budget);
}
