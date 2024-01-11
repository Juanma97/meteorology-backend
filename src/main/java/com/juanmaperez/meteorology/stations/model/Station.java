package com.juanmaperez.meteorology.stations.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Station {

    private String name;
    private String rain;
}
