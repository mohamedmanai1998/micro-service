package tn.esprit.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@EqualsAndHashCode
@Entity
@Table(name = "USERS")
public class Account implements Serializable{

	private static final long serialVersionUID = 032012L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "USERNAME", length = 100, unique = true)
	private String username;

	@Column(name= "EMAIL")
	private String email;

	@Column(name = "PASSWORD")
	private String password;
	
	@Column(name = "is_active")
	private boolean active;
	
	@Column(name = "VERIFICATION_CODE")
	private String verificationCode;
	
	@Column(name = "VERIFICATION_CODE_EXPIRATION")
	private LocalDateTime verificationCodeExpiration;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles;


}
