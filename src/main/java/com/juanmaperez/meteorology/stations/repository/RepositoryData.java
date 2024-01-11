package com.juanmaperez.meteorology.stations.repository;

import com.juanmaperez.meteorology.stations.model.Station;

import java.util.List;

public interface RepositoryData {

    public List<Station> getStations(List<String> stationsIds, String startDate, String endDate);
}
