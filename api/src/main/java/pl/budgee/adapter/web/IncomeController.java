package pl.budgee.adapter.web;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.budgee.adapter.web.WebModels.CreateIncomeDto;
import pl.budgee.adapter.web.WebModels.IncomeDto;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.Income.IncomeId;
import pl.budgee.domain.model.IncomeNotFoundException;
import pl.budgee.domain.usecase.CreateIncome;
import pl.budgee.domain.usecase.DeleteIncome;
import pl.budgee.domain.usecase.DeleteIncome.DeleteIncomeRequest;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class IncomeController {

  private final CreateIncome createIncome;
  private final DeleteIncome deleteIncome;

  @PostMapping("/{budgetId}/incomes")
  @Operation(summary = "Create new income")
  ResponseEntity<IncomeDto> createIncome(@PathVariable UUID budgetId, @Valid @RequestBody CreateIncomeDto payload) {
    var request = payload.toRequest(new BudgetId(budgetId));
    var income = createIncome.create(request);
    return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{incomeId}").build(income.id().value()))
        .body(IncomeDto.of(income));
  }

  @DeleteMapping("/incomes/{incomeId}")
  @Operation(summary = "Delete income")
  ResponseEntity<Void> deleteIncome(@PathVariable UUID incomeId) {
    try {
      var request = new DeleteIncomeRequest(new IncomeId(incomeId));
      deleteIncome.delete(request);
      return ResponseEntity.noContent().build();
    } catch (IncomeNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }
  }

}