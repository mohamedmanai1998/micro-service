package tn.esprit.service.impl;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.common.Constantes;
import tn.esprit.common.ErrorCode;
import tn.esprit.common.RegexValidation;
import tn.esprit.exceptions.BadRequestException;
import tn.esprit.exceptions.NotFoundException;
import tn.esprit.model.Account;
import tn.esprit.model.Role;
import tn.esprit.model.dto.*;
import tn.esprit.repository.AccountRepository;
import tn.esprit.repository.RoleRepository;
import tn.esprit.service.AccountService;
import tn.esprit.service.SecurityService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private RoleRepository roleRepository;


	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SecurityService securityservice;

	@Override
	public AccountCreationDto register(AccountCreationDto accountDto) {
		if (StringUtils.isEmpty(accountDto.getEmail())) {
			throw new BadRequestException(ErrorCode.MISSING_REQUIRED_DATA + "_EMAIL", "");
		}

		if (StringUtils.isEmpty(accountDto.getUsername())) {
			throw new BadRequestException(ErrorCode.MISSING_REQUIRED_DATA + "_USERNAME", "");
		}

		if (StringUtils.isEmpty(accountDto.getPassword())) {
			throw new BadRequestException(ErrorCode.MISSING_REQUIRED_DATA + "_PASSWORD", "");
		}

		if (StringUtils.isEmpty(accountDto.getConfirmPassword())) {
			throw new BadRequestException(ErrorCode.MISSING_REQUIRED_DATA + "_CONFIRM_PASSWORD", "");
		}

		if (!RegexValidation.isValidEmail(accountDto.getEmail())) {
			throw new BadRequestException(ErrorCode.INVALID_EMAIL, "");
		}

		if (null != accountDto.getPassword() && (StringUtils.length(accountDto.getPassword()) < 8
				|| !RegexValidation.checkPasswordFormat(RegexValidation.CHAR_PATTERN, accountDto.getPassword())
				|| !RegexValidation.checkPasswordFormat(RegexValidation.NUMERIC_PATTERN, accountDto.getPassword())
				|| !RegexValidation.checkPasswordFormat(RegexValidation.NOT_CHAR_PATTERN, accountDto.getPassword()))) {
			log.info(ErrorCode.ERROR_USER_INVALID_PASSWORD);
			throw new BadRequestException(ErrorCode.ERROR_USER_INVALID_PASSWORD, ErrorCode.ERROR_USER_INVALID_PASSWORD);
		}

		if (!accountDto.getConfirmPassword().equals(accountDto.getPassword())) {
			throw new BadRequestException(ErrorCode.PASSWORD_NOT_EQ_CONFIRM_PASSWORD, "");
		}

		Optional<Account> testAccount = accountRepository.findByEmailIgnoreCase(accountDto.getEmail());
		if (!testAccount.isEmpty()) {
			throw new BadRequestException(ErrorCode.DUPLICATED_EMAIL, "");
		}

		testAccount = accountRepository.findByUsernameIgnoreCase(accountDto.getUsername());
		if (!testAccount.isEmpty()) {
			throw new BadRequestException(ErrorCode.DUPLICATED_USERNAME, "");
		}

		Account account = new Account();
		account.setEmail(accountDto.getEmail());
		account.setActive(false);
		account.setUsername(accountDto.getUsername());
		String encodedPassword = passwordEncoder.encode(accountDto.getPassword());
		account.setPassword(encodedPassword);
		String verificationCode = RandomStringUtils.randomNumeric(Constantes.VERIFICATION_CODE_LENGTH);
		LocalDateTime verificationCodeExpiration = LocalDateTime.now(ZoneId.systemDefault())
				.plusMinutes(Constantes.VERIFICATION_CODE_LIFE_MINUTES);
		account.setVerificationCode(verificationCode);
		account.setVerificationCodeExpiration(verificationCodeExpiration);

		Optional<Role> memberRole = roleRepository.findById(2L);
		if (!memberRole.isEmpty()) {
			Set<Role> roles = new HashSet<Role>();
			roles.add(memberRole.get());
			account.setRoles(roles);
		}

		accountRepository.save(account);

		return null;
	}

	@Override
	public List<Account> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		if (accounts.isEmpty()) {
			throw new BadRequestException("There is not an account", "");
		}
		return accounts;
	}

	@Override
	public Account getAccountByid(Long id) {
		Optional<Account> account = accountRepository.findById(id);
		if (account.isEmpty()) {
			throw new BadRequestException("There is not an account", "");
		}
		return account.get();
	}

	@Override
	public Account getAccountByEmail(String email) {
		Optional<Account> account = accountRepository.findByEmailIgnoreCase(email);
		if (account.isPresent()) {
			return account.get();
		} else
			throw new BadRequestException("There is not an account", "");
	}

	@Override
	public PagedDataDto<Account> getAllAccount(String search, int page, int size) {
		Pageable paging = PageRequest.of(page, size);
		search = StringUtils.lowerCase(search);
		Page<Account> accounts = accountRepository.findAccountsBySearch(search, paging);
		return new PagedDataDto<Account>(accounts);
	}

	@Override
	public void updateActive(ValidationCodeAccountDto verificationCode) {
		Account account = accountRepository.findByEmailIgnoreCase(verificationCode.getEmail())
				.orElseThrow(() -> new NotFoundException("USER_NOT_FOUND", ""));
		if(account.getVerificationCode() == null || account.getVerificationCode().isEmpty()) {
			throw new BadRequestException("USER_HAVE_NO_VER_CODE", "");
		}
		
		if(!account.getVerificationCode().equals(verificationCode.getValidationCode())) {
			throw new BadRequestException("VALIDATION_CODE_INVALID", "");
		}
		
		if(account.getVerificationCodeExpiration().isBefore(LocalDateTime.now(ZoneId.systemDefault()))) {
			throw new BadRequestException("VALIDATION_CODE_EXPIRED", "");
		}
		
		account.setActive(true);
		account.setVerificationCode(null);
		account.setVerificationCodeExpiration(null);
		account = accountRepository.save(account);
	}
	
	@Override
	public void deleteAccount(Long id) {
		Optional<Account> user = accountRepository.findById(id);
		
		if(!user.isEmpty()) {
			
			user.get().setActive(false);
			
			accountRepository.save(user.get());
		}else
			 throw new NotFoundException("There is not an account", "");
		
	}

	@Override
	public void updatePassword(AccountUpdatePasswordDto updatePasswordDto) {
		validateUpdatePasswordDto(updatePasswordDto);
		Account currentUser = securityservice.getCurrentUser();
		if(!updatePasswordDto.getNewPassword().equals(updatePasswordDto.getRepeatNewPassword())) {
			throw new BadRequestException("NEW_PASSWORD_CONFIRMATION_INVALID","");
		}
		
		if(!passwordEncoder.matches(updatePasswordDto.getOldPassword(), currentUser.getPassword())) {
			throw new BadRequestException("OLD_PASSWORD_INVALID","");
		}
		
		String newPasswordEncoded = passwordEncoder.encode(updatePasswordDto.getNewPassword());
		currentUser.setPassword(newPasswordEncoded);
		accountRepository.save(currentUser);
		
	}
	
	private void validateUpdatePasswordDto(AccountUpdatePasswordDto updatePasswordDto) {
		if (StringUtils.isEmpty(updatePasswordDto.getOldPassword())) {
			throw new BadRequestException("MISSING_REQUIRED_DATA_CURRENT_PASSWORD","");
		}
		
		if (StringUtils.isEmpty(updatePasswordDto.getNewPassword())) {
			throw new BadRequestException("MISSING_REQUIRED_DATA_NEW_PASSWORD","");
		}
		
		if (StringUtils.isEmpty(updatePasswordDto.getRepeatNewPassword())) {
			throw new BadRequestException("MISSING_REQUIRED_DATA_NEW_PASSWORD_CONFIRMATION","");
		}
	}

	@Override
	public void updateRole(AccountUpdateRole accountUpdateDto) {
		Account account = accountRepository.findById(accountUpdateDto.getIdUser()).get();
		Role role = roleRepository.findById(accountUpdateDto.getIdRole()).get();
		Set<Role> roles = new HashSet<Role>();
		if(role.getId()==1L) {
			roles.add(role);
			account.setRoles(roles);
			account = accountRepository.save(account);
		}else if(role.getId()==2L) {
			roles.add(role);
			account.setRoles(roles);
			account = accountRepository.save(account);
		}
		
		
	}

}
