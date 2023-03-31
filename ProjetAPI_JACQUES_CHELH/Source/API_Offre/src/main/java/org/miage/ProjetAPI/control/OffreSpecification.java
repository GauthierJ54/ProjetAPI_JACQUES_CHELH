package org.miage.ProjetAPI.control;

import org.miage.ProjetAPI.entity.Offre;
import org.springframework.data.jpa.domain.Specification;

public class OffreSpecification {
    public static Specification<Offre> hasDomaine(String domaine) {
        return (offre, cq, cb) -> cb.equal(offre.get("domaine"), domaine);
    }

    public static Specification<Offre> hasNomorga(String nomorga) {
        return (offre, cq, cb) -> cb.equal(offre.get("nomorga"), nomorga);
    }

    public static Specification<Offre> hasNomstage(String nomstage) {
        return (offre, cq, cb) -> cb.equal(offre.get("nomstage"), nomstage);
    }
}