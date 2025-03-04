package it.sysman.chefbook.unit;

import it.sysman.chefbook.dto.AutoreDto;
import it.sysman.chefbook.entity.Autore;
import it.sysman.chefbook.entity.Role;
import it.sysman.chefbook.entity.User;
import it.sysman.chefbook.exception.AutoreNotFoundException;
import it.sysman.chefbook.repository.AutoreRepository;
import it.sysman.chefbook.repository.UserRepository;
import it.sysman.chefbook.service.AutoreServiceImpl;
import it.sysman.chefbook.service.RoleService;
import it.sysman.chefbook.utils.AutoreMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AutoreServiceTest {

    @InjectMocks
    private AutoreServiceImpl autoreService;

    @Mock
    private AutoreRepository autoreRepository;

    @Mock
    private AutoreMapper autoreMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Nested
    class Incorrect{
        @Test
        public void getAutoreByNameNotFoundTest() {
            String nome = "";
            when(autoreRepository.findByNome(nome)).thenReturn(null);

            AutoreNotFoundException exception = assertThrows(AutoreNotFoundException.class, () -> {
                autoreService.getAutoreByName(nome);
            });

            verify(autoreRepository).findByNome(nome);

            assertEquals("Autore non trovato", exception.getMessage());
        }

        @Test
        public void getAutoreByEmailNotFoundTest() {
            String email = "";
            when(autoreRepository.findByEmail(email)).thenReturn(null);

            AutoreNotFoundException exception = assertThrows(AutoreNotFoundException.class, () -> {
                autoreService.getAutoreByEmail(email);
            });

            verify(autoreRepository).findByEmail(email);

            assertEquals("Autore non trovato", exception.getMessage());
        }

        @Test
        public void editAndRemoveFailTest(){
            AutoreDto dto = AutoreDto.builder().id(1).build();
            Autore autore = Autore.builder().id(1).build();

            when(autoreMapper.autoreDtoToAutore(dto)).thenReturn(autore);
            when(autoreRepository.existsById(1)).thenReturn(false);
            boolean result = autoreService.editAutore(dto);

            assertFalse(result);
            verify(autoreMapper).autoreDtoToAutore(dto);
        }
    }

    @Nested
    class Correct{
        @Test
        public void getAutoreByNameTest() {
            String nome = "";
            Autore a = Autore.builder().id(1).build();
            AutoreDto dto = AutoreDto.builder().id(1).build();
            when(autoreRepository.findByNome(nome)).thenReturn(a);
            when(autoreMapper.autoreToAutoreDto(a)).thenReturn(dto);

            AutoreDto result = autoreService.getAutoreByName(nome);

            verify(autoreRepository).findByNome(nome);
            verify(autoreMapper).autoreToAutoreDto(a);

            assertEquals(result, dto);
        }

        @Test
        public void getAutoreByEmailTest() {
            String email = "";
            Autore a = Autore.builder().id(1).build();
            AutoreDto dto = AutoreDto.builder().id(1).build();
            when(autoreRepository.findByEmail(email)).thenReturn(a);
            when(autoreMapper.autoreToAutoreDto(a)).thenReturn(dto);

            AutoreDto result = autoreService.getAutoreByEmail(email);

            verify(autoreRepository).findByEmail(email);
            verify(autoreMapper).autoreToAutoreDto(a);

            assertEquals(result, dto);
        }



        @Test
        public void getAllAutoriTest() {
            Autore autore1 = Autore.builder().id(1).build();
            Autore autore2 = Autore.builder().id(2).build();
            List<Autore> autori = Arrays.asList(autore1, autore2);

            AutoreDto autoreDto1 = AutoreDto.builder().id(1).build();
            AutoreDto autoreDto2 = AutoreDto.builder().id(2).build();
            List<AutoreDto> autoriDto = Arrays.asList(autoreDto1, autoreDto2);

            when(autoreRepository.findAll()).thenReturn(autori);
            when(autoreMapper.autoriToAutoriDto(autori)).thenReturn(autoriDto);

            List<AutoreDto> result = autoreService.getAllAutori();

            verify(autoreRepository).findAll();
            verify(autoreMapper).autoriToAutoriDto(autori);

            assertEquals(2, result.size());
            assertEquals(autoreDto1, result.get(0));
            assertEquals(autoreDto2, result.get(1));
        }

        @Test
        public void addAutoreTest() {
            AutoreDto dto = AutoreDto.builder().password("password").build();
            Autore autore = Autore.builder().build();
            User user = User.builder().password("password").build();
            Role role = Role.builder().value("ROLE_CLIENT").build();

            when(autoreMapper.autoreDtoToAutore(dto)).thenReturn(autore);
            when(autoreMapper.autoreDtoToUser(dto)).thenReturn(user);
            when(roleService.findByValue("ROLE_CLIENT")).thenReturn(role);
            when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

            autoreService.addAutore(dto);

            verify(autoreMapper).autoreDtoToAutore(dto);
            verify(autoreMapper).autoreDtoToUser(dto);
            verify(roleService).findByValue("ROLE_CLIENT");
            verify(passwordEncoder).encode("password");
            verify(autoreRepository).save(autore);
            verify(userRepository).save(user);

            assertEquals("encodedPassword", user.getPassword());
            assertEquals(role, user.getRole());
        }

        @Test
        public void editAutoreTest() {
            AutoreDto dto = AutoreDto.builder().id(1).build();
            Autore autore = Autore.builder().id(1).build();

            when(autoreMapper.autoreDtoToAutore(dto)).thenReturn(autore);
            when(autoreRepository.existsById(1)).thenReturn(true);
            when(autoreRepository.save(autore)).thenReturn(autore);
            boolean result = autoreService.editAutore(dto);

            assertTrue(result);
            verify(autoreMapper).autoreDtoToAutore(dto);
        }

        @Test
        public void removeAutoreTest() {
            Autore autore = Autore.builder().id(1).build();
            when(autoreRepository.existsById(1)).thenReturn(true);
            doNothing().when(autoreRepository).deleteById(autore.getId());
            boolean result = autoreService.removeAutore(autore.getId());

            assertTrue(result);
        }
    }
}
