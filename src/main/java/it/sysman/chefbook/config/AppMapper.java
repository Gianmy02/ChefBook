package it.sysman.chefbook.config;

import it.sysman.chefbook.utils.AutoreMapper;
import it.sysman.chefbook.utils.AutoreMapperImpl;
import it.sysman.chefbook.utils.RicettaMapper;
import it.sysman.chefbook.utils.RicettaMapperImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppMapper {
    @Bean
    public RicettaMapper mapperCreate() {
        return new RicettaMapperImpl();
    }

    @Bean
    public AutoreMapper autoreMapper(){
        return new AutoreMapperImpl();
    }
}
