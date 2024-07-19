package carval.adi.agregadorDeInvestimentos.repository;

import carval.adi.agregadorDeInvestimentos.entity.Account;
import carval.adi.agregadorDeInvestimentos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
}
