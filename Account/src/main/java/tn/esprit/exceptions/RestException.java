package tn.esprit.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8137083502929555392L;

	private String code;
	
	private String message;
	
	private int status;
	
	
}
