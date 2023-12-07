package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.model.Income.IncomeId;
import pl.budgee.domain.model.IncomeNotFoundException;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.IncomeRepository;

@RequiredArgsConstructor
public class DeleteIncome {

  public record DeleteIncomeRequest(BudgetId budgetId, IncomeId id) {
  }

  private final IncomeRepository incomes;
  private final BudgetRepository budgets;

  public void delete(DeleteIncomeRequest request) {
    var budget = budgets.findOneById(request.budgetId()).orElseThrow(() -> new BudgetNotFoundException(request.budgetId()));
    var income = incomes.findOneById(budget.id(), request.id()).orElseThrow(() -> new IncomeNotFoundException(request.id()));

    incomes.delete(request.budgetId(), income.id());
  }
}
