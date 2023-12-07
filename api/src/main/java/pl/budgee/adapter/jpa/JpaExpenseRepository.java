package pl.budgee.adapter.jpa;

import lombok.RequiredArgsConstructor;
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

    void deleteByBudgetBusinessIdAndBusinessId(UUID budgetId, UUID id);

  }

  interface SpringDataBudgetRepository extends JpaRepository<BudgetEntity, UUID> {

    BudgetEntity getByBusinessId(UUID id);
  }

  private final SpringDataExpenseRepository expenses;
  private final SpringDataBudgetRepository budgets;

  @Override
  public Expense save(Expense expense) {
    var budget = budgets.getByBusinessId(expense.budgetId().value());
    budget.subtractBalance(expense);
    var entity = expenses.findOneByBusinessId(expense.id().value())
        .map(e -> e.update(expense))
        .orElseGet(() -> ExpenseEntity.create(this, expense)).toModel();

    return entity;
  }

  @Override
  public Optional<Expense> findOneById(BudgetId budgetId, ExpenseId id) {
    return expenses.findOneByBusinessId(id.value()).map(ExpenseEntity::toModel);
  }

  @Override
  public void delete(BudgetId budgetId, ExpenseId id) {
    expenses.deleteByBudgetBusinessIdAndBusinessId(budgetId.value(), id.value());
  }

  @Override
  public BudgetEntity resolve(BudgetId id) {
    return budgets.getByBusinessId(id.value());
  }
}
