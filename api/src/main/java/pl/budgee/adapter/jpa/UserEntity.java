package pl.budgee.adapter.jpa;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.NaturalId;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import pl.budgee.domain.model.User;
import pl.budgee.domain.model.User.UserId;

import java.util.UUID;

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id")
  private UUID databaseId;

  @Getter
  @NaturalId
  @Column(name = "business_id")
  private UUID businessId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Embedded
  private AuditMixin audit = new AuditMixin();

  static UserEntity create(User user) {
    var entity = new UserEntity();
    entity.businessId = user.id().value();
    entity.firstName = user.firstName();
    entity.lastName = user.lastName();
    entity.username = user.username();
    entity.password = user.password();
    return entity;
  }

  UserEntity update(User user) {
    firstName = user.firstName();
    lastName = user.lastName();
    username = user.username();
    password = user.password();
    return this;
  }

  User toModel() {
    return new User(new UserId(businessId),firstName, lastName, username, password, audit.toModel());
  }
}
