package com.juanmaperez.meteorology.stations.converter;

import com.juanmaperez.meteorology.stations.model.Station;
import com.juanmaperez.meteorology.stations.model.StationResponse;

public class StationConverter {

    public static StationResponse convert(final Station station){
        return StationResponse.builder().name(station.getName()).rain(station.getRain()).build();
    }
}
