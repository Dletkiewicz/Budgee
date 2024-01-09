package pl.budgee.adapter.web;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.budgee.adapter.web.WebModels.BudgetDto;
import pl.budgee.adapter.web.WebModels.CreateIncomeDto;
import pl.budgee.adapter.web.WebModels.ExpenseDto;
import pl.budgee.adapter.web.WebModels.IncomeDto;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.BudgetNotFoundException;
import pl.budgee.domain.model.Expense.ExpenseId;
import pl.budgee.domain.model.ExpenseNotFoundException;
import pl.budgee.domain.model.Income.IncomeId;
import pl.budgee.domain.model.IncomeNotFoundException;
import pl.budgee.domain.model.User.UserId;
import pl.budgee.domain.usecase.*;
import pl.budgee.domain.usecase.DeleteExpense.DeleteExpenseRequest;
import pl.budgee.domain.usecase.DeleteIncome.DeleteIncomeRequest;
import pl.budgee.domain.usecase.GetBudget.GetBudgetRequest;
import pl.budgee.domain.usecase.GetExpense.GetExpenseRequest;
import pl.budgee.domain.usecase.GetIncome.GetIncomeRequest;
import pl.budgee.domain.usecase.GetIncomeSum.GetIncomeSumRequest;
import pl.budgee.domain.usecase.ListExpenses.ListExpensesRequest;
import pl.budgee.domain.usecase.ListIncomes.ListIncomesRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class BudgetController {

  private final GetIncome getIncome;
  private final GetExpense getExpense;
  private final ListIncomes listIncomes;
  private final ListExpenses listExpenses;
  private final CreateIncome createIncome;
  private final DeleteIncome deleteIncome;
  private final CreateExpense createExpense;
  private final DeleteExpense deleteExpense;
  private final GetIncomeSum getIncomeSum;
  private final GetBudget getBudget;

  @GetMapping("/{budgetId}/incomes")
  @Operation(summary = "List incomes")
  Slice<IncomeDto> listIncomes(@PathVariable UUID budgetId,
      @ParameterObject @PageableDefault(size = 5) Pageable pageable) {
    try {
      var request = new ListIncomesRequest(new BudgetId(budgetId), pageable);
      return listIncomes.list(request).map(IncomeDto::of);
    } catch (BudgetNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }
  }

  @GetMapping("/{budgetId}/expenses")
  @Operation(summary = "List expenses")
  Slice<ExpenseDto> listExpenses(@PathVariable UUID budgetId,
      @ParameterObject @PageableDefault(size = 5) Pageable pageable) {
    try {
      var request = new ListExpensesRequest(new BudgetId(budgetId), pageable);
      return listExpenses.list(request).map(ExpenseDto::of);
    } catch (BudgetNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }
  }

  @GetMapping("/{userId}/budget")
  @Operation(summary = "Get budget by user ID")
  BudgetDto getBudget(@PathVariable UUID userId) {
    try {
      var request = new GetBudgetRequest(new UserId(userId));
      var budget = getBudget.get(request);
      return BudgetDto.of(budget);
    } catch (BudgetNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }
  }

  @GetMapping("/{budgetId}/incomes/sum")
  @Operation(summary = "Get incomes sum")
  ResponseEntity<BigDecimal> getSum(@PathVariable UUID budgetId,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime startDate,
      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDateTime endDate) {

    Instant startInstant = startDate.atZone(ZoneId.systemDefault()).toInstant();
    Instant endInstant = endDate.atZone(ZoneId.systemDefault()).toInstant();

    var request = new GetIncomeSumRequest(new BudgetId(budgetId), startInstant, endInstant);
    var sum = getIncomeSum.getSum(request);
    return ResponseEntity.ok().body(sum);
  }

  @GetMapping("/{budgetId}/incomes/{incomeId}")
  @Operation(summary = "Get income")
  IncomeDto getIncome(@PathVariable UUID budgetId, @PathVariable UUID incomeId) {
    try {
      var request = new GetIncomeRequest(new BudgetId(budgetId), new IncomeId(incomeId));
      return IncomeDto.of(getIncome.get(request));
    } catch (BudgetNotFoundException | IncomeNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }
  }

  @GetMapping("/{budgetId}/expenses/{expenseId}")
  @Operation(summary = "Get expense")
  ExpenseDto getExpense(@PathVariable UUID budgetId, @PathVariable UUID expenseId) {
    try {
      var request = new GetExpenseRequest(new BudgetId(budgetId), new ExpenseId(expenseId));
      return ExpenseDto.of(getExpense.get(request));
    } catch (BudgetNotFoundException | ExpenseNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }
  }

  @PostMapping("/{budgetId}/incomes")
  @Operation(summary = "Create income")
  ResponseEntity<IncomeDto> createIncome(@PathVariable UUID budgetId, @Valid @RequestBody CreateIncomeDto payload) {
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
  @Operation(summary = "Create expense")
  ResponseEntity<ExpenseDto> createExpense(@PathVariable UUID budgetId,
      @Valid @RequestBody WebModels.CreateExpenseDto payload) {
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

  @DeleteMapping("/{budgetId}/expenses/{expenseId}")
  @Operation(summary = "Delete expense")
  ResponseEntity<Void> deleteExpense(@PathVariable UUID budgetId, @PathVariable UUID expenseId) {
    try {
      var request = new DeleteExpenseRequest(new BudgetId(budgetId), new ExpenseId(expenseId));
      deleteExpense.delete(request);
      return ResponseEntity.noContent().build();
    } catch (BudgetNotFoundException | ExpenseNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), e);
    }
  }
}
