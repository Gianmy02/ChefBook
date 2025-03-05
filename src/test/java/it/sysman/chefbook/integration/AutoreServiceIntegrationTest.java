package it.sysman.chefbook.integration;

import it.sysman.chefbook.dto.AutoreDto;
import it.sysman.chefbook.service.AutoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AutoreServiceIntegrationTest {

    @Autowired
    private AutoreService autoreService;

    @Test
    void testSaveAndRetrieveUser() {
        AutoreDto dto = new AutoreDto();
        dto.setEmail("test@example.com");
        dto.setPassword("password");
        autoreService.addAutore(dto);

        AutoreDto a = autoreService.getAutoreByEmail("test@example.com");
        assertThat(a.getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void testEditAndRetrieveUser() {
        AutoreDto dto = new AutoreDto();
        dto.setEmail("test2@example.com");
        dto.setPassword("password");
        autoreService.addAutore(dto);
        AutoreDto dto2 = autoreService.getAutoreByEmail("test2@example.com");
        dto2.setEmail("test3@example.com");
        boolean result = autoreService.editAutore(dto2);
        AutoreDto a2 = autoreService.getAutoreByEmail("test3@example.com");
        assertThat(result).isTrue();
        assertThat(a2.getEmail()).isEqualTo("test3@example.com");
        assertThat(a2.getId()).isEqualTo(dto2.getId());
    }



    @Test
    void testRemove() {
        AutoreDto dto = new AutoreDto();
        dto.setEmail("test4@example.com");
        dto.setPassword("password");
        autoreService.addAutore(dto);
        AutoreDto dto2 = autoreService.getAutoreByEmail("test4@example.com");
        boolean result = autoreService.removeAutore(dto2.getId());
        assertThat(result).isTrue();
    }
}