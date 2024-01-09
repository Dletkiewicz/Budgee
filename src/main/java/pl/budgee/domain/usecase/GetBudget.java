package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.Budget;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.model.User.UserId;
import pl.budgee.domain.port.BudgetRepository;

@RequiredArgsConstructor
public class GetBudget {

  public record GetBudgetRequest(UserId userId) {
  }

  private final BudgetRepository budgets;

  public Budget get(GetBudgetRequest request) {
    return budgets.findOneByUser(request.userId()).orElseThrow(() -> new BudgetNotFoundException(request.userId()));
  }
}
