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
public class LoginResponseDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2785202733333663060L;

	private String accessToken;
}
