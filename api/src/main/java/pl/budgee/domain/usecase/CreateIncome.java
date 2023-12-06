package pl.budgee.domain.usecase;

import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.model.Income;
import pl.budgee.domain.model.Income.IncomeId;
import pl.budgee.domain.model.IncomeType;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.IncomeRepository;

import java.math.BigDecimal;

@RequiredArgsConstructor
public class CreateIncome {

  public record CreateIncomeRequest(BudgetId budgetId, BigDecimal amount, IncomeType type, @Nullable String description){
  }

  private final IncomeRepository incomes;
  private final BudgetRepository budgets;

  public Income create(CreateIncomeRequest request) {
    var budget = budgets.findOneById(request.budgetId()).orElseThrow(() -> new BudgetNotFoundException(request.budgetId()));

    var income = new Income(IncomeId.create(), budget.id(), request.amount(), request.type(),
        request.description(), null);

    return incomes.save(income);
  }
}
