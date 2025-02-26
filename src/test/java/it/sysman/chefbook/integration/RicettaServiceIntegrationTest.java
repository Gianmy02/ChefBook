package it.sysman.chefbook.integration;

import it.sysman.chefbook.dto.AutoreDto;
import it.sysman.chefbook.dto.RicettaDto;
import it.sysman.chefbook.dto.TransferRequestDto;
import it.sysman.chefbook.entity.Autore;
import it.sysman.chefbook.entity.Ricetta;
import it.sysman.chefbook.entity.TransferRequest;
import it.sysman.chefbook.repository.TransferRequestRepository;
import it.sysman.chefbook.repository.UserRepository;
import it.sysman.chefbook.service.AutoreService;
import it.sysman.chefbook.service.RicettaService;
import it.sysman.chefbook.utils.AutoreMapper;
import it.sysman.chefbook.utils.RicettaMapper;
import it.sysman.chefbook.utils.TransferRequestStatusEnum;
import jakarta.persistence.*;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    @Autowired
    private TransferRequestRepository transferRequestRepository;

    @Autowired
    private RicettaMapper ricettaMapper;

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

    @Test
    void testCreateTransferRicetta(){
        String email = "test@example.com";
        AutoreDto a = new AutoreDto();
        a.setEmail(email);
        a.setPassword("password");
        autoreService.addAutore(a);
        Autore autore = autoreMapper.autoreDtoToAutore(autoreService.getAutoreByEmail(email));
        RicettaDto r = new RicettaDto();
        r.setAutoreId(autore.getId());
        r.setNome("test");

        //email di autentication da verificare
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn(email);

        // Creiamo un mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Impostiamo il mock nel SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);

        ricettaService.addRicetta(r);

        RicettaDto rdto = ricettaService.getRicettaByName("test");
        TransferRequestDto tdto = TransferRequestDto
                .builder()
                .idRicetta(rdto.getId())
                .emailDestinatario(email)
                .build();

        ricettaService.transferRicetta(tdto);
        TransferRequest request = transferRequestRepository.findByRicettaId(ricettaMapper.ricettaDtoToRicetta(rdto).getId());
        assertThat(request.getRicetta().getId()).isEqualTo(ricettaMapper.ricettaDtoToRicetta(rdto).getId());
    }

    @Test
    void testAcceptTransferRicetta(){
        String token = "test-token";
        String email = "destinatario@example.com";
        Autore mittente = Autore.builder()
                .id(1)
                .email("mittente@example.com")
                .build();
        Ricetta ricetta = Ricetta.builder().id(1).autore(mittente).nome("test").build();
        TransferRequest request = TransferRequest.builder()
                .ricetta(ricetta)
                .mittente("mittente@example.com")
                .destinatario(email)
                .token(token)
                .status(TransferRequestStatusEnum.ACTIVE.getValue())
                .build();
        AutoreDto destinatario = AutoreDto.builder().id(2).email(email).password("password").build();
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        autoreService.addAutore(destinatario);
        ricettaService.addRicetta(ricettaMapper.ricettaToRicettaDto(ricetta));
        transferRequestRepository.save(request);
        ricettaService.acceptRicetta(token);

        TransferRequest result = transferRequestRepository.findByToken(token);

        assertEquals(TransferRequestStatusEnum.USED.getValue(), result.getStatus());
        //assertThat(result.getRicetta().getAutore().getId()).isEqualTo(destinatario.getId());
    }

    @Test
    void testDeclineTransferRicetta(){
        String token = "test-token";
        String email = "destinatario@example.com";
        Autore mittente = Autore.builder()
                .id(1)
                .email("mittente@example.com")
                .build();
        Ricetta ricetta = Ricetta.builder().id(1).autore(mittente).nome("test").build();
        TransferRequest request = TransferRequest.builder()
                .ricetta(ricetta)
                .mittente("mittente@example.com")
                .destinatario(email)
                .token(token)
                .status(TransferRequestStatusEnum.ACTIVE.getValue())
                .build();
        AutoreDto destinatario = AutoreDto.builder().id(2).email(email).password("password").build();
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        autoreService.addAutore(destinatario);
        ricettaService.addRicetta(ricettaMapper.ricettaToRicettaDto(ricetta));
        transferRequestRepository.save(request);
        ricettaService.declineRicetta(token);

        TransferRequest result = transferRequestRepository.findByToken(token);

        assertEquals(TransferRequestStatusEnum.DECLINED.getValue(), result.getStatus());
        assertThat(result.getRicetta().getAutore().getId()).isEqualTo(mittente.getId());
    }

    @Test
    void testRevokeTransferRicetta(){
        String token = "test-token";
        String email = "destinatario@example.com";
        Autore mittente = Autore.builder()
                .id(1)
                .email("mittente@example.com")
                .build();
        Ricetta ricetta = Ricetta.builder().id(1).autore(mittente).nome("test").build();
        TransferRequest request = TransferRequest.builder()
                .ricetta(ricetta)
                .mittente(email)
                .destinatario(email)
                .token(token)
                .status(TransferRequestStatusEnum.ACTIVE.getValue())
                .build();
        AutoreDto destinatario = AutoreDto.builder().id(2).email(email).password("password").build();
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        autoreService.addAutore(destinatario);
        ricettaService.addRicetta(ricettaMapper.ricettaToRicettaDto(ricetta));
        transferRequestRepository.save(request);
        ricettaService.revokeTransferRicetta(token);

        TransferRequest result = transferRequestRepository.findByToken(token);

        assertEquals(TransferRequestStatusEnum.REVOKED.getValue(), result.getStatus());
        //assertThat(result.getRicetta().getAutore().getId()).isEqualTo(mittente.getId());
    }
}
