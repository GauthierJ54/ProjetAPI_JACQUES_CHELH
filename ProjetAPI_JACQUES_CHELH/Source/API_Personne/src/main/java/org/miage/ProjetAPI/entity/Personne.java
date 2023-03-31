package org.miage.ProjetAPI.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.hateoas.RepresentationModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "personne")
public class Personne extends RepresentationModel<Personne> implements Serializable {
	
	private static final long serialVersionUID = 356898653245L;
	@Id
	private String id;
	private String prenom;
	private String nom;
	private String niveau;
	private String type;
	
	public Personne(String id, String prenom, String nom, String niveau, String type) {
		super();
		this.id = id;
		this.prenom = prenom;
		this.nom = nom;
		this.niveau = niveau;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	
}
