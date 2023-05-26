package tn.esprit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.exceptions.BadRequestException;
import tn.esprit.exceptions.NotFoundException;
import tn.esprit.model.Account;
import tn.esprit.model.Role;
import tn.esprit.repository.AccountRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByUsernameIgnoreCase(username)
				.orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", ""));
		if(!account.isActive()) {
			throw new BadRequestException("USER_DISABLED", "");
		}
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		Set<Role> roles = account.getRoles();
		if(roles == null || roles.isEmpty()) {
			throw new BadRequestException("USER_HAS_NO_ROLES", "");
		}else {
			roles.forEach(role->{
				authorities.add(new SimpleGrantedAuthority(role.getName()));
			});
		}
		return new org.springframework.security.core.userdetails.User(account.getUsername(), account.getPassword(), authorities);
	}

}
