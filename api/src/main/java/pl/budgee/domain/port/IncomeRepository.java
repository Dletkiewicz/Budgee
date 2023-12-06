package pl.budgee.domain.port;

import jakarta.transaction.Transactional;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.Income;
import pl.budgee.domain.model.Income.IncomeId;

import java.util.Optional;

public interface IncomeRepository {

  Income save(Income income);

  void delete(IncomeId id);

  Optional<Income> findOneById(IncomeId id);
}
