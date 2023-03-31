package org.miage.ProjetAPI;

import org.junit.jupiter.api.Test;
import org.miage.ProjetAPI.boundary.PersonneResource;
import org.miage.ProjetAPI.entity.Offre;
import org.miage.ProjetAPI.entity.Personne;
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
    private PersonneResource pr;
   

    @Test
    public void testGetAllPersonnes() throws Exception {
        mockMvc.perform(get("/personnes"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$._embedded.personneList").exists());
    }

    @Test
    public void testGetOffreById() throws Exception {
        Personne pers = new Personne("1", "testPrenom", "testNom", "Bac +3", "user");
        pr.save(pers);

        mockMvc.perform(get("/personnes/" + pers.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(pers.getId()));
        pr.delete(pers);
    }
}
