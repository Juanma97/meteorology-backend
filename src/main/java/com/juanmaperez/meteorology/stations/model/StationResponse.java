package com.juanmaperez.meteorology.stations.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class StationResponse {

    @JsonProperty("name")
    private String name;
    @JsonProperty("rain")
    private String rain;
}
