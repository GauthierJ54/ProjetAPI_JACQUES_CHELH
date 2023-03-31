package org.miage.ProjetAPI.boundary;

import java.util.List;
import java.util.Optional;
import org.miage.ProjetAPI.entity.Offre;
import org.miage.ProjetAPI.entity.Personne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonneResource extends JpaRepository<Personne, String> {
    //JPA va fournir les SELECT, INSERT, UPDATE, DELETE
	Optional<Personne> findById(String id);
	List<Personne> findAll();
	Offre save(Offre Offre);
}