package pl.budgee.domain.port;

import jakarta.transaction.Transactional;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.Income;
import pl.budgee.domain.model.Income.IncomeId;

import java.util.List;
import java.util.Optional;

public interface IncomeRepository {

  Income save(Income income);

  void delete(BudgetId budgetId, IncomeId id);

  Optional<Income> findOneById(BudgetId budgetId, IncomeId id);

  List<Income> findAll(BudgetId budgetId);
}
