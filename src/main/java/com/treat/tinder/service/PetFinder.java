package com.treat.tinder.service;

import com.treat.tinder.Constants;
import com.treat.tinder.entity.PetFinderResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class PetFinder {
    private final Logger logger = LoggerFactory.getLogger(PetFinder.class);
    private static final String GET_DOGS = Constants.PET_FINDER_BASE_URL + "v2/animals?type=dog&page=3";

    private String getAuthToken() {
        StringBuilder response = new StringBuilder();
        try {
            // Encode client id and client secret
            String encodedClientId = URLEncoder.encode(Constants.CLIENT_ID, StandardCharsets.UTF_8);
            String encodedClientSecret = URLEncoder.encode(Constants.CLIENT_SECRET, StandardCharsets.UTF_8);

            // Construct request body
            String requestBody = "grant_type=" + URLEncoder.encode(Constants.GRANT_TYPE, StandardCharsets.UTF_8) +
                    "&client_id=" + encodedClientId +
                    "&client_secret=" + encodedClientSecret;

            // Create URL object
            HttpURLConnection con = getHttpURLConnection(requestBody);

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            // Read response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Parse JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response.toString());
            // Extract access token
            return jsonNode.get("access_token").asText();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static HttpURLConnection getHttpURLConnection(String requestBody) throws IOException {
        URL obj = new URL(Constants.GET_AUTH_TOKEN_URL);

        // Create HttpURLConnection
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Set the request method
        con.setRequestMethod("POST");

        // Set request headers
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Enable input/output
        con.setDoInput(true);
        con.setDoOutput(true);

        // Send POST request
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(requestBody);
            wr.flush();
        }
        return con;
    }


    public PetFinderResponse getDogsPetFinder() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getAuthToken());

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<PetFinderResponse> responseEntity = restTemplate.exchange(GET_DOGS, HttpMethod.GET, entity, PetFinderResponse.class);

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            PetFinderResponse petFinderResponse = responseEntity.getBody();
            if (petFinderResponse != null && !petFinderResponse.getAnimals().isEmpty()) {
                logger.info("We were able to fetch the data from Pet finder");
                return petFinderResponse;
            }
        }
        return null;
    }
}
