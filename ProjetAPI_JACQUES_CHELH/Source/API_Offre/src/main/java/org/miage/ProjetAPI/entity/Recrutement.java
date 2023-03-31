package org.miage.ProjetAPI.entity;

import java.io.Serializable;
import java.util.List;
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
@Table(name = "recrutement")
public class Recrutement extends RepresentationModel<Recrutement> implements Serializable {
	
	private static final long serialVersionUID = 87643794322L;
	@Id
	private String id;
	private String nomoffre;
	private String idcandidature;
	private String nbcandidats;
	
	public Recrutement(String id, String nomoffre, String idcandidature, String nbcandidats) {
		super();
		this.id = id;
		this.nomoffre = nomoffre;
		this.idcandidature = idcandidature;
		this.nbcandidats = nbcandidats;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNomoffre() {
		return nomoffre;
	}

	public void setNomoffre(String nomoffre) {
		this.nomoffre = nomoffre;
	}

	public String getIdcandidature() {
		return idcandidature;
	}

	public void setIdcandidature(String idcandidature) {
		this.idcandidature = idcandidature;
	}

	public String getNbcandidats() {
		return nbcandidats;
	}

	public void setNbcandidats(String nbcandidats) {
		this.nbcandidats = nbcandidats;
	}
}
