package com.literalura.literalura.repository;

import com.literalura.literalura.dto.AutorDTO;
import com.literalura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {
    @Query(value = "SELECT a.nombre, a.fecha_de_nacimiento, a.fecha_de_fallecimiento, STRING_AGG(l.titulo, ', ') AS libros " +
            "FROM autor a " +
            "LEFT JOIN libro l ON a.id = l.autor_id " +
            "GROUP BY a.id", nativeQuery = true)
    List<Object[]> findAllAutoresConLibro();




    @Query(value = "SELECT a.nombre, a.fecha_de_nacimiento, a.fecha_de_fallecimiento, STRING_AGG(l.titulo, ', ') " +
            "FROM Autor a " +
            "LEFT JOIN Libro l ON a.id = l.autor_id " +
            "WHERE (a.fecha_de_fallecimiento IS NULL OR a.fecha_de_fallecimiento >= :anio) " +
            "AND a.fecha_de_nacimiento <= :anio " +
            "GROUP BY a.id", nativeQuery = true)
    List<Object[]> findAutoresVivosEnAnioConLibro(@Param("anio") int anio);


  Autor findByNombre(String nombre);
}