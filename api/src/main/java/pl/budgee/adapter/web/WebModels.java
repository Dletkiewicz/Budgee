package pl.budgee.adapter.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.budgee.domain.model.Audit;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.Income;
import pl.budgee.domain.model.IncomeType;
import pl.budgee.domain.model.User;
import pl.budgee.domain.usecase.CreateIncome.CreateIncomeRequest;
import pl.budgee.domain.usecase.CreateUser.CreateUserRequest;
import pl.budgee.domain.usecase.UpdateUser.UpdateUserRequest;

import java.math.BigDecimal;
import java.util.UUID;

public class WebModels {

  @Value
  static class UserDto {
    UUID id;
    String firstName;
    String lastName;
    String username;
    Audit audit;

    static UserDto of(User user) {
      return new UserDto(user.id().value(), user.firstName(), user.lastName(), user.username(), user.audit());
    }
  }

  @Value
  static class IncomeDto {
    UUID id;
    BigDecimal amount;
    IncomeType type;
    String description;

    static IncomeDto of(Income income) {
      return new IncomeDto(income.id().value(), income.amount(), income.type(), income.description());
    }
  }

  @Data
  @NoArgsConstructor
  static class CreateUserDto {
    @NotBlank @Size(min = 2, max = 40, message = "First name must be between 2 and 40 characters")
    String firstName;
    @NotBlank @Size(min = 2, max = 60, message = "Last name must be between 2 and 60 characters")
    String lastName;
    @NotBlank @Size(min = 2, max = 15, message = "Username must be between 2 and 15 characters")
    String username;
    @NotBlank @Size(min = 5, max = 15, message = "Password must be between 5 and 15 characters")
    String password;

    CreateUserRequest toRequest() {
      return new CreateUserRequest(firstName, lastName, username, password);
    }
  }

  @Data
  @NoArgsConstructor
  static class CreateIncomeDto {
    BigDecimal amount;
    IncomeType type;
    String description;

    CreateIncomeRequest toRequest(BudgetId id) {
      return new CreateIncomeRequest(id, amount, type, description);
    }
  }

  @Data
  @NoArgsConstructor
  static class UpdateUserDto {
    @NotBlank @Size(min = 2, max = 40, message = "First name must be between 2 and 40 characters")
    String firstName;
    @NotBlank @Size(min = 2, max = 60, message = "Last name must be between 2 and 60 characters")
    String lastName;
    @NotBlank @Size(min = 2, max = 15, message = "Username must be between 2 and 15 characters")
    String username;
    @NotBlank @Size(min = 5, max = 15, message = "Password must be between 5 and 15 characters")
    String password;

    UpdateUserRequest toRequest() {
      return new UpdateUserRequest(firstName, lastName, username, password);
    }
  }
}
