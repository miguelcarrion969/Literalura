package com.literalura.literalura.services;

import com.literalura.literalura.dto.AutorDTO;
import com.literalura.literalura.model.Autor;
import com.literalura.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }


    public List<AutorDTO> listarAutores() {
        List<Object[]> result = autorRepository.findAllAutoresConLibro();
        List<AutorDTO> autores = new ArrayList<>();
        System.out.println(result);
        for (Object[] row : result) {

            String nombre = (String) row[0];
            int fechaDeNacimiento = (int) row[1];
            int fechaDeFallecimiento = (int) row[2];
            List<String> libros = Arrays.asList(((String) row[3]).split(", "));


            AutorDTO autorDTO = new AutorDTO(nombre, fechaDeNacimiento, fechaDeFallecimiento, libros);
            autores.add(autorDTO);
        }

        return autores;
    }



    public List<AutorDTO> listarAutoresVivosEnAnio(int anio) {
        List<Object[]> autoresVivos = autorRepository.findAutoresVivosEnAnioConLibro(anio);
        Map<String, AutorDTO> mapaAutores = new HashMap<>();

        for (Object[] row : autoresVivos) {
            String nombre = (String) row[0];
            int fechaDeNacimiento = (Integer) row[1];
            int fechaDeFallecimiento = (Integer) row[2];
            List<String> libros = Arrays.asList(((String) row[3]).split(", "));


            if (mapaAutores.containsKey(nombre)) {
                AutorDTO autorExistente = mapaAutores.get(nombre);
                autorExistente.getLibros().addAll(libros);
            } else {

                mapaAutores.put(nombre, new AutorDTO(nombre, fechaDeNacimiento, fechaDeFallecimiento, libros));
            }
        }

        return new ArrayList<>(mapaAutores.values());
    }


}
