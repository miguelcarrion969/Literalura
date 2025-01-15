package com.literalura.literalura.principal;

import com.literalura.literalura.dto.AutorDTO;
import com.literalura.literalura.dto.LibroDTO;
import com.literalura.literalura.dto.ResultadosDTO;
import com.literalura.literalura.model.Autor;
import com.literalura.literalura.model.Libro;
import com.literalura.literalura.repository.AutorRepository;
import com.literalura.literalura.repository.LibroRepository;
import com.literalura.literalura.services.AutorService;
import com.literalura.literalura.services.ConsumoAPI;
import com.literalura.literalura.services.ConvierteDatos;
import com.literalura.literalura.services.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos conversor = new ConvierteDatos();

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;
    @Autowired
    private LibroService libroService;
    @Autowired
    private AutorService autorService;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository, LibroService libroService, AutorService autorService) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
        this.libroService = libroService;
        this.autorService = autorService;
        this.consumoApi = consumoApi;
        this.conversor = conversor;
    }


    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libro por titulo
                    2 - mostrar libros 
                    3 - mostrar autores registrados
                    4- Ver autores vivos en un determinado año
                    5 - Mostrar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    autoresVivosEnAnio();
                    break;
                case 5:
                    mostrarLibroPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }


    }



    private List<LibroDTO> buscarLibroPorTitulo() {
        System.out.println("Escribe el nombre del libro que quieres buscar");
        String nombreLibro = teclado.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + URLEncoder.encode(nombreLibro, StandardCharsets.UTF_8));

        if (json == null || json.isEmpty()) {
            System.out.println("No se encontró la información del libro");
            return null;
        }

        try {
            ResultadosDTO resultados = conversor.obtenerDatos(json, ResultadosDTO.class);
            System.out.println(resultados);
            List<LibroDTO> librosDTO = resultados.getResultados();

            if (librosDTO == null || librosDTO.isEmpty()) {
                System.out.println("No se encontraron libros.");
                return null;
            }

            LibroDTO libroDTO = librosDTO.get(0);

            Libro libro = new Libro();
            libro.setTitulo(libroDTO.getTitulo());


            if (libroDTO.getLenguajes() != null && !libroDTO.getLenguajes().isEmpty()) {

                libro.setLenguajes(libroDTO.getLenguajes().get(0));
            } else {

                libro.setLenguajes("Desconocido");
            }

            libro.setNumeroDeDescargas(libroDTO.getNumeroDeDescargas());
            System.out.println(libro);

            if (libroDTO.getAutores() != null && !libroDTO.getAutores().isEmpty()) {
                Autor primerAutorDTO = libroDTO.getAutores().get(0);

                Autor autor = autorRepository.findByNombre(primerAutorDTO.getNombre());
                if (autor == null) {
                    autor = new Autor();
                    autor.setNombre(primerAutorDTO.getNombre());
                    autor.setFechaDeNacimiento(primerAutorDTO.getFechaDeNacimiento());
                    autor.setFechaDeFallecimiento(primerAutorDTO.getFechaDeFallecimiento());

                    autorRepository.save(autor);
                }

                libro.setAutor(autor);
            } else {
                System.out.println("Autor: Desconocido");
            }

            libroRepository.save(libro);
            System.out.println("------------LIBRO-----------");
            System.out.println("Libro guardado: " + libro.getTitulo());
            System.out.println("Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
            System.out.println("Idioma: " + libro.getLenguajes());
            System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
            System.out.println("---------------------");

            return List.of(libroDTO);

        } catch (Exception e) {
            throw new RuntimeException("Error al procesar la información del libro: " + e.getMessage(), e);
        }
    }


    public List<LibroDTO> listarLibrosRegistrados() {
        try {
            List<LibroDTO> libros = libroService.findAllLibros();
            if (libros == null || libros.isEmpty()) {
                System.out.println("No hay libros registrados.");
            } else {
                libros.forEach(libro -> {
                    System.out.println("---------LIBRO--------");
                    System.out.println("Titulo: " + libro.getTitulo());


                    if (libro.getAutores() != null ) {
                        Autor autorNombre = libro.getAutores().get(0);
                        System.out.println("Autor: " + autorNombre.getNombre());
                    } else {
                        System.out.println("Autor: Desconocido");
                    }
                    System.out.println("Idioma: " + libro.getLenguajes().get(0));
                    System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
                });
            }
            return libros;
        } catch (Exception e) {
            System.err.println("Error al listar libros: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    private List<AutorDTO> listarAutoresRegistrados() {
        List<AutorDTO> autores = autorService.listarAutores();
        if (autores == null || autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
            return Collections.emptyList();
        }

        Map<String, AutorDTO> autorMap = autores.stream()
                .collect(Collectors.toMap(
                        AutorDTO::getNombre,
                        autor -> autor,
                        (autorExistente, nuevoAutor) -> {
                            if (autorExistente.getLibros() == null) {
                                autorExistente.setLibros(new ArrayList<>());
                            }
                            autorExistente.getLibros().addAll(nuevoAutor.getLibros());
                            return autorExistente;
                        }
                ));

        autorMap.values().forEach(autor -> {
            System.out.println("---------LIBRO---------");
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getFechaDeNacimiento());
            System.out.println("Fecha de fallecimiento: " + autor.getFechaDeFallecimiento());
            System.out.println("---------------------------");
            String libros = (autor.getLibros() != null && !autor.getLibros().isEmpty())
                    ? String.join(", ", autor.getLibros())
                    : "No hay libros disponibles";

            System.out.println("Libros: [" + libros + "]");

        });

        return new ArrayList<>(autorMap.values());
    }


    public List<AutorDTO> autoresVivosEnAnio() {
        System.out.println("Ingrese el año en el que desea buscar autores vivos:");

        int anio = teclado.nextInt();

        List<AutorDTO> autorVivo = autorService.listarAutoresVivosEnAnio(anio);


        if (autorVivo.isEmpty()) {
            System.out.println("No se encontraron autores vivos en el año: " + anio);
        } else {
            System.out.println("Autores vivos en el año " + anio + ":");
            for (AutorDTO autor : autorVivo) {
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getFechaDeNacimiento());
                System.out.println("Fecha de fallecimiento: " + autor.getFechaDeFallecimiento());

                String libros = (autor.getLibros() != null && !autor.getLibros().isEmpty())
                        ? String.join(", ", autor.getLibros())
                        : "No hay libros disponibles";

                System.out.println("Libros: [" + libros + "]");
                System.out.println("-----------");
            }
        }

        return autorVivo;
    }


    private void mostrarLibroPorIdioma(){
        System.out.println("Elija un idioma");

        System.out.println("es - Español");
        System.out.println("en - Inglés");
        System.out.println("fr - Francés");
        System.out.println("pt - Portugues");
        String lenguaje = teclado.nextLine();
        if("es".equalsIgnoreCase(lenguaje) || "en".equalsIgnoreCase(lenguaje) || "fr".equalsIgnoreCase(lenguaje) || "pt".equalsIgnoreCase(lenguaje)){
            libroService.listarLibrosPorIdioma(lenguaje).forEach(libro -> {
                System.out.println("------------LIBRO-----------");
                System.out.println("Libro guardado: " + libro.getTitulo());
                System.out.println("Autor: " + (libro.getAutor() != null ? libro.getAutor().getNombre() : "Desconocido"));
                System.out.println("Idioma: " + libro.getLenguajes());
                System.out.println("Número de descargas: " + libro.getNumeroDeDescargas());
                System.out.println("---------------------");
            });
        }else {
            System.out.println("El lenguaje no fue valido");
        }
    }

}


