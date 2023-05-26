package tn.esprit.exceptions;

public class ServerErrorException extends RestException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4260614112249996514L;

	public ServerErrorException(String code, String message) {
		super(code, message, 500);
	}
}
