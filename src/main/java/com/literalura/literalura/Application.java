package com.literalura.literalura;


import com.literalura.literalura.principal.Principal;
import com.literalura.literalura.repository.AutorRepository;
import com.literalura.literalura.repository.LibroRepository;

import com.literalura.literalura.services.AutorService;
import com.literalura.literalura.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Application implements CommandLineRunner {
@Autowired
private LibroRepository libroRepository;
@Autowired
private AutorRepository autorRepository;
	@Autowired
	private LibroService libroService;
	@Autowired
	private AutorService autorService;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepository, libroRepository,libroService,autorService);
		principal.muestraElMenu();
	}
}
