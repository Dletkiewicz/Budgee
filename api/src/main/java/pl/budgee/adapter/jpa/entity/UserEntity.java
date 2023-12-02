package pl.budgee.adapter.jpa.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.NaturalId;
import pl.budgee.domain.model.User;
import pl.budgee.domain.model.User.UserId;

import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID databaseId;

  @Getter
  @NaturalId
  @Column(name = "business_id")
  private UUID businessId;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Embedded
  private AuditMixin audit = new AuditMixin();

  User toModel() {
    return new User(new UserId(businessId), username, password, audit.toModel());
  }
}
