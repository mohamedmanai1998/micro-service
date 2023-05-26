package tn.esprit.rest.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.config.JWTAuthorizationFilter;
import tn.esprit.exceptions.BadRequestException;
import tn.esprit.model.Account;
import tn.esprit.model.dto.*;
import tn.esprit.repository.AccountRepository;
import tn.esprit.service.AccountService;
import tn.esprit.service.SecurityService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/accounts")
@CrossOrigin("*")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	private AccountRepository accountRepository;

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody AccountCreationDto accountDto) {
		accountService.register(accountDto);
		return ResponseEntity.ok(null);
	}
	
//	@GetMapping("/all")
//	public ResponseEntity<List<Account>> getAllAccounts(String search, Long compteMailid, int page, int size){
//		Page<Account> accounts = accountService.getAllAccount(search, compteMailid, page, size);
//		return ResponseEntity.ok(accounts.getContent());
//	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest){
		System.out.println(passwordEncoder.encode("SuperAdmin"));
		if(StringUtils.isEmpty(loginRequest.getUsername()) || StringUtils.isEmpty(loginRequest.getPassword())) {
			throw new BadRequestException("MISSING_USERNAME_OR_PASSWORD", "");
		}
		Account account= accountRepository.findByUsernameIgnoreCase(loginRequest.getUsername()).get();
		if(!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) {
			throw new BadRequestException("PASSWORD_INCORRECT", "");
		}
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		User userPrincipal = (User) authentication.getPrincipal();
		
		Account currentUser = accountRepository.findByUsernameIgnoreCase(loginRequest.getUsername()).get();
		String jwt = Jwts.builder().setSubject(userPrincipal.getUsername()).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + JWTAuthorizationFilter.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS256, JWTAuthorizationFilter.SECRET)
				.claim("roles", userPrincipal.getAuthorities()).claim("name", currentUser.getUsername())
				.compact();
		return ResponseEntity.ok(new LoginResponseDto(jwt));
	}
	@PutMapping(value ="/updateActive")
	public ResponseEntity<Account> updateActive(@RequestBody ValidationCodeAccountDto verificationCode){
		accountService.updateActive(verificationCode);
		return ResponseEntity.ok().body(null);
	}
	
	@GetMapping
	public ResponseEntity<Account> getCurrentUser(){
		return ResponseEntity.ok(securityService.getCurrentUser());
		
	}
	@GetMapping("/{id}")
	public ResponseEntity<Account> getAccountById(@PathVariable Long id){
		return ResponseEntity.ok(accountService.getAccountByid(id));
		
	}
	
	@GetMapping("/allAccounts")
	public ResponseEntity<List<Account>> getAccounts(){
		return ResponseEntity.ok(accountService.getAllAccounts());
		
	}
	@GetMapping("/users")
	public ResponseEntity<PagedDataDto<Account>> getAccountsWithPaging(@RequestParam(value = "page") int page,
                                                                       @RequestParam(required = false) int size,

                                                                       @RequestParam(required = false) String search){
		return ResponseEntity.ok(accountService.getAllAccount(search, page, size));
		
	}
	@PutMapping("{id}")
	public ResponseEntity<?> deleteAccount(@PathVariable Long id){
		accountService.deleteAccount(id);
		return ResponseEntity.ok(null);
	}
	
	@PutMapping("/updatePass")
	public ResponseEntity<AccountUpdatePasswordDto> updatePassword(@RequestBody AccountUpdatePasswordDto accountDto){
		accountService.updatePassword(accountDto);
		return ResponseEntity.ok(null);
	}
	@PostMapping("/updateRole")
	public ResponseEntity<AccountUpdateRole> updateRole(
			@RequestBody AccountUpdateRole accountDto){
		accountService.updateRole(accountDto);
		return ResponseEntity.ok(null);
	}
	
}
