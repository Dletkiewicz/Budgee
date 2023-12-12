package pl.budgee.adapter.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.budgee.adapter.jpa.IncomeEntity.EntityResolver;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.Income;
import pl.budgee.domain.model.Income.IncomeId;
import pl.budgee.domain.port.IncomeRepository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaIncomeRepository implements IncomeRepository, EntityResolver {

  interface SpringDataIncomeRepository extends JpaRepository<IncomeEntity, UUID> {

    @EntityGraph(attributePaths = {"budget"})
    void deleteByBudgetBusinessIdAndBusinessId(UUID budgetId, UUID id);

    @EntityGraph(attributePaths = {"budget"})
    Optional<IncomeEntity> findOneByBudgetBusinessIdAndBusinessId(UUID budgetId, UUID incomeId);

    @EntityGraph(attributePaths = {"budget"})
    Slice<IncomeEntity> findAllByBudgetBusinessId(UUID budgetId);
  }

  interface SpringDataBudgetRepository extends JpaRepository<BudgetEntity, UUID> {

    BudgetEntity getByBusinessId(UUID id);
  }

  private final SpringDataIncomeRepository incomes;
  private final SpringDataBudgetRepository budgets;

  @Override
  @Transactional
  public Income save(Income income) {
    var entity = incomes.findOneByBudgetBusinessIdAndBusinessId(income.budgetId().value(), income.id().value())
        .map(e -> e.update(income))
        .orElseGet(() -> incomes.save(IncomeEntity.create(this, income)));

    return entity.toModel();
  }

  @Override
  @Transactional
  public void delete(BudgetId budgetId, IncomeId id) {
    incomes.deleteByBudgetBusinessIdAndBusinessId(budgetId.value(), id.value());
  }

  @Override
  public BudgetEntity resolve(BudgetId id) {
    return budgets.getByBusinessId(id.value());
  }

  @Override
  public Slice<Income> findAll(BudgetId budgetId) {
    return incomes.findAllByBudgetBusinessId(budgetId.value()).map(IncomeEntity::toModel);
  }

  @Override
  public Optional<Income> findOneById(BudgetId budgetId, IncomeId id) {
    return incomes.findOneByBudgetBusinessIdAndBusinessId(budgetId.value(), id.value()).map(IncomeEntity::toModel);
  }
}
