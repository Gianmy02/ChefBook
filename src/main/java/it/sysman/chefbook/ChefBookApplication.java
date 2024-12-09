package it.sysman.chefbook;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "ChefBook API",
				description = "Questa è una descrizione dell'API",
				version = "v0.0.1",
				contact = @Contact(
						name = "Riviello Gianmarco",
						email = "griviello-external@sysmanagement.it"
				)
		)
)
@SpringBootApplication
public class ChefBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChefBookApplication.class, args);
	}

}
