package carval.adi.agregadorDeInvestimentos.repository;

import carval.adi.agregadorDeInvestimentos.entity.AccountStock;
import carval.adi.agregadorDeInvestimentos.entity.AccountStockId;
import carval.adi.agregadorDeInvestimentos.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountStockRepository extends JpaRepository<AccountStock, AccountStockId> {
}
