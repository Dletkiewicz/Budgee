package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.Income.IncomeId;
import pl.budgee.domain.model.IncomeNotFoundException;
import pl.budgee.domain.port.IncomeRepository;

@RequiredArgsConstructor
public class DeleteIncome {

  public record DeleteIncomeRequest(IncomeId id) {
  }

  private final IncomeRepository incomes;

  public void delete(DeleteIncomeRequest request) {
    var income = incomes.findOneById(request.id()).orElseThrow(() -> new IncomeNotFoundException(request.id()));

    incomes.delete(income.id());
  }
}
