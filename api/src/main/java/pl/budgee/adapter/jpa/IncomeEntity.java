package pl.budgee.adapter.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.NaturalId;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.Income;
import pl.budgee.domain.model.Income.IncomeId;
import pl.budgee.domain.model.IncomeType;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "incomes")
public class IncomeEntity {

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
  private IncomeType type;

  @Column(name = "description")
  private String description;

  @Embedded
  private AuditMixin audit = new AuditMixin();

  Income toModel() {
    return new Income(new IncomeId(businessId), new BudgetId(budget.getBusinessId()), amount, type, description,
        audit.toModel());
  }
}
