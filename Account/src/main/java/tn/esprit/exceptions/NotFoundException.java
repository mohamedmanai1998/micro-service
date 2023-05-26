package tn.esprit.exceptions;

public class NotFoundException extends RestException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4873069316416302517L;
	
	public NotFoundException(String code, String message) {
		super(code, message, 404);
	}
		
}
