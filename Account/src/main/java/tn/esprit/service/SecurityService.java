package tn.esprit.service;

import tn.esprit.model.Account;

public interface SecurityService {
	
	String getCurrentUsername();

	Account getCurrentUser();
}
