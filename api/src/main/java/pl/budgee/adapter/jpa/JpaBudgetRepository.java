package pl.budgee.adapter.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.budgee.domain.port.BudgetRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class JpaBudgetRepository implements BudgetRepository {

  interface SpringDataBudgetRepository extends JpaRepository<BudgetEntity, UUID> {

  }
}
