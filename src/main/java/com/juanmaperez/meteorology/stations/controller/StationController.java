package com.juanmaperez.meteorology.stations.controller;

import com.juanmaperez.meteorology.stations.converter.StationConverter;
import com.juanmaperez.meteorology.stations.model.Station;
import com.juanmaperez.meteorology.stations.model.StationResponse;
import com.juanmaperez.meteorology.stations.service.StationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/stations")
public class StationController {

    private final StationService stationService;

    public StationController(final StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping()
    public ResponseEntity<List<StationResponse>> getStations(@RequestParam final List<String> stationsIds, @RequestParam final String startDate, @RequestParam final String endDate){
        final List<Station> stations = stationService.getStations(stationsIds, startDate, endDate);
        final List<StationResponse> stationResponse = stations.stream().map(StationConverter::convert).collect(Collectors.toList());

        return ResponseEntity.of(Optional.of(stationResponse));
    }
}
