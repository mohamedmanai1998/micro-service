package tn.esprit.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.model.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

	Optional<Account> findByUsernameIgnoreCase(String username);
	
	Optional<Account> findByEmailIgnoreCase(String email);
	
	@Query("FROM Account account WHERE "
			+ "(:search IS NULL OR account.username LIKE %:search% OR account.email LIKE %:search%)"
			+ "AND(account.active = true)")
	Page<Account> findAccountsBySearch(String search, Pageable pageable);
	 
	Account findByVerificationCode(String verifiactionCode);
	

}
