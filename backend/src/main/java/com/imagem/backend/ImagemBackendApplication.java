package com.imagem.backend;

import com.imagem.backend.infra.ext.IntegrationAI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class ImagemBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(ImagemBackendApplication.class, args);
		log.info("Para acessar a documentação acessar: https://app.swaggerhub.com/apis/FLUFFYFATEC/FluffyImagem/1.0.0");
	}

}
