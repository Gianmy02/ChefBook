package it.sysman.chefbook.unit;

import it.sysman.chefbook.entity.Role;
import it.sysman.chefbook.repository.RoleRepository;
import it.sysman.chefbook.service.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {
    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    @Test
    public void findByValueTest() {
        String value = "ROLE_USER";
        Role role = Role.builder().id(1).value("ROLE_USER").build();

        when(roleRepository.findByValue(value)).thenReturn(role);

        Role result = roleService.findByValue(value);

        assertEquals(role, result);
        verify(roleRepository).findByValue(value);
    }

    @Test
    public void findByValueTestFail() {
        String value = "";

        when(roleRepository.findByValue(value)).thenReturn(null);

        Role result = roleService.findByValue(value);

        assertNull(result);
        verify(roleRepository).findByValue(value);
    }
}
