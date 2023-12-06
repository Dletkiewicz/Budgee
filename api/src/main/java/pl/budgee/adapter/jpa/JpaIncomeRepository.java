package pl.budgee.adapter.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

    void deleteByBusinessId(UUID id);

    Optional<IncomeEntity> findOneByBusinessId(UUID id);
  }

  interface SpringDataBudgetRepository extends JpaRepository<BudgetEntity, UUID> {

    BudgetEntity getByBusinessId(UUID id);
  }

  private final SpringDataIncomeRepository incomes;
  private final SpringDataBudgetRepository budgets;

  @Override
  @Transactional
  public Income save(Income income) {
    var budget = budgets.getByBusinessId(income.budgetId().value());
    budget.addBalance(income);
    return incomes.save(IncomeEntity.create(this, income)).toModel();
  }

  @Override
  @Transactional
  public void delete(IncomeId id) {
    incomes.deleteByBusinessId(id.value());
  }

  @Override
  public BudgetEntity resolve(BudgetId id) {
    return budgets.getByBusinessId(id.value());
  }

  @Override
  public Optional<Income> findOneById(IncomeId id) {
    return incomes.findOneByBusinessId(id.value()).map(IncomeEntity::toModel);
  }
}
