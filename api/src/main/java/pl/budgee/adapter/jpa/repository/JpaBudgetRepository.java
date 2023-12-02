package pl.budgee.adapter.jpa.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.budgee.domain.port.BudgetRepository;

@RequiredArgsConstructor
@Repository
public class JpaBudgetRepository implements BudgetRepository {

  interface SpringDataBudgetRepository extends JpaRepository<BudgetEntity, UUID> {

  }
}
