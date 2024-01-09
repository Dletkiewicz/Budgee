package pl.budgee.domain.port;

import pl.budgee.domain.model.User;
import pl.budgee.domain.model.User.UserId;

import java.util.Optional;

public interface UserRepository {

  User save(User user);

  void delete(UserId id);

  boolean existsByUsername(String username);

  Optional<User> findOneByUsername(String username);

  Optional<User> findOneById(UserId id);

}
