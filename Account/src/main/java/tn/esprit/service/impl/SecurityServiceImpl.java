package tn.esprit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.esprit.exceptions.NotFoundException;
import tn.esprit.model.Account;
import tn.esprit.repository.AccountRepository;
import tn.esprit.service.SecurityService;

@Service
public class SecurityServiceImpl implements SecurityService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account getCurrentUser() {
		String currentUsername = getCurrentUsername();
		return accountRepository.findByUsernameIgnoreCase(currentUsername)
				.orElseThrow(() -> new NotFoundException("", ""));
	}

	@Override
	public String getCurrentUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = new String();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return username;
	}

}
