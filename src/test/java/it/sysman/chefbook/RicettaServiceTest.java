package it.sysman.chefbook;

import it.sysman.chefbook.dto.RicettaDto;
import it.sysman.chefbook.dto.TransferRequestDto;
import it.sysman.chefbook.entity.Autore;
import it.sysman.chefbook.entity.Ricetta;
import it.sysman.chefbook.entity.TransferRequest;
import it.sysman.chefbook.exception.AutoreNotFoundException;
import it.sysman.chefbook.exception.ForbiddenActionException;
import it.sysman.chefbook.exception.RicettaNotFoundException;
import it.sysman.chefbook.repository.AutoreRepository;
import it.sysman.chefbook.repository.RicettaRepository;
import it.sysman.chefbook.repository.TransferRequestRepository;
import it.sysman.chefbook.service.RicettaServiceImpl;
import it.sysman.chefbook.utils.RicettaMapper;
import it.sysman.chefbook.utils.TransferRequestStatusEnum;
import it.sysman.chefbook.utils.TransferTokenGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RicettaServiceTest {
    @InjectMocks
    private RicettaServiceImpl ricettaService;

    @Mock
    private RicettaRepository ricettaRepository;

    @Mock
    private RicettaMapper ricettaMapper;

    @Mock
    private AutoreRepository autoreRepository;

    @Mock
    private TransferRequestRepository transferRequestRepository;

    @Nested
    class Incorrect{
        @Test
        public void getRicettaByNameNotFoundTest(){
            String nome = "";
            when(ricettaRepository.findByNome(nome)).thenReturn(null);


            RicettaNotFoundException exception = assertThrows(RicettaNotFoundException.class, () -> {
                ricettaService.getRicettaByName(nome);
            });

            verify(ricettaRepository).findByNome(nome);

            assertEquals("Ricetta not found", exception.getMessage());
        }

        @Test
        public void addRicettaAutoreNotFoundTest() {
            RicettaDto dto = RicettaDto.builder().id(1).build();
            Ricetta r = Ricetta.builder().id(1).build();
            String email = "test@example.com";
            Authentication authentication = mock(Authentication.class);
            SecurityContext securityContext = mock(SecurityContext.class);

            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn(email);
            SecurityContextHolder.setContext(securityContext);
            when(ricettaMapper.ricettaDtoToRicetta(dto)).thenReturn(r);
            when(autoreRepository.findByEmail(email)).thenReturn(null);

            AutoreNotFoundException exception = assertThrows(AutoreNotFoundException.class, () -> {
                ricettaService.addRicetta(dto);
            });

            verify(autoreRepository).findByEmail(email);

            assertEquals("Autore: " + email, exception.getMessage());
        }

        @Test
        public void editAndRemoveFailTest(){
            RicettaDto dto = RicettaDto.builder().id(1).build();
            Ricetta ricetta = Ricetta.builder().id(1).build();

            when(ricettaMapper.ricettaDtoToRicetta(dto)).thenReturn(ricetta);
            when(ricettaRepository.existsById(1)).thenReturn(false);
            boolean result = ricettaService.editRicetta(dto);

            assertFalse(result);
            verify(ricettaMapper).ricettaDtoToRicetta(dto);
        }

        @Test
        public void createTransferRicettaFailDestinatario(){
            String email = "destinatario@example.com";
            Ricetta ricetta = Ricetta.builder().id(1).autore(Autore.builder().email("test@example.com").build()).build();
            TransferRequestDto tdto = TransferRequestDto.builder()
                    .idRicetta(1)
                    .emailDestinatario(email)
                    .build();
            Authentication authentication = mock(Authentication.class);
            SecurityContext securityContext = mock(SecurityContext.class);
            SecurityContextHolder.setContext(securityContext);
            when(ricettaRepository.findById(1)).thenReturn(Optional.of(ricetta));

            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn(email);
            AutoreNotFoundException exception = assertThrows(AutoreNotFoundException.class, () -> {
                ricettaService.transferRicetta(tdto);
            });
            verify(securityContext).getAuthentication();
            verify(authentication).getName();
            assertEquals("Autore: " + email, exception.getMessage());
        }

        @Test
        public void createTransferRicettaFail(){
            String email = "destinatario@example.com";
            Ricetta ricetta = Ricetta.builder().id(1).autore(Autore.builder().email("test@example.com").build()).build();
            TransferRequestDto tdto = TransferRequestDto.builder()
                    .idRicetta(1)
                    .emailDestinatario(email)
                    .build();
            Autore autore = Autore.builder().email(email).build();
            Authentication authentication = mock(Authentication.class);
            SecurityContext securityContext = mock(SecurityContext.class);
            SecurityContextHolder.setContext(securityContext);

            when(ricettaRepository.findById(1)).thenReturn(Optional.of(ricetta));
            when(autoreRepository.findByEmail(tdto.getEmailDestinatario())).thenReturn(autore);
            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn(email);

            ForbiddenActionException exception = assertThrows(ForbiddenActionException.class, () -> {
                ricettaService.transferRicetta(tdto);
            });

            verify(securityContext).getAuthentication();
            verify(authentication).getName();
            verify(autoreRepository).findByEmail(email);
            assertEquals("Forbidden Action for: " + email, exception.getMessage());
        }
    }

    @Nested
    class Correct{
        @Test
        public void getRicettaByNameTest(){
            String nome = "";
            Ricetta r = Ricetta.builder().id(1).build();
            RicettaDto ricettaDto = RicettaDto.builder().id(1).build();
            when(ricettaRepository.findByNome(nome)).thenReturn(r);
            when(ricettaMapper.ricettaToRicettaDto(r)).thenReturn(ricettaDto);

            RicettaDto result = ricettaService.getRicettaByName(nome);

            verify(ricettaRepository).findByNome(nome);
            verify(ricettaMapper).ricettaToRicettaDto(r);

            assertEquals(result, ricettaDto);
        }

        @Test
        public void addRicettaTest() {
            RicettaDto dto = RicettaDto.builder().id(1).build();
            Ricetta r = Ricetta.builder().id(1).build();
            String email = "test@example.com";
            Autore autore = Autore.builder().email(email).build();
            Authentication authentication = mock(Authentication.class);
            SecurityContext securityContext = mock(SecurityContext.class);

            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn(email);
            SecurityContextHolder.setContext(securityContext);

            when(ricettaMapper.ricettaDtoToRicetta(dto)).thenReturn(r);
            when(autoreRepository.findByEmail(email)).thenReturn(autore);

            ricettaService.addRicetta(dto);

            verify(ricettaMapper).ricettaDtoToRicetta(dto);
            verify(securityContext).getAuthentication();
            verify(authentication).getName();
            verify(autoreRepository).findByEmail(email);
            verify(ricettaRepository).save(r);

            assertEquals(autore, r.getAutore());
        }

        @Test
        public void getAllRicetteTest() {
            Ricetta ricetta1 = Ricetta.builder().id(1).build();
            Ricetta ricetta2 = Ricetta.builder().id(2).build();
            List<Ricetta> ricette = Arrays.asList(ricetta1, ricetta2);

            RicettaDto ricettaDto1 = RicettaDto.builder().id(1).build();
            RicettaDto ricettaDto2 = RicettaDto.builder().id(2).build();
            List<RicettaDto> ricetteDto = Arrays.asList(ricettaDto1, ricettaDto2);

            when(ricettaRepository.findAll()).thenReturn(ricette);
            when(ricettaMapper.ricetteToRicetteDto(ricette)).thenReturn(ricetteDto);

            List<RicettaDto> result = ricettaService.getAllRicette();

            verify(ricettaRepository).findAll();
            verify(ricettaMapper).ricetteToRicetteDto(ricette);

            assertEquals(2, result.size());
            assertEquals(ricettaDto1, result.get(0));
            assertEquals(ricettaDto2, result.get(1));
        }

        @Test
        public void editRicettaTest(){
            RicettaDto dto = RicettaDto.builder().id(1).build();
            Ricetta ricetta = Ricetta.builder().id(1).build();

            when(ricettaMapper.ricettaDtoToRicetta(dto)).thenReturn(ricetta);
            when(ricettaRepository.existsById(1)).thenReturn(true);
            when(ricettaRepository.save(ricetta)).thenReturn(ricetta);
            boolean result = ricettaService.editRicetta(dto);

            assertTrue(result);
            verify(ricettaMapper).ricettaDtoToRicetta(dto);
        }

        @Test
        public void removeRicettaTest(){
            Ricetta ricetta = Ricetta.builder().id(1).build();

            when(ricettaRepository.existsById(1)).thenReturn(true);
            doNothing().when(ricettaRepository).deleteById(ricetta.getId());
            boolean result = ricettaService.removeRicetta(ricetta.getId());

            assertTrue(result);
        }

        @Test
        public void createTransferRicettaTest(){
            String email = "mittente@example.com";
            Ricetta ricetta = Ricetta.builder().id(1).autore(Autore.builder().email(email).build()).build();
            TransferRequestDto tdto = TransferRequestDto.builder()
                    .idRicetta(1)
                    .emailDestinatario(email)
                    .build();
            Autore autore = Autore.builder().email(email).build();
            Authentication authentication = mock(Authentication.class);
            SecurityContext securityContext = mock(SecurityContext.class);
            SecurityContextHolder.setContext(securityContext);

            when(securityContext.getAuthentication()).thenReturn(authentication);
            when(authentication.getName()).thenReturn(email);

            when(authentication.getName()).thenReturn(email);
            when(ricettaRepository.findById(1)).thenReturn(Optional.of(ricetta));
            when(autoreRepository.findByEmail(tdto.getEmailDestinatario())).thenReturn(autore);

            ricettaService.transferRicetta(tdto);

            verify(securityContext).getAuthentication();
            verify(authentication).getName();
            verify(autoreRepository).findByEmail(email);
        }
    }
}
