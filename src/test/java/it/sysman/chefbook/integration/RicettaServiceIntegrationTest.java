package it.sysman.chefbook.integration;

import it.sysman.chefbook.dto.AutoreDto;
import it.sysman.chefbook.dto.RicettaDto;
import it.sysman.chefbook.dto.TransferRequestDto;
import it.sysman.chefbook.entity.Autore;
import it.sysman.chefbook.entity.Ricetta;
import it.sysman.chefbook.entity.TransferRequest;
import it.sysman.chefbook.repository.TransferRequestRepository;
import it.sysman.chefbook.service.AutoreService;
import it.sysman.chefbook.service.RicettaService;
import it.sysman.chefbook.utils.AutoreMapper;
import it.sysman.chefbook.utils.RicettaMapper;
import it.sysman.chefbook.utils.TransferRequestStatusEnum;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RicettaServiceIntegrationTest {

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
        a.setEmail("test6@example.com");
        a.setPassword("password");
        autoreService.addAutore(a);
        Autore autore = autoreMapper.autoreDtoToAutore(autoreService.getAutoreByEmail("test6@example.com"));
        RicettaDto r = new RicettaDto();
        r.setAutoreId(autore.getId());
        r.setNome("test1");
        r.setPrivacy(true);
        //email di autentication da verificare
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("test6@example.com");

        // Creiamo un mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Impostiamo il mock nel SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);
        ricettaService.addRicetta(r);
        RicettaDto ricetta = ricettaService.getRicettaByName("test1");
        assertThat(ricetta.getNome()).isEqualTo(r.getNome());
    }

    @Test
    void testEditRicetta(){
        AutoreDto a = new AutoreDto();
        a.setEmail("test7@example.com");
        a.setPassword("password");
        autoreService.addAutore(a);
        Autore autore = autoreMapper.autoreDtoToAutore(autoreService.getAutoreByEmail("test7@example.com"));
        RicettaDto r = new RicettaDto();
        r.setAutoreId(autore.getId());
        r.setNome("test2");
        r.setPrivacy(true);
        //email di autentication da verificare
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("test7@example.com");

        // Creiamo un mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Impostiamo il mock nel SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);
        ricettaService.addRicetta(r);
        RicettaDto ricetta = ricettaService.getRicettaByName("test2");
        ricetta.setNome("test3");
        boolean result = ricettaService.editRicetta(ricetta);
        r = ricettaService.getRicettaByName("test3");
        assertThat(result).isTrue();
        assertThat(ricetta.getNome()).isEqualTo(r.getNome());
    }

    @Test
    void testRemoveRicetta(){
        AutoreDto a = new AutoreDto();
        a.setEmail("test8@example.com");
        a.setPassword("password");
        autoreService.addAutore(a);
        Autore autore = autoreMapper.autoreDtoToAutore(autoreService.getAutoreByEmail("test8@example.com"));
        RicettaDto r = new RicettaDto();
        r.setAutoreId(autore.getId());
        r.setNome("test4");
        r.setPrivacy(true);
        //email di autentication da verificare
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("test8@example.com");

        // Creiamo un mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Impostiamo il mock nel SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);
        ricettaService.addRicetta(r);
        RicettaDto ricetta = ricettaService.getRicettaByName("test4");
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
        String email = "test9@example.com";
        AutoreDto a = new AutoreDto();
        a.setEmail(email);
        a.setPassword("password");
        autoreService.addAutore(a);
        Autore autore = autoreMapper.autoreDtoToAutore(autoreService.getAutoreByEmail(email));
        RicettaDto r = new RicettaDto();
        r.setAutoreId(autore.getId());
        r.setNome("test5");

        //email di autentication da verificare
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn(email);

        // Creiamo un mock del SecurityContext
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        // Impostiamo il mock nel SecurityContextHolder
        SecurityContextHolder.setContext(securityContext);

        ricettaService.addRicetta(r);

        RicettaDto rdto = ricettaService.getRicettaByName("test5");
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
    @Transactional
    void testAcceptTransferRicetta(){
        String token = "test-token";
        String email = "destinatario1@example.com";
        AutoreDto mittenteDto = AutoreDto.builder()
                .id(1)
                .email("mittente1@example.com")
                .password("password")
                .build();
        Autore mittente = autoreMapper.autoreDtoToAutore(mittenteDto);
        Ricetta ricetta = Ricetta.builder().id(1).autore(mittente).nome("test6").build();
        AutoreDto destinatario = AutoreDto.builder().id(2).email(email).password("password").build();
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        autoreService.addAutore(mittenteDto);
        autoreService.addAutore(destinatario);
        ricettaService.addRicetta(ricettaMapper.ricettaToRicettaDto(ricetta));
        RicettaDto r = ricettaService.getRicettaByName("test6");
        TransferRequest request = TransferRequest.builder()
                .ricetta(ricettaMapper.ricettaDtoToRicetta(r))
                .mittente("mittente1@example.com")
                .destinatario(email)
                .token(token)
                .status(TransferRequestStatusEnum.ACTIVE.getValue())
                .build();
        transferRequestRepository.save(request);
        ricettaService.acceptRicetta(token);

        TransferRequest result = transferRequestRepository.findByToken(token);

        assertEquals(TransferRequestStatusEnum.USED.getValue(), result.getStatus());
        assertThat(result.getRicetta().getAutore().getId()).isEqualTo(destinatario.getId());
    }



    @Test
    @Transactional
    void testDeclineTransferRicetta(){
        String token = "test-token";
        String email = "destinatario2@example.com";
        AutoreDto mittente = AutoreDto.builder()
                .id(1)
                .password("password")
                .email("mittente2@example.com")
                .build();
        Ricetta ricetta = Ricetta.builder().id(1).autore(autoreMapper.autoreDtoToAutore(mittente)).nome("test7").build();
        TransferRequest request = TransferRequest.builder()
                .ricetta(ricetta)
                .mittente("mittente2@example.com")
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
    @Transactional
    void testRevokeTransferRicetta(){
        String token = "test-token";
        String email = "destinatario3@example.com";
        AutoreDto dtoMittente = AutoreDto.builder()
                .id(1)
                .email("mittente3@example.com")
                .password("password")
                .build();
        Autore mittente = autoreMapper.autoreDtoToAutore(dtoMittente);
        Ricetta ricetta = Ricetta.builder().id(1).autore(mittente).nome("test8").build();
        AutoreDto destinatario = AutoreDto.builder()
                .id(2)
                .email(email)
                .password("password")
                .build();
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(email);
        autoreService.addAutore(dtoMittente);
        autoreService.addAutore(destinatario);
        RicettaDto r1 = ricettaMapper.ricettaToRicettaDto(ricetta);
        ricettaService.addRicetta(r1);
        RicettaDto r = ricettaService.getRicettaByName("test8");
        TransferRequest request = TransferRequest.builder()
                .ricetta(ricettaMapper.ricettaDtoToRicetta(r))
                .mittente(email)
                .destinatario(email)
                .token(token)
                .status(TransferRequestStatusEnum.ACTIVE.getValue())
                .build();
        transferRequestRepository.save(request);
        ricettaService.revokeTransferRicetta(token);

        TransferRequest result = transferRequestRepository.findByToken(token);

        assertEquals(TransferRequestStatusEnum.REVOKED.getValue(), result.getStatus());
    }
}
