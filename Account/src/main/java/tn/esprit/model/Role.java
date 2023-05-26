package tn.esprit.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Data
@Entity
@Table(name = "ROLES")
public class Role implements Serializable {

	private static final long serialVersionUID = 8374931L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="NAME", unique=true, length=100)
	private String name;
	
	@Lob
	@Column(name="DESCRIPTION")
	private String description;
	
//	@ManyToMany(mappedBy="roles")
//	private Set<Account> users = new HashSet<>();

}
