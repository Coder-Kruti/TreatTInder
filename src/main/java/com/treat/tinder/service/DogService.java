package com.treat.tinder.service;

import com.treat.tinder.datasource.BreedRepository;
import com.treat.tinder.datasource.DogRepository;
import com.treat.tinder.datasource.LastProcessedRepository;
import com.treat.tinder.datasource.OrganisationRepository;
import com.treat.tinder.entity.Animal;
import com.treat.tinder.entity.Breed;
import com.treat.tinder.entity.Dog;
import com.treat.tinder.entity.DogFilterOptions;
import com.treat.tinder.entity.Gender;
import com.treat.tinder.entity.LastProcessed;
import com.treat.tinder.entity.Organisation;
import com.treat.tinder.entity.PetFinderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DogService {
    @Autowired
    private final DogRepository dogRepository;
    @Autowired
    private final BreedRepository breedRepository;
    @Autowired
    private final OrganisationRepository organisationRepository;
    @Autowired
    private final LastProcessedRepository lastProcessedRepository;

    private final Logger logger = LoggerFactory.getLogger(DogService.class);

    DogService(DogRepository dogRepository, BreedRepository breedRepository, OrganisationRepository organisationRepository, LastProcessedRepository lastProcessedRepository) {
        this.dogRepository = dogRepository;
        this.breedRepository = breedRepository;
        this.organisationRepository = organisationRepository;
        this.lastProcessedRepository = lastProcessedRepository;
    }

    /***
     * Filter the dog from the database as per given criteria.
     * @param dogFilterOptions Takes the filter options which includes organisation , breed and address.
     * @return List pf dogs which satisfy the given filter
     */

    public List<Dog> filterDogs(DogFilterOptions dogFilterOptions) {
        logger.info("Accepted request to filter dogs");
        //1. Verify if any of the filter parameters are empty , if empty make it as null or take the actual value.
        String organisationName = isEmpty(dogFilterOptions.getOrganizationID()) ? null : dogFilterOptions.getOrganizationID();
        String primaryBreed = isEmpty(dogFilterOptions.getBreed().getPrimary()) ? null : dogFilterOptions.getBreed().getPrimary();
        String secondaryBreed = isEmpty(dogFilterOptions.getBreed().getSecondary()) ? null : dogFilterOptions.getBreed().getSecondary();
        boolean mixed = dogFilterOptions.getBreed().isMixed();
        boolean notKnown = dogFilterOptions.getBreed().isUnknown();
        Gender gender = dogFilterOptions.getGender();
        String city = isEmpty(dogFilterOptions.getAddress().getCity()) ? null : dogFilterOptions.getAddress().getCity();
        String state = isEmpty(dogFilterOptions.getAddress().getState()) ? null : dogFilterOptions.getAddress().getState();
        String postCode = isEmpty(dogFilterOptions.getAddress().getPostcode()) ? null : dogFilterOptions.getAddress().getPostcode();
        String country = isEmpty(dogFilterOptions.getAddress().getCountry()) ? null : dogFilterOptions.getAddress().getCountry();
        logger.info("The filters are validated and will be querying database.");

        //2. Call the JPA to execute the query and list of dogs as per the filter.
        return dogRepository.filterDogs(organisationName, gender.name(), primaryBreed, secondaryBreed,
                mixed, notKnown, city, state, postCode, country);

    }

    /**
     * Helper method to check if a string is empty
     * @param str String to be checked if empty
     * @return true if String empty else false
     */
    private boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Fetch the details from PetFinder API.
     */
    public void getDogsPetFinder() {
        logger.info("Fetching the Dogs from Pet finder");
        //1. Get the today's date .
        PetFinder petFinder = new PetFinder();
        LocalDate today = LocalDate.now();
        //2. Check if already  data is processed for today's date, if processed then increment the page number and update the db so that it fetches new set of records
        Optional<LastProcessed> optionalPage = lastProcessedRepository.findById(1); // The ID of the row will be one as we are updating the same record
        if (optionalPage.isPresent()) {
            LastProcessed existingPage = optionalPage.get();
            if (!existingPage.getLastProcessedDate().equals(today)) {
                existingPage.setLastProcessedDate(today);
                existingPage.setPageNumber(1);
            } else {
                existingPage.setPageNumber(existingPage.getPageNumber() + 1);
            }
            lastProcessedRepository.save(existingPage);
        } else {
            LastProcessed newPage = new LastProcessed();
            newPage.setId(1);
            newPage.setLastProcessedDate(today);
            newPage.setPageNumber(1);
            lastProcessedRepository.save(newPage);
        }
        logger.info("Updated the last processed page ");
        //3. Call the pet finder service
        PetFinderResponse petFinderResponse = petFinder.getDogsPetFinder(lastProcessedRepository.findById(1).get().getPageNumber());
        logger.info("Successfully fetched the response from PetFinder.");
        //4. Process the response and store in the database. Here we will first store the organisation , dog and its breed if they are already not present.
        for (Animal animal : petFinderResponse.getAnimals()) {
            Organisation existingOrg = organisationRepository.findByOrgID(animal.getOrganization_id());

            if (existingOrg == null) {
                saveOrganisation(animal);
            }
            Dog existingDog = dogRepository.findByDogID(animal.getId());

            if (existingDog == null) {
                Dog newDog = saveDog(animal);
                saveDogBreed(animal, newDog);
            }
        }
        logger.info("Successfully stored all the data into database.");
    }

    /**
     * Helper method to store the breed data into data base
     * @param animal incoming response to be stored
     * @param newDog to get the dog id.
     */
    private void saveDogBreed(Animal animal, Dog newDog) {
        Breed breed = new Breed();
        breed.setPrimary(animal.getBreeds().getPrimary());
        breed.setSecondary(animal.getBreeds().getSecondary());
        breed.setUnknown(animal.getBreeds().isUnknown());
        breed.setMixed(animal.getBreeds().isMixed());
        breed.setDogId(newDog.getId());
        breedRepository.save(breed);
    }

    /**
     * Helper method to save the Dog data into database
     * @param animal Incoming response
     * @return saved data into db.
     */
    private Dog saveDog(Animal animal) {
        Dog dog = new Dog();
        dog.setName(animal.getName());
        dog.setUrl(animal.getUrl());
        dog.setAge(animal.getAge());
        dog.setGender(animal.getGender());
        dog.setSize(animal.getSize());
        dog.setOrgID(animal.getOrganization_id());
        dog.setDogID(animal.getId());

        dogRepository.save(dog);
        return dog;
    }

    /**
     * Helper method to save the Organisation data into database
     * @param animal Incoming response
     */
    private void saveOrganisation(Animal animal) {
        Organisation organisation = new Organisation();
        organisation.setOrgID(animal.getOrganization_id());
        organisation.setName(animal.getOrganization_id());
        organisation.setPhone(animal.getContact().getPhone());
        organisation.setEmail(animal.getContact().getEmail());
        organisation.setCity(animal.getContact().getAddress().getCity());
        organisation.setCountry(animal.getContact().getAddress().getCountry());
        organisation.setState(animal.getContact().getAddress().getState());
        organisation.setPostcode(animal.getContact().getAddress().getPostcode());
        organisation.setAddress1(animal.getContact().getAddress().getAddress1());
        organisation.setAddress2(animal.getContact().getAddress().getAddress2());
        organisationRepository.save(organisation);
    }

}
