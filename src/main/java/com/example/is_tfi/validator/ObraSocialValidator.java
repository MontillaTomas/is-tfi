package com.example.is_tfi.validator;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ObraSocialValidator {

    private static final String API_URL = "https://istp1service.azurewebsites.net/api/servicio-salud/obras-sociales/";

    private final RestTemplate restTemplate;

    public ObraSocialValidator() {
        this.restTemplate = new RestTemplate();
    }

    public boolean obraSocialEsValida(int codigoObraSocial) {
        String url = API_URL + codigoObraSocial;
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }
}

