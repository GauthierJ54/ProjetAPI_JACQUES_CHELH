package org.miage.ProjetAPI;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.miage.ProjetAPI.boundary.OffreResource;
import org.miage.ProjetAPI.entity.Offre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjetApiApplicationTests {
	
	@Autowired
	 private MockMvc mockMvc;

    @Autowired
    private OffreResource or;
   
    @Test
    public void testGetAllOffres() throws Exception {
    	Offre offre = new Offre(
    		    "1",
    		    "Stage en développement Java",
    		    "Informatique",
    		    "TechCorp",
    		    "Stage de 3 mois en développement Java",
    		    "2023-03-10",
    		    "Bac+3",
    		    "1 an d'expérience en Java",
    		    "2023-06-01",
    		    "3 mois",
    		    "1000",
    		    "Aucune",
    		    "France",
    		    "Paris",
    		    "75000",
    		    "12, Rue des Exemples",
    		    "contact@techcorp.com",
    		    "0987654321",
    		    "https://www.techcorp.com",
    		    "France",
    		    "Paris",
    		    "75000",
    		    "12, Rue des Exemples",
    		    "48.8566",
    		    "2.3522",
    		    "+33 1 23 45 67 89",
    		    "https://www.techcorp.com/stage-java",
    		    "ouvert",
    		    "John Doe"
    		);
        or.save(offre);
        
        mockMvc.perform(get("/offres"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.offreList").exists());
        or.delete(offre);
    }

    @Test
    public void testGetOffreById() throws Exception {
        Offre offre = new Offre(
    		    "1",
    		    "Stage en développement Java",
    		    "Informatique",
    		    "TechCorp",
    		    "Stage de 3 mois en développement Java",
    		    "2023-03-10",
    		    "Bac+3",
    		    "1 an d'expérience en Java",
    		    "2023-06-01",
    		    "3 mois",
    		    "1000",
    		    "Aucune",
    		    "France",
    		    "Paris",
    		    "75000",
    		    "12, Rue des Exemples",
    		    "contact@techcorp.com",
    		    "0987654321",
    		    "https://www.techcorp.com",
    		    "France",
    		    "Paris",
    		    "75000",
    		    "12, Rue des Exemples",
    		    "48.8566",
    		    "2.3522",
    		    "+33 1 23 45 67 89",
    		    "https://www.techcorp.com/stage-java",
    		    "ouvert",
    		    "John Doe"
    		);
        or.save(offre);

        mockMvc.perform(get("/offres/" + offre.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(offre.getId()));
        or.delete(offre);
    }
    
    @Test
    public void testGetOffreFilters() throws Exception {
        Offre offre = new Offre(
    		    "1",
    		    "Stage en développement Java",
    		    "DomaineTest",
    		    "TechCorp",
    		    "Stage de 3 mois en développement Java",
    		    "2023-03-10",
    		    "Bac+3",
    		    "1 an d'expérience en Java",
    		    "2023-06-01",
    		    "3 mois",
    		    "1000",
    		    "Aucune",
    		    "France",
    		    "Paris",
    		    "75000",
    		    "12, Rue des Exemples",
    		    "contact@techcorp.com",
    		    "0987654321",
    		    "https://www.techcorp.com",
    		    "France",
    		    "Paris",
    		    "75000",
    		    "12, Rue des Exemples",
    		    "48.8566",
    		    "2.3522",
    		    "+33 1 23 45 67 89",
    		    "https://www.techcorp.com/stage-java",
    		    "ouvert",
    		    "John Doe"
    		);
        or.save(offre);

        mockMvc.perform(get("/offres?domaine=DomaineTest"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.offreList").exists());
        or.delete(offre);
    }
    
    @Test
    public void testPatch() throws Exception {
        Offre offre = new Offre(
    		    "1",
    		    "Stage en développement Java",
    		    "DomaineTest",
    		    "TechCorp",
    		    "Stage de 3 mois en développement Java",
    		    "2023-03-10",
    		    "Bac+3",
    		    "1 an d'expérience en Java",
    		    "2023-06-01",
    		    "3 mois",
    		    "1000",
    		    "Aucune",
    		    "France",
    		    "Paris",
    		    "75000",
    		    "12, Rue des Exemples",
    		    "contact@techcorp.com",
    		    "0987654321",
    		    "https://www.techcorp.com",
    		    "France",
    		    "Paris",
    		    "75000",
    		    "12, Rue des Exemples",
    		    "48.8566",
    		    "2.3522",
    		    "+33 1 23 45 67 89",
    		    "https://www.techcorp.com/stage-java",
    		    "ouvert",
    		    "John Doe"
    		);
        or.save(offre);

        mockMvc.perform(patch("/offres/"+offre.getId()+"?statut=test"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.statut").value("test"));
        or.delete(offre);
    }
    
    @Test
    public void testDelete() throws Exception {
        Offre offre = new Offre(
    		    "1",
    		    "Stage en développement Java",
    		    "DomaineTest",
    		    "TechCorp",
    		    "Stage de 3 mois en développement Java",
    		    "2023-03-10",
    		    "Bac+3",
    		    "1 an d'expérience en Java",
    		    "2023-06-01",
    		    "3 mois",
    		    "1000",
    		    "Aucune",
    		    "France",
    		    "Paris",
    		    "75000",
    		    "12, Rue des Exemples",
    		    "contact@techcorp.com",
    		    "0987654321",
    		    "https://www.techcorp.com",
    		    "France",
    		    "Paris",
    		    "75000",
    		    "12, Rue des Exemples",
    		    "48.8566",
    		    "2.3522",
    		    "+33 1 23 45 67 89",
    		    "https://www.techcorp.com/stage-java",
    		    "ouvert",
    		    "John Doe"
    		);
        or.save(offre);

        mockMvc.perform(delete("/offres/"+offre.getId()))
            .andExpect(status().isNoContent());
            
        mockMvc.perform(get("/offres/"+offre.getId()))
        .andExpect(status().isNotFound());
    }
}
