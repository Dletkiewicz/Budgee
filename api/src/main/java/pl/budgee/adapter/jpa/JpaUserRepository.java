package pl.budgee.adapter.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.budgee.domain.model.User;
import pl.budgee.domain.model.User.UserId;
import pl.budgee.domain.port.UserRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class JpaUserRepository implements UserRepository {

  interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {
    void deleteByBusinessId(UUID id);

    boolean existsByUsername(String username);
    Optional<UserEntity> findOneByBusinessId(UUID id);
  }

  private final SpringDataUserRepository users;

  @Override
  @Transactional
  public User save(User user) {
    var entity = users.findOneByBusinessId(user.id().value())
        .map(e -> e.update(user))
        .orElseGet(() -> users.save(UserEntity.create(user)));

    return entity.toModel();
  }

  @Override
  @Transactional
  public void delete(UserId id) {
    users.deleteByBusinessId(id.value());
  }

  @Override
  public boolean existsByUsername(String username) {
    return users.existsByUsername(username);
  }

  @Override
  public Optional<User> findOneById(UserId id) {
    return users.findOneByBusinessId(id.value()).map(UserEntity::toModel);
  }
}
