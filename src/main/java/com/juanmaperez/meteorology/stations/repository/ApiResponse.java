package com.juanmaperez.meteorology.stations.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ApiResponse {

    @JsonProperty("descripcion")
    private String descripcion;

    @JsonProperty("estado")
    private int estado;

    @JsonProperty("datos")
    private String datos;

    @JsonProperty("metadatos")
    private String metadatos;
}
