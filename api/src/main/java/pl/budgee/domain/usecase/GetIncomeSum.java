package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.IncomeRepository;

import java.math.BigDecimal;
import java.time.Instant;

@RequiredArgsConstructor
public class GetIncomeSum {

  public record GetIncomeSumRequest(BudgetId budgetId, Instant startDate, Instant endDate) {
  }

  private final BudgetRepository budgets;
  private final IncomeRepository incomes;

  public BigDecimal getSum(GetIncomeSumRequest request) {
    var budget = budgets.findOneById(request.budgetId()).orElseThrow(() -> new BudgetNotFoundException(request.budgetId()));

    return incomes.sumIncomes(budget.id(), request.startDate(), request.endDate());

  }
}
