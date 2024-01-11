package com.juanmaperez.meteorology.stations.repository;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.juanmaperez.meteorology.stations.model.Station;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AemetRepositoryData implements RepositoryData{

    private final String apiURL = "https://opendata.aemet.es/opendata";
    private final String API_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdWFubWE5N0BvdXRsb29rLmNvbSIsImp0aSI6ImRkYjNjNTg4LTExMjEtNDc0My05OGE0LTgxNzZmNGI1YWI5YyIsImlzcyI6IkFFTUVUIiwiaWF0IjoxNzAxMjcyNjcyLCJ1c2VySWQiOiJkZGIzYzU4OC0xMTIxLTQ3NDMtOThhNC04MTc2ZjRiNWFiOWMiLCJyb2xlIjoiIn0.Fjxwa6DGHvMFo126Y8UjWA3deOZPmZ2G6q4_mqii95g";
    private final String stationsURL = "/api/valores/climatologicos/diarios/datos/fechaini/{fechaIniStr}/fechafin/{fechaFinStr}/estacion/{idema}";
    @Override
    public List<Station> getStations(final List<String> stationsIds, String startDate, String endDate) {
        WebClient client = WebClient.create(apiURL);

        ApiResponse response = client.get()
                .uri(uriBuilder ->
                        uriBuilder.path(stationsURL).queryParam("api_key", API_KEY).build(startDate, endDate, String.join(",", stationsIds)))
                .retrieve()
                .bodyToMono(ApiResponse.class).block();

        List<Station> stations = new ArrayList<>();

        try
        {
            //creamos una URL donde esta nuestro webservice
            URL url = new URL(response.getDatos());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //indicamos por que verbo HTML ejecutaremos la solicitud
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            if (conn.getResponseCode() != 200)
            {
                //si la respuesta del servidor es distinta al codigo 200 lanzaremos una Exception
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            //creamos un StringBuilder para almacenar la respuesta del web service
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = br.read()) != -1)
            {
                sb.append((char) cp);
            }
            //en la cadena output almacenamos toda la respuesta del servidor
            String output = sb.toString();
            //convertimos la cadena a JSON a traves de la libreria GSON
            JsonArray json = new Gson().fromJson(output,JsonArray.class);

            for (JsonElement jsonElement : json) {
                String rain = getJsonValueAsString(jsonElement, "prec");
                String name = getJsonValueAsString(jsonElement, "nombre");
                stations.add(Station.builder().name(name).rain(rain).build());
            }

            conn.disconnect();
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }

        return stations;
    }

    private String getJsonValueAsString(JsonElement jsonElement, String key) {
        return ((JsonObject) jsonElement).has(key) ? ((JsonObject) jsonElement).get(key).getAsString() : "";
    }
}
