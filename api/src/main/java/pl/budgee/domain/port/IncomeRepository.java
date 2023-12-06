package pl.budgee.domain.port;

import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.Income;

public interface IncomeRepository {

  Income save(Income income);
}
