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
    private static final String GET_DOGS = Constants.PET_FINDER_BASE_URL + "v2/animals?type=dog&page=";

    /**
     * Get the auth token from AUTH api and fetch the access token .
     * @return Access token.
     */
    private String getAuthToken() {
        logger.info("Fetch the auth token");
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
            logger.info("Response Code from the AUTH API is " + con.getResponseCode());

            // Read response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

        } catch (IOException e) {
            logger.error("Failed to read data from Pet Finder Auth API");
            throw new RuntimeException(e);
        }

        // Parse JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(response.toString());
            // Extract access token and return
            logger.info("Successfully got the access token ");
            return jsonNode.get("access_token").asText();
        } catch (IOException e) {
            logger.error("Failed in parsing response from PetFinder Auth API");
            throw new RuntimeException(e);
        }
    }

    /**
     * Helper Method to establish the connection.
     * @param requestBody Request to be sent to the API
     * @return URL connection
     * @throws IOException
     */

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

    /**
     * Get the list of dogs from Petfinder
     * @param pageNumber page to be fetched.
     * @return Return the response from petfinder
     */
    public PetFinderResponse getDogsPetFinder(int pageNumber) {
        RestTemplate restTemplate = new RestTemplate();
        //1. Get the auth the token.
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + getAuthToken());
        logger.info("Got the auth token and set it as header.");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        //2. Fetch the data
        ResponseEntity<PetFinderResponse> responseEntity = restTemplate.exchange(GET_DOGS + pageNumber, HttpMethod.GET, entity, PetFinderResponse.class);

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
