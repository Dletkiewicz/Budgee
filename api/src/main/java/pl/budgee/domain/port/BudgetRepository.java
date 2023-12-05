package pl.budgee.domain.port;

import pl.budgee.domain.model.Budget;

public interface BudgetRepository {

  Budget save(Budget budget);
}
