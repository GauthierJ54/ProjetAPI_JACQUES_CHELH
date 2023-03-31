package org.miage.ProjetAPI.boundary;

import java.util.List;
import java.util.Optional;
import org.miage.ProjetAPI.entity.Recrutement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecrutementResource extends JpaRepository<Recrutement, String> {
    //JPA va fournir les SELECT, INSERT, UPDATE, DELETE
	Optional<Recrutement> findById(String id);
	List<Recrutement> findAll();
	Recrutement save(Recrutement Recrutement);
}