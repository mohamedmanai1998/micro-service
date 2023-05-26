package tn.esprit.service;

import tn.esprit.model.Account;
import tn.esprit.model.dto.*;

import java.util.List;

public interface AccountService {

	AccountCreationDto register(AccountCreationDto accountDto);
	
	List<Account> getAllAccounts();
	
	Account getAccountByid(Long id);
	
	Account getAccountByEmail(String email);
	
	PagedDataDto<Account> getAllAccount(String search, int page, int size);
	
	void updateActive(ValidationCodeAccountDto verficationCode);
	
	void updatePassword(AccountUpdatePasswordDto account);
	
	void deleteAccount(Long id);
	
	void updateRole( AccountUpdateRole accountUpdateDto);
	

}
