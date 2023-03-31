package org.miage.ProjetAPI.boundary;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.miage.ProjetAPI.control.OffreSpecification;
import org.miage.ProjetAPI.entity.Candidature;
import org.miage.ProjetAPI.entity.Offre;
import org.miage.ProjetAPI.entity.Personne;
import org.miage.ProjetAPI.entity.Recrutement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.data.jpa.domain.Specification.where;

@Controller
@ResponseBody
@RequestMapping(value = "/offres", produces = MediaType.APPLICATION_JSON_VALUE)
public class OffreRepresentation {
    
    private final OffreResource or;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final CandidatureResource cr;
    private final RecrutementResource rr;

    @Autowired
    public OffreRepresentation(OffreResource or, CandidatureResource cr, RecrutementResource rr) {
        this.or = or;
        this.cr = cr;
        this.rr = rr;
    }
        
    @GetMapping(value = "/{offreId}")
    public EntityModel<Offre> getOneById(@PathVariable("offreId") String id){
    	Optional<Offre> offreOpt = or.findById(id);

        if (offreOpt.isPresent()) {
            Offre offre = offreOpt.get();
            Link selfLink = linkTo(OffreRepresentation.class).slash(offre.getId()).withSelfRel();
            return EntityModel.of(offre, selfLink);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offre introuvable");
        }
    }
    
    @GetMapping(value = "/{offreId}/personnes")
    public CollectionModel<Personne> getCandidatureByOffre(@PathVariable("offreId") String id){
    	Optional<Offre> offreOpt = or.findById(id);
    	List<Candidature> lsCandidature = cr.findAll();
    	List<Personne> lsPers = new ArrayList<>();

        if (offreOpt.isPresent()) {
            Offre offre = offreOpt.get();
            for (Candidature cand : lsCandidature) {
            	if(cand.getIdoffre().equals(offre.getId())) {
            		try {
                		String url = "http://localhost:8081/personnes/" + cand.getIdpersonne();              		
                		HttpRequest httpRequest = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .GET()
                                .build();
                        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                        
                        ObjectMapper objectMapper = new ObjectMapper();
                        Map<String, Object> responseMap = objectMapper.readValue(httpResponse.body(), Map.class);
                        
                        int statusCode = httpResponse.statusCode();
                        if (statusCode == 200) {
                        	Personne pers = new Personne(
                        			responseMap.get("id").toString(), 
                        			responseMap.get("prenom").toString(), 
                        			responseMap.get("nom").toString(), 
                        			responseMap.get("niveau").toString(), 
                        			responseMap.get("type").toString());
                        	Link selfLink = Link.of("http://localhost:8081/personnes/" + responseMap.get("id").toString());
                        	pers.add(selfLink);
                        	lsPers.add(pers);
                        } else {
                        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne introuvable");
                        }
                        
                	}catch (Exception e) {
                        e.printStackTrace();
                    }
            	}
            }
            
            Link link = linkTo(OffreRepresentation.class).slash(id).slash("personnes").withSelfRel();
            return CollectionModel.of(lsPers, link);
            
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offre introuvable");
        }
    }
    
    @GetMapping
    public CollectionModel<Offre> getAllOffres(
    		@RequestParam(value = "domaine", required = false) String domaine,
    	    @RequestParam(value = "nomorga", required = false) String nomorga,
    	    @RequestParam(value = "nomstage", required = false) String nomstage){
    	
    	Specification<Offre> spec = where(null);

        if (domaine != null) {
            spec = spec.and(OffreSpecification.hasDomaine(domaine));
        }
        if (nomorga != null) {
            spec = spec.and(OffreSpecification.hasNomorga(nomorga));
        }
        if (nomstage != null) {
            spec = spec.and(OffreSpecification.hasNomstage(nomstage));
        }

        List<Offre> lsOffre = or.findAll(spec);

    	List<Offre> lsOffreRes = new ArrayList<>();
    	
    	for (Offre ofr : lsOffre) {
    		if(ofr.getStatut().equals("ouvert")) {
    			String ofrId = ofr.getId();
                Link selfLink = linkTo(OffreRepresentation.class).slash(ofrId).withSelfRel();
                ofr.add(selfLink);
                lsOffreRes.add(ofr);
    		}
            
        } 
    	Link link = linkTo(OffreRepresentation.class).withSelfRel();
        CollectionModel<Offre> result = CollectionModel.of(lsOffreRes, link);
        return result;
    }
    
    @GetMapping(value="/recrutement/{idoffre}")
    public EntityModel<Recrutement> getRecrutement(@PathVariable("idoffre") String idOffre){
    	Optional<Offre> offreOpt = or.findById(idOffre);
    	List<Candidature> lsCandidature = cr.findAll();
    	int nbCand = 0;
    	String idcands = "";
    	Recrutement rct = null;
        if (offreOpt.isPresent()) {
            Offre offre = offreOpt.get();
            for (Candidature cand : lsCandidature) {
            	if(cand.getIdoffre().equals(offre.getId())) {
            		nbCand++;
            		if(idcands.equals("")) {
            			idcands += cand.getId();
            		}else {
            			idcands+= " / " + cand.getId();
            		}
            	}
            }
            offre.setStatut("en recrutement");
            or.save(offre);
            rct = new Recrutement(UUID.randomUUID().toString(), offre.getNomstage(), idcands , Integer.toString(nbCand));
            rr.save(rct);
            Link link = linkTo(OffreRepresentation.class).slash("recrutement").slash(idOffre).withSelfRel();
            return EntityModel.of(rct, link);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offre introuvable");
        }
    }
    
    @PostMapping
    public ResponseEntity<EntityModel<Offre>> postOffre(@RequestBody Offre newOffre) {
    	newOffre.setId(UUID.randomUUID().toString());
    	newOffre.setStatut("ouvert");
    	newOffre.setNomuserret("nobody");
        Offre savedOffre = or.save(newOffre);
        Link selfLink = linkTo(OffreRepresentation.class).slash(savedOffre.getId()).withSelfRel();
        EntityModel<Offre> entityModel = EntityModel.of(savedOffre, selfLink);

        return ResponseEntity.created(selfLink.toUri()).body(entityModel);
    }
    
    @PostMapping(value = "/{offreId}")
    public ResponseEntity<EntityModel<Candidature>> postulerOffre(@RequestParam String idPersonne, @PathVariable("offreId") String idOffre) {
    	Link selfLink = null;
    	EntityModel<Candidature> entityModel = null;
    	Optional<Offre> offreOpt = or.findById(idOffre);
    	try {
    		String url = "http://localhost:8081/personnes/" + idPersonne;
    		HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            
            int statusCode = httpResponse.statusCode();
            if (statusCode == 200 && offreOpt.isPresent()) {
            	Candidature newCand = new Candidature(UUID.randomUUID().toString(), idOffre, idPersonne);
                Candidature savedCand = cr.save(newCand);
                selfLink = linkTo(OffreRepresentation.class).slash(idOffre).withSelfRel();
                entityModel = EntityModel.of(savedCand, selfLink);       
            } 
            
    	}catch (Exception e) {
            e.printStackTrace();
        }
    	if(selfLink == null) {
    		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne ou Offre introuvable");
    	}
    	return ResponseEntity.created(selfLink.toUri()).body(entityModel);
        
    }

    @PatchMapping("/{id}")
    public ResponseEntity<EntityModel<Offre>> patchStatut(@PathVariable("id") String id, @RequestParam("statut") String newStatut) {
        Optional<Offre> offreOpt = or.findById(id);

        if (offreOpt.isPresent()) {
            Offre offre = offreOpt.get();
            offre.setStatut(newStatut);
            Offre updatedOffre = or.save(offre);

            Link selfLink = linkTo(OffreRepresentation.class).slash(updatedOffre.getId()).withSelfRel();
            EntityModel<Offre> entityModel = EntityModel.of(updatedOffre, selfLink);

            return ResponseEntity
            		.ok(entityModel);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offre introuvable");
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> SupprOffre(@PathVariable String id) {
        Optional<Offre> existingOffre = or.findById(id);
        if (existingOffre.isPresent()) {
            or.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Offre> MAJOffre(@PathVariable String id, @RequestBody Offre updatedOffre) {
        Optional<Offre> existingOffre = or.findById(id);
        if (existingOffre.isPresent()) {
            Offre offre = existingOffre.get();
            offre.setNomstage(updatedOffre.getNomstage());
            offre.setNomorga(updatedOffre.getNomorga());
            offre.setDomaine(updatedOffre.getDomaine());
            offre.setDescstage(updatedOffre.getDescstage());
            offre.setDatepub(updatedOffre.getDatepub());
            offre.setNivetu(updatedOffre.getNivetu());
            offre.setExprequise(updatedOffre.getExprequise());
            offre.setDatedeb(updatedOffre.getDatedeb());
            offre.setDuree(updatedOffre.getDuree());
            offre.setSalaire(updatedOffre.getSalaire());
            offre.setIndemnisation(updatedOffre.getIndemnisation());
            offre.setOrgaadrpays(updatedOffre.getOrgaadrpays());
            offre.setOrgaadrville(updatedOffre.getOrgaadrville());
            offre.setOrgaadrcp(updatedOffre.getOrgaadrcp());
            offre.setOrgaadrrue(updatedOffre.getOrgaadrrue());
            offre.setOrgaemail(updatedOffre.getOrgaemail());
            offre.setOrgatel(updatedOffre.getOrgatel());
            offre.setOrgaurl(updatedOffre.getOrgaurl());
            offre.setStageadrpays(updatedOffre.getStageadrpays());
            offre.setStageadrville(updatedOffre.getStageadrville());
            offre.setStageadrrue(updatedOffre.getStageadrrue());
            offre.setStageadrcp(updatedOffre.getStageadrcp());
            offre.setLatitude(updatedOffre.getLatitude());
            offre.setLongitude(updatedOffre.getLongitude());
            offre.setTel(updatedOffre.getTel());
            offre.setUrl(updatedOffre.getUrl());
            offre.setNomuserret(updatedOffre.getNomuserret());

            Offre savedOffre = or.save(offre);
            return ResponseEntity.ok(savedOffre);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}





