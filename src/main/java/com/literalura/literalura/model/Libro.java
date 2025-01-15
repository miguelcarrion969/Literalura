package com.literalura.literalura.model;



import jakarta.persistence.*;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    private String  lenguajes;

    private int numeroDeDescargas;

    public Libro(){}

    public Libro(Long id, String titulo, Autor autor, String  lenguajes, int numeroDeDescargas) {
        Id = id;
        this.titulo = titulo;
        this.autor =  autor;
        this.lenguajes = lenguajes;
        this.numeroDeDescargas = numeroDeDescargas;
    }



    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }



    public String  getLenguajes() {
        return lenguajes;
    }

    public void setLenguajes(String  lenguajes) {
        this.lenguajes = lenguajes;
    }

    public int getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(int numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", lenguajes=" + lenguajes +
                ", numeroDeDescargas=" + numeroDeDescargas +
                '}';
    }
}
