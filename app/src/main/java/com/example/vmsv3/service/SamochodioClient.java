package com.example.vmsv3.service;

import android.util.Log;

import com.example.vmsv3.transport.MandatDto;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SamochodioClient {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String apiUrl = "http://localhost:8080/api";
    public static SamochodioClient getInstance(){
        return new SamochodioClient();
    }

    public boolean createTicket(MandatDto mandatDto, String authToken) {
        String createTicketUrl = apiUrl + "/tickets";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authToken);
        HttpEntity<MandatDto> httpEntity = new HttpEntity<>(mandatDto, headers);

        ResponseEntity<Object> response = restTemplate.exchange(createTicketUrl, HttpMethod.POST, httpEntity, Object.class);
        Log.i("SC response", response.toString());
        return response.getStatusCode().value() == 201;
    }
}
