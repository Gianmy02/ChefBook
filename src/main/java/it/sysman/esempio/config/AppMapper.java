package it.sysman.esempio.config;

import it.sysman.esempio.utils.RicettaMapper;
import it.sysman.esempio.utils.RicettaMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppMapper {
    @Bean
    public RicettaMapper mapperCreate() {
        return new RicettaMapperImpl();
    }
}
