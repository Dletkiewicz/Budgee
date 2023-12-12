package pl.budgee.domain.port;

import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.Expense;
import pl.budgee.domain.model.Expense.ExpenseId;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository {

  Expense save(Expense expense);

  void delete(BudgetId budgetId, ExpenseId id);

  Optional<Expense> findOneById(BudgetId budgetId, ExpenseId id);

  List<Expense> findAll(BudgetId budgetId);
}
