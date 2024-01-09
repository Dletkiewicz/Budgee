package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.model.Income;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.IncomeRepository;

@RequiredArgsConstructor
public class ListIncomes {

  public record ListIncomesRequest(BudgetId budgetId, Pageable pageable) {
  }

  private final BudgetRepository budgets;
  private final IncomeRepository incomes;

  public Slice<Income> list(ListIncomesRequest request) {
    var budget = budgets.findOneById(request.budgetId()).orElseThrow(() -> new BudgetNotFoundException(request.budgetId()));

    return incomes.findAll(budget.id(), request.pageable());
  }
}
