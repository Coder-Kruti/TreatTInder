package com.example.treat.tinder.service;

import com.example.treat.tinder.Constants;
import com.example.treat.tinder.entity.Address;
import com.example.treat.tinder.entity.Animal;
import com.example.treat.tinder.entity.Breed;
import com.example.treat.tinder.entity.Contact;
import com.example.treat.tinder.entity.PetFinderResponse;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PetFinder {
    private final Logger logger = LoggerFactory.getLogger(PetFinder.class);
    private static final String GET_DOGS = Constants.PET_FINDER_BASE_URL + "v2/animals?type=dog&page=2";

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

//    public void getDogsPetFinder() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + getAuthToken());
//
//        HttpEntity<String> entity = new HttpEntity<>(headers);
//        PetFinderResponse petFinderResponse = new PetFinderResponse();
//
//        ResponseEntity<String> response = restTemplate.exchange(GET_DOGS, HttpMethod.GET, entity, String.class);
//        if (response.getStatusCode().equals(HttpStatus.OK) && !Objects.requireNonNull(response.getBody()).isEmpty()) {
////        if(true){
//            logger.info("We were able to fetch the data from Petfinder");
//            String responseBody = response.getBody();
////            String responseBody = "{\n" +
////                    "   \"animals\":[\n" +
////                    "      {\n" +
////                    "         \"id\":70713430,\n" +
////                    "         \"organization_id\":\"MD35\",\n" +
////                    "         \"url\":\"https://www.petfinder.com/dog/kiera-70713430/md/waldorf/humane-society-of-charles-county-md-md35/?referrer_id=865644a2-78d8-462b-b3e6-dc1ec6e37f24&utm_source=api&utm_medium=partnership&utm_content=865644a2-78d8-462b-b3e6-dc1ec6e37f24\",\n" +
////                    "         \"type\":\"Dog\",\n" +
////                    "         \"species\":\"Dog\",\n" +
////                    "         \"breeds\":{\n" +
////                    "            \"primary\":\"Chihuahua\",\n" +
////                    "            \"secondary\":\"Yorkshire Terrier\",\n" +
////                    "            \"mixed\":true,\n" +
////                    "            \"unknown\":false\n" +
////                    "         },\n" +
////                    "         \"colors\":{\n" +
////                    "            \"primary\":\"Black\",\n" +
////                    "            \"secondary\":\"Brown / Chocolate\",\n" +
////                    "            \"tertiary\":null\n" +
////                    "         },\n" +
////                    "         \"age\":\"Baby\",\n" +
////                    "         \"gender\":\"Female\",\n" +
////                    "         \"size\":\"Small\",\n" +
////                    "         \"coat\":null,\n" +
////                    "         \"attributes\":{\n" +
////                    "            \"spayed_neutered\":false,\n" +
////                    "            \"house_trained\":false,\n" +
////                    "            \"declawed\":null,\n" +
////                    "            \"special_needs\":false,\n" +
////                    "            \"shots_current\":true\n" +
////                    "         },\n" +
////                    "         \"environment\":{\n" +
////                    "            \"children\":null,\n" +
////                    "            \"dogs\":null,\n" +
////                    "            \"cats\":null\n" +
////                    "         },\n" +
////                    "         \"tags\":[\n" +
////                    "            \n" +
////                    "         ],\n" +
////                    "         \"name\":\"Kiera\",\n" +
////                    "         \"description\":null,\n" +
////                    "         \"organization_animal_id\":\"HSCC-A-22707\",\n" +
////                    "         \"photos\":[\n" +
////                    "            \n" +
////                    "         ],\n" +
////                    "         \"primary_photo_cropped\":null,\n" +
////                    "         \"videos\":[\n" +
////                    "            \n" +
////                    "         ],\n" +
////                    "         \"status\":\"adoptable\",\n" +
////                    "         \"status_changed_at\":\"2024-02-13T18:49:41+0000\",\n" +
////                    "         \"published_at\":\"2024-02-13T18:49:40+0000\",\n" +
////                    "         \"distance\":null,\n" +
////                    "         \"contact\":{\n" +
////                    "            \"email\":\"hsccadoptioninquiries@gmail.com\",\n" +
////                    "            \"phone\":\"(301) 645-8181\",\n" +
////                    "            \"address\":{\n" +
////                    "               \"address1\":\"71 Industrial Park Dr\",\n" +
////                    "               \"address2\":null,\n" +
////                    "               \"city\":\"Waldorf\",\n" +
////                    "               \"state\":\"MD\",\n" +
////                    "               \"postcode\":\"20604\",\n" +
////                    "               \"country\":\"US\"\n" +
////                    "            }\n" +
////                    "         },\n" +
////                    "         \"_links\":{\n" +
////                    "            \"self\":{\n" +
////                    "               \"href\":\"/v2/animals/70713430\"\n" +
////                    "            },\n" +
////                    "            \"type\":{\n" +
////                    "               \"href\":\"/v2/types/dog\"\n" +
////                    "            },\n" +
////                    "            \"organization\":{\n" +
////                    "               \"href\":\"/v2/organizations/md35\"\n" +
////                    "            }\n" +
////                    "         }\n" +
////                    "      },\n" +
////                    "      {\n" +
////                    "         \"id\":70713431,\n" +
////                    "         \"organization_id\":\"MD35\",\n" +
////                    "         \"url\":\"https://www.petfinder.com/dog/inky-70713431/md/waldorf/humane-society-of-charles-county-md-md35/?referrer_id=865644a2-78d8-462b-b3e6-dc1ec6e37f24&utm_source=api&utm_medium=partnership&utm_content=865644a2-78d8-462b-b3e6-dc1ec6e37f24\",\n" +
////                    "         \"type\":\"Dog\",\n" +
////                    "         \"species\":\"Dog\",\n" +
////                    "         \"breeds\":{\n" +
////                    "            \"primary\":\"Chihuahua\",\n" +
////                    "            \"secondary\":\"Yorkshire Terrier\",\n" +
////                    "            \"mixed\":true,\n" +
////                    "            \"unknown\":false\n" +
////                    "         },\n" +
////                    "         \"colors\":{\n" +
////                    "            \"primary\":\"Black\",\n" +
////                    "            \"secondary\":\"Brown / Chocolate\",\n" +
////                    "            \"tertiary\":null\n" +
////                    "         },\n" +
////                    "         \"age\":\"Baby\",\n" +
////                    "         \"gender\":\"Female\",\n" +
////                    "         \"size\":\"Small\",\n" +
////                    "         \"coat\":null,\n" +
////                    "         \"attributes\":{\n" +
////                    "            \"spayed_neutered\":false,\n" +
////                    "            \"house_trained\":false,\n" +
////                    "            \"declawed\":null,\n" +
////                    "            \"special_needs\":false,\n" +
////                    "            \"shots_current\":true\n" +
////                    "         },\n" +
////                    "         \"environment\":{\n" +
////                    "            \"children\":null,\n" +
////                    "            \"dogs\":null,\n" +
////                    "            \"cats\":null\n" +
////                    "         },\n" +
////                    "         \"tags\":[\n" +
////                    "            \n" +
////                    "         ],\n" +
////                    "         \"name\":\"Inky\",\n" +
////                    "         \"description\":null,\n" +
////                    "         \"organization_animal_id\":\"HSCC-A-22706\",\n" +
////                    "         \"photos\":[\n" +
////                    "            \n" +
////                    "         ],\n" +
////                    "         \"primary_photo_cropped\":null,\n" +
////                    "         \"videos\":[\n" +
////                    "            \n" +
////                    "         ],\n" +
////                    "         \"status\":\"adoptable\",\n" +
////                    "         \"status_changed_at\":\"2024-02-13T18:49:41+0000\",\n" +
////                    "         \"published_at\":\"2024-02-13T18:49:40+0000\",\n" +
////                    "         \"distance\":null,\n" +
////                    "         \"contact\":{\n" +
////                    "            \"email\":\"hsccadoptioninquiries@gmail.com\",\n" +
////                    "            \"phone\":\"(301) 645-8181\",\n" +
////                    "            \"address\":{\n" +
////                    "               \"address1\":\"71 Industrial Park Dr\",\n" +
////                    "               \"address2\":null,\n" +
////                    "               \"city\":\"Waldorf\",\n" +
////                    "               \"state\":\"MD\",\n" +
////                    "               \"postcode\":\"20604\",\n" +
////                    "               \"country\":\"US\"\n" +
////                    "            }\n" +
////                    "         },\n" +
////                    "         \"_links\":{\n" +
////                    "            \"self\":{\n" +
////                    "               \"href\":\"/v2/animals/70713431\"\n" +
////                    "            },\n" +
////                    "            \"type\":{\n" +
////                    "               \"href\":\"/v2/types/dog\"\n" +
////                    "            },\n" +
////                    "            \"organization\":{\n" +
////                    "               \"href\":\"/v2/organizations/md35\"\n" +
////                    "            }\n" +
////                    "         }\n" +
////                    "      }\n" +
////                    "   ],\n" +
////                    "   \"pagination\":{\n" +
////                    "      \"count_per_page\":20,\n" +
////                    "      \"total_count\":164699,\n" +
////                    "      \"current_page\":2,\n" +
////                    "      \"total_pages\":8235,\n" +
////                    "      \"_links\":{\n" +
////                    "         \"previous\":{\n" +
////                    "            \"href\":\"/v2/animals?page=1&type=dog\"\n" +
////                    "         },\n" +
////                    "         \"next\":{\n" +
////                    "            \"href\":\"/v2/animals?page=3&type=dog\"\n" +
////                    "         }\n" +
////                    "      }\n" +
////                    "   }\n" +
////                    "}";
//
//            try {
//                ResponseEntity<PetFinderResponse> responseEntity = restTemplate.exchange(GET_DOGS, HttpMethod.GET, entity, PetFinderResponse.class);
//
//                PetFinderResponse petFinderResponse1 = responseEntity.getBody();
//                // ObjectMapper to parse JSON
//                ObjectMapper objectMapper = new ObjectMapper();
//                JsonNode rootNode = objectMapper.readTree(responseBody);
//
//                // Extract animals array
//                JsonNode animalsNode = rootNode.get("animals");
//                List<Animal> animalList = new ArrayList<>();
//                if (animalsNode != null && animalsNode.isArray()) {
//                    for (JsonNode animalNode : animalsNode) {
//                        String id = animalNode.path("id").asText();
//                        String name = animalNode.path("name").asText();
//                        String url = animalNode.path("url").asText();
//                        String age = animalNode.path("age").asText();
//                        String gender = animalNode.path("gender").asText();
//                        String size = animalNode.path("size").asText();
//                        String organizationId = animalNode.path("organization_id").asText();
//
//                        JsonNode breedsNode = animalNode.path("breeds");
//                        String primaryBreed = breedsNode.path("primary").asText();
//                        String secondaryBreed = breedsNode.path("secondary").asText();
//                        boolean mixed = breedsNode.path("mixed").asBoolean();
//                        boolean unknown = breedsNode.path("unknown").asBoolean();
//
//                        JsonNode contactNode = animalNode.path("contact");
//                        String email = contactNode.path("email").asText();
//                        String phone = contactNode.path("phone").asText();
//
//                        JsonNode addressNode = contactNode.path("address");
//                        String address1 = addressNode.path("address1").asText();
//                        String address2 = addressNode.path("address2").asText();
//                        String city = addressNode.path("city").asText();
//                        String state = addressNode.path("state").asText();
//                        String postcode = addressNode.path("postcode").asText();
//                        String country = addressNode.path("country").asText();
//
//
//                        Animal animal = new Animal();
//                        animal.setId(Long.parseLong(id));
//                        animal.setAge(age);
//                        animal.setName(name);
//                        animal.setGender(gender);
//                        animal.setSize(size);
//                        animal.setUrl(url);
//                        animal.setOrganization_id(organizationId);
//                        Breed breed = new Breed();
//                        breed.setDogId(Integer.parseInt(id));
//                        breed.setMixed(mixed);
//                        breed.setUnknown(unknown);
//                        breed.setPrimary(primaryBreed);
//                        breed.setSecondary(secondaryBreed);
//                        animal.setBreeds(breed);
//                        Address address = new Address();
//                        address.setAddress1(address1);
//                        address.setAddress2(address2);
//                        address.setCity(city);
//                        address.setState(state);
//                        address.setCountry(country);
//                        address.setPostcode(postcode);
//                        Contact contact = new Contact(email, phone, address);
//                        animal.setContact(contact);
//
//                        animalList.add(animal);
//
//                    }
//                    petFinderResponse.setAnimals(animalList);
//                    System.out.println("****************" + petFinderResponse.getAnimals().size());
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//    }


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
