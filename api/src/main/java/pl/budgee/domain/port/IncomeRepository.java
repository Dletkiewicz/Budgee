package pl.budgee.domain.port;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.Income;
import pl.budgee.domain.model.Income.IncomeId;

import java.util.Optional;

public interface IncomeRepository {

  Income save(Income income);

  void delete(BudgetId budgetId, IncomeId id);

  Optional<Income> findOneById(BudgetId budgetId, IncomeId id);

  Slice<Income> findAll(BudgetId budgetId, Pageable pageable);
}
