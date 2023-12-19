package pl.budgee.adapter.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.budgee.adapter.jpa.BudgetEntity.EntityResolver;
import pl.budgee.domain.model.Budget;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.User.UserId;
import pl.budgee.domain.port.BudgetRepository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class JpaBudgetRepository implements BudgetRepository, EntityResolver {

  interface SpringDataBudgetRepository extends JpaRepository<BudgetEntity, UUID> {

    void deleteByBusinessId(UUID id);

    @EntityGraph(attributePaths = {"user"})
    Optional<BudgetEntity> findOneByBusinessId(UUID id);

    @EntityGraph(attributePaths = {"user"})
    Optional<BudgetEntity> findOneByUserBusinessId(UUID id);

    BudgetEntity getOneByBusinessId(UUID id);

  }

  interface SpringDataUserRepository extends JpaRepository<UserEntity, UUID> {

    UserEntity getByBusinessId(UUID id);
  }

  private final SpringDataBudgetRepository budgets;
  private final SpringDataUserRepository users;

  @Override
  @Transactional
  public void save(Budget budget) {
    budgets.findOneByUserBusinessId(budget.userId().value()).map(e -> e.update(budget))
        .orElseGet(() -> budgets.save(BudgetEntity.create(this, budget)));
  }

  @Override
  public void subtractBalance(Budget budget, BigDecimal amount) {
    var entity = budgets.getOneByBusinessId(budget.id().value());
    entity.subtractBalance(amount);
  }

  @Override
  public void addBalance(Budget budget, BigDecimal amount) {
    var entity = budgets.getOneByBusinessId(budget.id().value());
    entity.addBalance(amount);
  }

  @Override
  public Optional<Budget> findOneById(BudgetId id) {
    return budgets.findOneByBusinessId(id.value()).map(BudgetEntity::toModel);
  }

  @Override
  public UserEntity resolve(UserId id) {
    return users.getByBusinessId(id.value());
  }
}
