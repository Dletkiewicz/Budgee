package pl.budgee.adapter.web;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.budgee.adapter.web.WebModels.CreateIncomeDto;
import pl.budgee.adapter.web.WebModels.IncomeDto;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.usecase.CreateIncome;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class IncomeController {

  private final CreateIncome createIncome;

  @PostMapping("/{budgetId}/income")
  @Operation(summary = "Create new income")
  ResponseEntity<IncomeDto> createIncome(@PathVariable UUID budgetId, @Valid @RequestBody CreateIncomeDto payload) {
      var request = payload.toRequest(new BudgetId(budgetId));
      var income = createIncome.create(request);
      return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{incomeId}").build(income.id().value()))
          .body(IncomeDto.of(income));
  }
}
