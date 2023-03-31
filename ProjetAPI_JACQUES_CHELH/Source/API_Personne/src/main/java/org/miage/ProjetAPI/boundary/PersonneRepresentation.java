package org.miage.ProjetAPI.boundary;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.miage.ProjetAPI.entity.Candidature;
import org.miage.ProjetAPI.entity.Offre;
import org.miage.ProjetAPI.entity.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Controller
@ResponseBody
@RequestMapping(value = "/personnes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonneRepresentation {
    
    private final PersonneResource or;
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final CandidatureResource cr;

    @Autowired
    public PersonneRepresentation(PersonneResource or, CandidatureResource cr) {
        this.or = or;
        this.cr = cr;
    }
        
    @GetMapping(value = "/{PersonneId}")
    public EntityModel<Personne> getOneById(@PathVariable("PersonneId") String id){
    	Optional<Personne> PersonneOpt = or.findById(id);

        if (PersonneOpt.isPresent()) {
            Personne Personne = PersonneOpt.get();
            Link selfLink = linkTo(PersonneRepresentation.class).slash(Personne.getId()).withSelfRel();

            return EntityModel.of(Personne, selfLink);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne introuvable");
        }
    }
    
    @GetMapping("/{idPersonne}/candidature/{idOffre}")
    public EntityModel<Offre> StatutOffre(@PathVariable String idPersonne, @PathVariable String idOffre) {
    	List<Candidature> lsCand = cr.findAll();
    	Offre ofr = null;
    	Candidature existingCand = null;
    	for (Candidature cand : lsCand) {
    		if (cand.getIdoffre().equals(idOffre) && cand.getIdpersonne().equals(idPersonne)) {
    			existingCand = cand;
    			try {
    				String url = "http://localhost:8082/offres/" + cand.getIdoffre();
    	    		HttpRequest httpRequest = HttpRequest.newBuilder()
    	                    .uri(URI.create(url))
    	                    .GET()
    	                    .build();
    	            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
    	            
    	            ObjectMapper objectMapper = new ObjectMapper();
                    Map<String, Object> responseMap = objectMapper.readValue(httpResponse.body(), Map.class);
    	            
    	            int statusCode = httpResponse.statusCode();
    	            if (statusCode == 200) {
    	            	ofr = (new Offre(
    	            			responseMap.get("id").toString(), 
    	            			responseMap.get("nomstage").toString(), 
    	            			responseMap.get("domaine").toString(), 
    	            			responseMap.get("nomorga").toString(), 
    	            			responseMap.get("descstage").toString(),
    	            			responseMap.get("datepub").toString(),
    	            			responseMap.get("nivetu").toString(),
    	            			responseMap.get("exprequise").toString(),
    	            			responseMap.get("datedeb").toString(),
    	            			responseMap.get("duree").toString(),
    	            			responseMap.get("salaire").toString(),
    	            			responseMap.get("indemnisation").toString(),
    	            			responseMap.get("orgaadrpays").toString(),
    	            			responseMap.get("orgaadrville").toString(),
    	            			responseMap.get("orgaadrcp").toString(),
    	            			responseMap.get("orgaadrrue").toString(),
    	            			responseMap.get("orgaemail").toString(),
    	            			responseMap.get("orgatel").toString(),
    	            			responseMap.get("orgaurl").toString(),
    	            			responseMap.get("stageadrpays").toString(),
    	            			responseMap.get("stageadrville").toString(),
    	            			responseMap.get("stageadrcp").toString(),
    	            			responseMap.get("stageadrrue").toString(),
    	            			responseMap.get("latitude").toString(),
    	            			responseMap.get("longitude").toString(),
    	            			responseMap.get("tel").toString(),
    	            			responseMap.get("url").toString(),
    	            			responseMap.get("statut").toString(),
    	            			responseMap.get("nomuserret").toString()));
    	            	ofr.add(Link.of("http://localhost:8082/offres/" + ofr.getId()));
                    } else {
                    	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne introuvable");
                    } 
    			}
	            catch (Exception e) {
	            	e.printStackTrace();
				}
    		}
    	}
        if (ofr != null) {
        	Link selfLink = linkTo(PersonneRepresentation.class).slash(idPersonne).slash("candidature").slash(idOffre).withSelfRel();
        	return EntityModel.of(ofr, selfLink);
        } else {
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offre ou candidature introuvable");
        }
    }
    
    @GetMapping(value = "/{PersonneId}/candidature")
    public CollectionModel<Offre> getCandidatureByIdPers(@PathVariable("PersonneId") String id){
    	List<String> lsIdOffre = new ArrayList<>();
    	List<Offre> lsOffre = new ArrayList<>();
    	List<Candidature> lsCand = cr.findAll();
    	for(Candidature cand : lsCand) {
    		if(cand.getIdpersonne().equals(id)) {
    			lsIdOffre.add(cand.getIdoffre());
    		}
    	}
    	for (String s : lsIdOffre) {
    		try {
	    		String url = "http://localhost:8082/offres/" + s;
	    		HttpRequest httpRequest = HttpRequest.newBuilder()
	                    .uri(URI.create(url))
	                    .GET()
	                    .build();
	            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
	            
	            ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> responseMap = objectMapper.readValue(httpResponse.body(), Map.class);
	            
	            int statusCode = httpResponse.statusCode();
	            if (statusCode == 200) {
	            	Offre ofr = new Offre(
	            			responseMap.get("id").toString(), 
	            			responseMap.get("nomstage").toString(), 
	            			responseMap.get("domaine").toString(), 
	            			responseMap.get("nomorga").toString(), 
	            			responseMap.get("descstage").toString(),
	            			responseMap.get("datepub").toString(),
	            			responseMap.get("nivetu").toString(),
	            			responseMap.get("exprequise").toString(),
	            			responseMap.get("datedeb").toString(),
	            			responseMap.get("duree").toString(),
	            			responseMap.get("salaire").toString(),
	            			responseMap.get("indemnisation").toString(),
	            			responseMap.get("orgaadrpays").toString(),
	            			responseMap.get("orgaadrville").toString(),
	            			responseMap.get("orgaadrcp").toString(),
	            			responseMap.get("orgaadrrue").toString(),
	            			responseMap.get("orgaemail").toString(),
	            			responseMap.get("orgatel").toString(),
	            			responseMap.get("orgaurl").toString(),
	            			responseMap.get("stageadrpays").toString(),
	            			responseMap.get("stageadrville").toString(),
	            			responseMap.get("stageadrcp").toString(),
	            			responseMap.get("stageadrrue").toString(),
	            			responseMap.get("latitude").toString(),
	            			responseMap.get("longitude").toString(),
	            			responseMap.get("tel").toString(),
	            			responseMap.get("url").toString(),
	            			responseMap.get("statut").toString(),
	            			responseMap.get("nomuserret").toString());
	            	ofr.add(Link.of("http://localhost:8082/offres/" + ofr.getId()));
	            	lsOffre.add(ofr);
                } else {
                	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne introuvable");
                } 
	    	}catch (Exception e) {
	            e.printStackTrace();
	        }
    	}
        if (!lsOffre.isEmpty()) {
        	Link link = linkTo(Personne.class).slash(id).slash("candidature").withSelfRel();
            return CollectionModel.of(lsOffre, link);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne introuvable");
        }
    }
    	
    @GetMapping
    public CollectionModel<Personne> getAllPersonnes(){
    	List<Personne> lsPersonne = or.findAll();
    	
    	for (Personne ofr : lsPersonne) {
            String ofrId = ofr.getId();
            Link selfLink = linkTo(PersonneRepresentation.class).slash(ofrId).withSelfRel();
            ofr.add(selfLink);
        } 
    	Link link = linkTo(PersonneRepresentation.class).withSelfRel();
        CollectionModel<Personne> result = CollectionModel.of(lsPersonne, link);
        return result;
    }
    
    @PostMapping
    public ResponseEntity<EntityModel<Personne>> postPersonne(@RequestBody Personne newPersonne) {
    	newPersonne.setId(UUID.randomUUID().toString());
    	newPersonne.setType("user");
        Personne savedPersonne = or.save(newPersonne);
        Link selfLink = linkTo(PersonneRepresentation.class).slash(savedPersonne.getId()).withSelfRel();
        EntityModel<Personne> entityModel = EntityModel.of(savedPersonne, selfLink);

        return ResponseEntity
                .created(selfLink.toUri())
                .body(entityModel);
    }
    
    @DeleteMapping("/{idPersonne}/candidature/{idOffre}")
    public ResponseEntity<Void> SupprOffre(@PathVariable String idPersonne, @PathVariable String idOffre) {
    	List<Candidature> lsCand = cr.findAll();
    	Candidature existingCand = null;
    	for (Candidature cand : lsCand) {
    		if (cand.getIdoffre().equals(idOffre) && cand.getIdpersonne().equals(idPersonne)) {
    			existingCand = cand;
    		}
    	}
    	System.out.println(existingCand);
        if (existingCand != null) {
            cr.deleteById(existingCand.getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}





