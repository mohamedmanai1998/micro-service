package tn.esprit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1093244791404438525L;

	private String username;
	
	private String password;
}
