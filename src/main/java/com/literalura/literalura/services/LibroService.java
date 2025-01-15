package com.literalura.literalura.services;

import com.literalura.literalura.dto.LibroDTO;
import com.literalura.literalura.model.Autor;
import com.literalura.literalura.model.Libro;
import com.literalura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LibroService {

    @Autowired
   private LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }


public List<LibroDTO> findAllLibros() {
    List<Object[]> infoLibros = libroRepository.findAllLibros();
    List<LibroDTO> libros = new ArrayList<>();
    for (Object[] libro : infoLibros) {
        String titulo = (String) libro[0];
        String autorNombre = (String) libro[1];
        String lenguaje = (String) libro[2];
        int numeroDeDescargas = ((Number) libro[3]).intValue();


        Autor autor = new Autor();
        autor.setNombre(autorNombre);


        List<Autor> autores = Collections.singletonList(autor);


        LibroDTO libroDTO;
        libroDTO = new LibroDTO(titulo, autores, Collections.singletonList(lenguaje), numeroDeDescargas);
        libros.add(libroDTO);
    }
    return libros;
}

    public List<Libro> listarLibrosPorIdioma(String lenguajes){
        return libroRepository.findLibroPorIdioma(lenguajes);
    }



    @Override
    public String toString() {
        return "LibroService{" +
                "libroRepository=" + libroRepository +
                '}';
    }
}
