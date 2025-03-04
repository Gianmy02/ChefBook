package it.sysman.chefbook.integration;

import it.sysman.chefbook.dto.AutoreDto;
import it.sysman.chefbook.repository.UserRepository;
import it.sysman.chefbook.service.AutoreService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AutoreServiceIntegrationTest {
//
//    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0")
//            .withDatabaseName("cb")
//            .withUsername("chefbook")
//            .withPassword("chefbook");
//
//    @BeforeAll
//    static void startContainer() {
//        mySQLContainer.start();
//    }
//
//    @AfterAll
//    static void stopContainer() {
//        mySQLContainer.stop();
//    }
//
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", mySQLContainer::getUsername);
//        registry.add("spring.datasource.password", mySQLContainer::getPassword);
//    }

    @Autowired
    private UserRepository userRepository;

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