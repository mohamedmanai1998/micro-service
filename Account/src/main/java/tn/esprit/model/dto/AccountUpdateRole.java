package tn.esprit.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountUpdateRole implements Serializable{

 
	/**
	 * 
	 */
	private static final long serialVersionUID = -7906368087217671339L;
	
	private Long idUser ;
	
	private Long idRole;
}