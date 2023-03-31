package org.miage.ProjetAPI.boundary;

import java.util.List;
import java.util.Optional;

import org.miage.ProjetAPI.entity.Candidature;
import org.miage.ProjetAPI.entity.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidatureResource extends JpaRepository<Candidature, String> {
    //JPA va fournir les SELECT, INSERT, UPDATE, DELETE
	Optional<Candidature> findById(String id);
	List<Candidature> findAll();
	Candidature save(Offre Candidature);
}