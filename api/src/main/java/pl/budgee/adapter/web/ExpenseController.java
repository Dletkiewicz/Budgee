package pl.budgee.adapter.web;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.budgee.adapter.web.WebModels.CreateExpenseDto;
import pl.budgee.adapter.web.WebModels.ExpenseDto;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.model.Expense.ExpenseId;
import pl.budgee.domain.model.ExpenseNotFoundException;
import pl.budgee.domain.usecase.CreateExpense;
import pl.budgee.domain.usecase.DeleteExpense;
import pl.budgee.domain.usecase.DeleteExpense.DeleteExpenseRequest;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ExpenseController {

  private final CreateExpense createExpense;
  private final DeleteExpense deleteExpense;

  @PostMapping("/{budgetId}/expenses")
  @Operation(summary = "Create new expense")
  ResponseEntity<ExpenseDto> createExpense(@PathVariable UUID budgetId, @Valid @RequestBody CreateExpenseDto payload) {
    try {
      var request = payload.toRequest(new BudgetId(budgetId));
      var expense = createExpense.create(request);
      return ResponseEntity.created(
              ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{expenseId}").build(expense.id().value()))
          .body(ExpenseDto.of(expense));
    } catch (BudgetNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }
  }

  @DeleteMapping("/{budgetId}/expenses/{expenseId}")
  @Operation(summary = "Create new expense")
  ResponseEntity<Void> createExpense(@PathVariable UUID budgetId, @PathVariable UUID expenseId) {
    try {
      var request = new DeleteExpenseRequest(new BudgetId(budgetId), new ExpenseId(expenseId));
      deleteExpense.delete(request);
      return ResponseEntity.noContent().build();
    } catch (BudgetNotFoundException | ExpenseNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }
  }
}
