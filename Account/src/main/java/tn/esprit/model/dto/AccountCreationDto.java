package tn.esprit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.model.Role;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCreationDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5397769729399699590L;

	private String username;
	
	private String password;
	
	private String confirmPassword;
	
	private String email;
	
	private Role role ;
}
