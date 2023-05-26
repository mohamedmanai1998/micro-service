package tn.esprit.exceptions;

public class BadRequestException extends RestException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4442388488241840661L;

	public BadRequestException(String code, String message) {
		super(code, message, 400);
	}

}
