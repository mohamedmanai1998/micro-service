package tn.esprit.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class Constantes {

	public static final Integer VERIFICATION_CODE_LENGTH = 8;
	
	public static final Integer VERIFICATION_CODE_LIFE_MINUTES = 30;
	
	public static final String DOCS_PATH = "C:/attachments";
	
	public static String formatDate(java.util.Date d, String format) {
		if (d==null)	return null;
		try {
			DateFormat df = new SimpleDateFormat(format);
			return df.format(d);
		} catch (Exception e) {			
			e.printStackTrace();
			return null;
		}
	}
}
