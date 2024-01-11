package com.juanmaperez.meteorology.stations.service;

import com.juanmaperez.meteorology.stations.model.Station;
import com.juanmaperez.meteorology.stations.repository.RepositoryData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationService {

    private final RepositoryData repositoryData;

    public StationService(final RepositoryData repositoryData) {
        this.repositoryData = repositoryData;
    }

    public List<Station> getStations(List<String> stationsIds, String startDate, String endDate) {
        return repositoryData.getStations(stationsIds, startDate, endDate);
    }
}
