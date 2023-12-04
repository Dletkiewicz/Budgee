package pl.budgee.adapter.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.NaturalId;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.Expense;
import pl.budgee.domain.model.Expense.ExpenseId;
import pl.budgee.domain.model.ExpenseType;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "expenses")
public class ExpenseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID databaseId;

  @Getter
  @NaturalId
  @Column(name = "business_id")
  private UUID businessId;

  @JoinColumn(name = "budget_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private BudgetEntity budget;

  @Column(name = "amount")
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(name = "type")
  private ExpenseType type;

  @Column(name = "description")
  private String description;

  @Embedded
  private AuditMixin audit = new AuditMixin();

  Expense toModel() {
    return new Expense(new ExpenseId(businessId), new BudgetId(budget.getBusinessId()), amount, type, description,
        audit.toModel());
  }

}
