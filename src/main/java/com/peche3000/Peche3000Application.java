package com.peche3000;

import com.peche3000.entity.Produit;
import com.peche3000.repository.ProduitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories("com.peche3000.repository")
@EntityScan("com.peche3000.entity")
public class Peche3000Application {

	public static void main(String[] args) {
		SpringApplication.run(Peche3000Application.class, args);
	}

}
