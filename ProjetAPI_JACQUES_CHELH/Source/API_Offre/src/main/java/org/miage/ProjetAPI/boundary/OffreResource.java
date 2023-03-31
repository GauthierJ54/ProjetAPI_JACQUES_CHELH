package org.miage.ProjetAPI.boundary;

import java.util.List;
import java.util.Optional;
import org.miage.ProjetAPI.entity.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface OffreResource extends JpaRepository<Offre, String>, JpaSpecificationExecutor<Offre> {
    //JPA va fournir les SELECT, INSERT, UPDATE, DELETE
	Optional<Offre> findById(String id);
	List<Offre> findAll();
	Offre save(Offre Offre);
	List<Offre> findByDomaine(String domaine);
}