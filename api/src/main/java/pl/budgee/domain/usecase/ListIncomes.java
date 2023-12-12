package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.model.Income;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.IncomeRepository;

import java.util.List;

@RequiredArgsConstructor
public class ListIncomes {

  public record ListIncomesRequest(BudgetId budgetId) {
  }

  private final BudgetRepository budgets;
  private final IncomeRepository incomes;

  public List<Income> list(ListIncomesRequest request) {
    var budget = budgets.findOneById(request.budgetId()).orElseThrow(() -> new BudgetNotFoundException(request.budgetId()));

    return incomes.findAll(budget.id());
  }
}
