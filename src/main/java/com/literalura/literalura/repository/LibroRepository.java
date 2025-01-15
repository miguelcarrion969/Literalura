package com.literalura.literalura.repository;

import com.literalura.literalura.dto.LibroDTO;
import com.literalura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {



    @Query(value = "SELECT l.titulo, a.nombre, l.lenguajes, l.numero_de_descargas\n" +
            "FROM Libro l\n" +
            "JOIN Autor a ON l.autor_id = a.id;\n", nativeQuery = true)
    List<Object[]> findAllLibros();

    @Query(value = "SELECT * FROM libro WHERE lenguajes = :lenguajes", nativeQuery = true)
    List<Libro> findLibroPorIdioma(@Param("lenguajes") String lenguajes);


}
