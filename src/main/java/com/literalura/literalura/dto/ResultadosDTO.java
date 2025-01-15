package com.literalura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.literalura.literalura.model.Libro;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultadosDTO {
    @JsonProperty("results")
    List<LibroDTO> resultados;

    public ResultadosDTO(List<LibroDTO> resultados) {
        this.resultados = resultados;
    }

    public ResultadosDTO(){}


    public List<LibroDTO> getResultados() {
        return resultados;
    }

    public void setResultados(List<LibroDTO> resultados) {
        this.resultados = resultados;
    }

    @Override
    public String toString() {
        return "ResultadosDTO{" +
                "resultados=" + resultados +
                '}';
    }
}
