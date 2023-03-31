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
@Table(name = "offre")
public class Offre extends RepresentationModel<Offre> implements Serializable {
    
	@Serial
	private static final long serialVersionUID = 1244647786456L;
	@Id
    private String id;
	private String nomstage;
    private String domaine;
    private String nomorga;
    private String descstage;
    private String datepub;
    private String nivetu;
    private String exprequise;
    private String datedeb;
    private String duree;
    private String salaire;
    private String indemnisation;
    private String orgaadrpays;
    private String orgaadrville;
    private String orgaadrcp;
    private String orgaadrrue;
    private String orgaemail;
    private String orgatel;
    private String orgaurl;
    private String stageadrpays;
    private String stageadrville;
    private String stageadrcp;
    private String stageadrrue;
    private String latitude;
    private String longitude;
    private String tel;
    private String url;
    private String statut;
    private String nomuserret;
	
    public Offre(String id, String nomstage, String domaine, String nomorga, String descstage, String datepub,
			String nivetu, String exprequise, String datedeb, String duree, String salaire, String indemnisation,
			String orgaadrpays, String orgaadrville, String orgaadrcp, String orgaadrrue, String orgaemail,
			String orgatel, String orgaurl, String stageadrpays, String stageadrville, String stageadrcp, String stageadrrue,
			String latitude, String longitude, String tel, String url, String statut, String nomuserret) {
		super();
		this.id = id;
		this.nomstage = nomstage;
		this.domaine = domaine;
		this.nomorga = nomorga;
		this.descstage = descstage;
		this.datepub = datepub;
		this.nivetu = nivetu;
		this.exprequise = exprequise;
		this.datedeb = datedeb;
		this.duree = duree;
		this.salaire = salaire;
		this.indemnisation = indemnisation;
		this.orgaadrpays = orgaadrpays;
		this.orgaadrville = orgaadrville;
		this.orgaadrcp = orgaadrcp;
		this.orgaadrrue = orgaadrrue;
		this.orgaemail = orgaemail;
		this.orgatel = orgatel;
		this.orgaurl = orgaurl;
		this.stageadrpays = stageadrpays;
		this.stageadrville = stageadrville;
		this.stageadrcp = stageadrcp;
		this.stageadrrue = stageadrrue;
		this.latitude = latitude;
		this.longitude = longitude;
		this.tel = tel;
		this.url = url;
		this.statut = statut;
		this.nomuserret = nomuserret;
	}
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNomstage() {
		return nomstage;
	}
	public void setNomstage(String nomstage) {
		this.nomstage = nomstage;
	}
	public String getDomaine() {
		return domaine;
	}
	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}
	public String getNomorga() {
		return nomorga;
	}
	public void setNomorga(String nomorga) {
		this.nomorga = nomorga;
	}
	public String getDescstage() {
		return descstage;
	}
	public void setDescstage(String descstage) {
		this.descstage = descstage;
	}
	public String getDatepub() {
		return datepub;
	}
	public void setDatepub(String datepub) {
		this.datepub = datepub;
	}
	public String getNivetu() {
		return nivetu;
	}
	public void setNivetu(String nivetu) {
		this.nivetu = nivetu;
	}
	public String getExprequise() {
		return exprequise;
	}
	public void setExprequise(String exprequise) {
		this.exprequise = exprequise;
	}
	public String getDatedeb() {
		return datedeb;
	}
	public void setDatedeb(String datedeb) {
		this.datedeb = datedeb;
	}
	public String getDuree() {
		return duree;
	}
	public void setDuree(String duree) {
		this.duree = duree;
	}
	public String getSalaire() {
		return salaire;
	}
	public void setSalaire(String salaire) {
		this.salaire = salaire;
	}
	public String getIndemnisation() {
		return indemnisation;
	}
	public void setIndemnisation(String indemnisation) {
		this.indemnisation = indemnisation;
	}
	public String getOrgaadrpays() {
		return orgaadrpays;
	}
	public void setOrgaadrpays(String orgaadrpays) {
		this.orgaadrpays = orgaadrpays;
	}
	public String getOrgaadrville() {
		return orgaadrville;
	}
	public void setOrgaadrville(String orgaadrville) {
		this.orgaadrville = orgaadrville;
	}
	public String getOrgaadrcp() {
		return orgaadrcp;
	}
	public void setOrgaadrcp(String orgaadrcp) {
		this.orgaadrcp = orgaadrcp;
	}
	public String getOrgaadrrue() {
		return orgaadrrue;
	}
	public void setOrgaadrrue(String orgaadrrue) {
		this.orgaadrrue = orgaadrrue;
	}
	public String getOrgaemail() {
		return orgaemail;
	}
	public void setOrgaemail(String orgaemail) {
		this.orgaemail = orgaemail;
	}
	public String getOrgatel() {
		return orgatel;
	}

	public void setOrgatel(String orgatel) {
		this.orgatel = orgatel;
	}
	public String getOrgaurl() {
		return orgaurl;
	}
	public void setOrgaurl(String orgaurl) {
		this.orgaurl = orgaurl;
	}
	public String getStageadrpays() {
		return stageadrpays;
	}
	public void setStageadrpays(String stageadrpays) {
		this.stageadrpays = stageadrpays;
	}
	public String getStageadrville() {
		return stageadrville;
	}
	public void setStageadrville(String stageadrville) {
		this.stageadrville = stageadrville;
	}
	public String getStageadrcp() {
		return stageadrcp;
	}
	public void setStageadrcp(String stageadrcp) {
		this.stageadrcp = stageadrcp;
	}
	public String getStageadrrue() {
		return stageadrrue;
	}
	public void setStageadrrue(String stageadrrue) {
		this.stageadrrue = stageadrrue;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getStatut() {
		return statut;
	}
	public void setStatut(String statut) {
		this.statut = statut;
	}
	public String getNomuserret() {
		return nomuserret;
	}
	public void setNomuserret(String nomuserret) {
		this.nomuserret = nomuserret;
	}
}
