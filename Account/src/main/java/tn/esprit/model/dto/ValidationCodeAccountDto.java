package tn.esprit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationCodeAccountDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4045662820329710848L;

	private String email;
	
	private String validationCode;

}
