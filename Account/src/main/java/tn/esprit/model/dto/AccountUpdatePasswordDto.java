package tn.esprit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdatePasswordDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 820264706903339623L;
	
	private String oldPassword;
	private String newPassword;
	private String repeatNewPassword;

}
