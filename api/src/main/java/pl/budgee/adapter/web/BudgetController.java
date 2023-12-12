package pl.budgee.adapter.web;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.budgee.adapter.web.WebModels.ExpenseDto;
import pl.budgee.adapter.web.WebModels.IncomeDto;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.model.Expense.ExpenseId;
import pl.budgee.domain.model.ExpenseNotFoundException;
import pl.budgee.domain.model.Income.IncomeId;
import pl.budgee.domain.model.IncomeNotFoundException;
import pl.budgee.domain.usecase.CreateExpense;
import pl.budgee.domain.usecase.CreateIncome;
import pl.budgee.domain.usecase.DeleteExpense;
import pl.budgee.domain.usecase.DeleteExpense.DeleteExpenseRequest;
import pl.budgee.domain.usecase.DeleteIncome;
import pl.budgee.domain.usecase.DeleteIncome.DeleteIncomeRequest;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BudgetController {

  private final CreateIncome createIncome;
  private final DeleteIncome deleteIncome;
  private final CreateExpense createExpense;
  private final DeleteExpense deleteExpense;

  @PostMapping("/{budgetId}/incomes")
  @Operation(summary = "Create new income")
  ResponseEntity<IncomeDto> createIncome(@PathVariable UUID budgetId, @Valid @RequestBody WebModels.CreateIncomeDto payload) {
    try {
      var request = payload.toRequest(new BudgetId(budgetId));
      var income = createIncome.create(request);
      return ResponseEntity.created(
              ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{incomeId}").build(income.id().value()))
          .body(IncomeDto.of(income));
    } catch (BudgetNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }
  }

  @PostMapping("/{budgetId}/expenses")
  @Operation(summary = "Create new expense")
  ResponseEntity<ExpenseDto> createExpense(@PathVariable UUID budgetId, @Valid @RequestBody WebModels.CreateExpenseDto payload) {
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

  @DeleteMapping("/{budgetId}/incomes/{incomeId}")
  @Operation(summary = "Delete income")
  ResponseEntity<Void> deleteIncome(@PathVariable UUID budgetId, @PathVariable UUID incomeId) {
    try {
      var request = new DeleteIncomeRequest(new BudgetId(budgetId), new IncomeId(incomeId));
      deleteIncome.delete(request);
      return ResponseEntity.noContent().build();
    } catch (BudgetNotFoundException | IncomeNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }
  }
}
