package pl.budgee.domain.usecase;

import lombok.RequiredArgsConstructor;
import pl.budgee.domain.model.Budget;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.User;
import pl.budgee.domain.model.User.UserId;
import pl.budgee.domain.model.UsernameTakenException;
import pl.budgee.domain.port.BudgetRepository;
import pl.budgee.domain.port.UserRepository;

import java.math.BigDecimal;
import java.util.Currency;

@RequiredArgsConstructor
public class CreateUser {

  public record CreateUserRequest(String firstName, String lastName, String username, String password){
  }

  private final UserRepository users;
  private final BudgetRepository budgets;

  public User create(CreateUserRequest request) {
    if (users.existsByUsername(request.username())) {
      throw new UsernameTakenException(request.username());
    }

    var user = users.save(new User(UserId.create(), request.firstName(), request.lastName(), request.username(), request.password(), null));
    budgets.save(new Budget(BudgetId.create(), user.id(), new BigDecimal(0), Currency.getInstance("PLN")));

    return user;
  }
}
