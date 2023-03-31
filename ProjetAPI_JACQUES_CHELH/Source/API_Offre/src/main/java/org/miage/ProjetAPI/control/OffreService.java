package org.miage.ProjetAPI.control;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.miage.ProjetAPI.boundary.OffreResource;
import org.miage.ProjetAPI.entity.Offre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OffreService {
	
	private OffreResource or;

    @Autowired
    public OffreService(OffreResource or) {
        this.or = or;
    }

    public String getOneOffre(String id){
        Optional<Offre> offre = or.findById(id);
        String result="{ID : "+offre.get().getId().toString()+",NomStage : "+offre.get().getNomstage()+",NomOrga :"+ offre.get().getNomorga()+",Domaine : "+offre.get().getDomaine()+"}";

        return result;
    }
    
    public List<Offre> getAllOffre() {
    	List<Offre> lsoffre = or.findAll();
    	String res = "";
    	for (Offre offre : lsoffre) {
			res += "{ID : "+offre.getId().toString()+",NomStage : "+offre.getNomstage()+",NomOrga :"+ offre.getNomorga()+",Domaine : "+offre.getDomaine()+"}";
		}
    	return lsoffre;
    }
    
    public Offre saveOneOffre(Offre offre) {
    	return or.save(offre);
    }
}
