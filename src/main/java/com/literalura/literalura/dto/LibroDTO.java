package com.literalura.literalura.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.literalura.literalura.model.Autor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LibroDTO {
    @JsonProperty("title")String titulo;
    @JsonProperty("authors")
    List<Autor> autores;
    @JsonProperty("languages") List<String>  lenguajes;
    @JsonProperty("download_count") int numeroDeDescargas;

    public LibroDTO(String titulo, List<Autor> autores, List<String> lenguajes, int numeroDeDescargas) {
        this.titulo = titulo;
        this.autores = autores;
        this.lenguajes = lenguajes;
        this.numeroDeDescargas = numeroDeDescargas;
    }
    public LibroDTO(){}

    public void setNumeroDeDescargas(int numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public List<String> getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(List<String>  lenguajes) {
        this.lenguajes = lenguajes;
    }

    public int getNumeroDeDescargas() {
        return numeroDeDescargas;
    }


    @Override
    public String toString() {
        return "LibroDTO{" +
                "titulo='" + titulo + '\'' +
                ", autor=" + autores +
                ", lenguajes=" + lenguajes +
                ", numeroDeDescargas=" + numeroDeDescargas +
                '}';
    }



}
