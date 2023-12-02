package pl.budgee.adapter.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.NaturalId;
import pl.budgee.domain.model.Budget;
import pl.budgee.domain.model.Budget.BudgetId;
import pl.budgee.domain.model.User;
import pl.budgee.domain.model.User.UserId;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

@Entity
@Table(name = "budgets")
public class BudgetEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID databaseId;

  @Getter
  @NaturalId
  @Column(name = "business_id")
  private UUID businessId;

  @JoinColumn(name = "user_id")
  @OneToOne(fetch = FetchType.LAZY)
  private UserEntity user;

  @Column(name = "balance")
  private BigDecimal balance;

  @Column(name = "currency")
  private Currency currency;

  Budget toModel() {
    return new Budget(new BudgetId(businessId), new UserId(user.getBusinessId()), balance, currency);
  }

}