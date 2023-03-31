package org.miage.ProjetAPI.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "candidature")
public class Candidature extends RepresentationModel<Candidature> implements Serializable {
    
	@Serial
	private static final long serialVersionUID = 34568256554L;
	@Id
    private String id;
	private String idoffre;
	private String idpersonne;
	
	public Candidature(String id, String idoffre, String idpersonne) {
		super();
		this.id = id;
		this.idoffre = idoffre;
		this.idpersonne = idpersonne;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdoffre() {
		return idoffre;
	}

	public void setIdoffre(String idoffre) {
		this.idoffre = idoffre;
	}

	public String getIdpersonne() {
		return idpersonne;
	}

	public void setIdpersonne(String idpersonne) {
		this.idpersonne = idpersonne;
	}
}
