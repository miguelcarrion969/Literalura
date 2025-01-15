package com.literalura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;


import java.util.List;

public class Resultados {

    private List<Libro> listaDeResultados;

    public Resultados(){}

    public Resultados(List<Libro> listaDeResultados) {
        this.listaDeResultados = listaDeResultados;
    }

    public List<Libro> getListaDeResultados() {
        return listaDeResultados;
    }

    public void setListaDeResultados(List<Libro> listaDeResultados) {
        this.listaDeResultados = listaDeResultados;
    }
}
