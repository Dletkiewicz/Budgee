package pl.budgee.adapter.jpa;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.budgee.adapter.jpa.ExpenseEntity.EntityResolver;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.Expense;
import pl.budgee.domain.model.Expense.ExpenseId;
import pl.budgee.domain.port.ExpenseRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class JpaExpenseRepository implements ExpenseRepository, EntityResolver {

  interface SpringDataExpenseRepository extends JpaRepository<ExpenseEntity, UUID> {

    Optional<ExpenseEntity> findOneByBusinessId(UUID id);

    @EntityGraph(attributePaths = {"budget"})
    void deleteByBudgetBusinessIdAndBusinessId(UUID budgetId, UUID id);

    @EntityGraph(attributePaths = {"budget"})
    Slice<ExpenseEntity> findAllByBudgetBusinessId(UUID budgetId, Pageable pageable);

  }

  interface SpringDataBudgetRepository extends JpaRepository<BudgetEntity, UUID> {

    BudgetEntity getByBusinessId(UUID id);
  }

  private final SpringDataExpenseRepository expenses;
  private final SpringDataBudgetRepository budgets;

  @Override
  @Transactional
  public Expense save(Expense expense) {
    var entity = expenses.findOneByBusinessId(expense.id().value())
        .map(e -> e.update(expense))
        .orElseGet(() -> expenses.save(ExpenseEntity.create(this, expense)));

    return entity.toModel();
  }

  @Override
  public Optional<Expense> findOneById(BudgetId budgetId, ExpenseId id) {
    return expenses.findOneByBusinessId(id.value()).map(ExpenseEntity::toModel);
  }

  @Override
  @Transactional
  public void delete(BudgetId budgetId, ExpenseId id) {
    expenses.deleteByBudgetBusinessIdAndBusinessId(budgetId.value(), id.value());
  }

  @Override
  public Slice<Expense> findAll(BudgetId budgetId, Pageable pageable) {
    return expenses.findAllByBudgetBusinessId(budgetId.value(), pageable).map(ExpenseEntity::toModel);
  }

  @Override
  public BudgetEntity resolve(BudgetId id) {
    return budgets.getByBusinessId(id.value());
  }
}
