package pl.budgee.income;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.budgee.domain.port.IncomeRepository;
import pl.budgee.domain.usecase.CreateIncome;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class CreateIncomeTests {

  public final CreateIncome createIncome = mock(CreateIncome.class);
  private final IncomeRepository incomes = mock(IncomeRepository.class);

  @Test
  void should_create_income_when_budget_exists() {

  }

  }