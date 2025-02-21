package it.sysman.chefbook.integration;

import it.sysman.chefbook.dto.AutoreDto;
import it.sysman.chefbook.dto.RicettaDto;
import it.sysman.chefbook.entity.Autore;
import it.sysman.chefbook.exception.RicettaNotFoundException;
import it.sysman.chefbook.repository.UserRepository;
import it.sysman.chefbook.service.AutoreService;
import it.sysman.chefbook.service.RicettaService;
import it.sysman.chefbook.utils.AutoreMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class RicettaServiceIntegrationTest {
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("cb")
            .withUsername("chefbook")
            .withPassword("chefbook");

    @BeforeAll
    static void startContainer() {
        mySQLContainer.start();
    }

    @AfterAll
    static void stopContainer() {
        mySQLContainer.stop();
    }

    // Configura il datasource dinamicamente
    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RicettaService ricettaService;

    @Autowired
    private AutoreService autoreService;

    @Autowired
    private AutoreMapper autoreMapper;

    @Test
    void testSaveAndRetrieveRicetta() {
        AutoreDto a = new AutoreDto();
        a.setEmail("test@example.com");
        a.setPassword("password");
        autoreService.addAutore(a);
        Autore autore = autoreMapper.autoreDtoToAutore(autoreService.getAutoreByEmail("test@example.com"));
        RicettaDto r = new RicettaDto();
        r.setAutoreId(autore.getId());
        r.setNome("test");
        r.setPrivacy(true);
        //email di autentication da verificare
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("test@example.com");

        // Creiamo un mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Impostiamo il mock nel SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);
        ricettaService.addRicetta(r);
        RicettaDto ricetta = ricettaService.getRicettaByName("test");
        assertThat(ricetta.getNome()).isEqualTo(r.getNome());
    }

    @Test
    void testEditRicetta(){
        AutoreDto a = new AutoreDto();
        a.setEmail("test@example.com");
        a.setPassword("password");
        autoreService.addAutore(a);
        Autore autore = autoreMapper.autoreDtoToAutore(autoreService.getAutoreByEmail("test@example.com"));
        RicettaDto r = new RicettaDto();
        r.setAutoreId(autore.getId());
        r.setNome("test");
        r.setPrivacy(true);
        //email di autentication da verificare
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("test@example.com");

        // Creiamo un mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Impostiamo il mock nel SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);
        ricettaService.addRicetta(r);
        RicettaDto ricetta = ricettaService.getRicettaByName("test");
        ricetta.setNome("test2");
        boolean result = ricettaService.editRicetta(ricetta);
        r = ricettaService.getRicettaByName("test2");
        assertThat(result).isTrue();
        assertThat(ricetta.getNome()).isEqualTo(r.getNome());
    }

    @Test
    void testRemoveRicetta(){
        AutoreDto a = new AutoreDto();
        a.setEmail("test@example.com");
        a.setPassword("password");
        autoreService.addAutore(a);
        Autore autore = autoreMapper.autoreDtoToAutore(autoreService.getAutoreByEmail("test@example.com"));
        RicettaDto r = new RicettaDto();
        r.setAutoreId(autore.getId());
        r.setNome("test");
        r.setPrivacy(true);
        //email di autentication da verificare
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("test@example.com");

        // Creiamo un mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Impostiamo il mock nel SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);
        ricettaService.addRicetta(r);
        RicettaDto ricetta = ricettaService.getRicettaByName("test");
        boolean result = ricettaService.removeRicetta(ricetta.getId());
        assertThat(result).isTrue();
    }

    @Test
    void testRemoveOrEditRicettaFail(){
        int id = -1;
        boolean result = ricettaService.removeRicetta(id);
        assertThat(result).isFalse();
    }
}
