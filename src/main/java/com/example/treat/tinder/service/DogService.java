package com.example.treat.tinder.service;

import com.example.treat.tinder.datasource.BreedRepository;
import com.example.treat.tinder.datasource.CustomerInteractionRepository;
import com.example.treat.tinder.datasource.CustomerRepository;
import com.example.treat.tinder.datasource.DogRepository;
import com.example.treat.tinder.datasource.OrganisationRepository;
import com.example.treat.tinder.entity.Animal;
import com.example.treat.tinder.entity.Breed;
import com.example.treat.tinder.entity.Dog;
import com.example.treat.tinder.entity.DogFilterOptions;
import com.example.treat.tinder.entity.Gender;
import com.example.treat.tinder.entity.Organisation;
import com.example.treat.tinder.entity.PetFinderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class DogService {
    @Autowired
    private final DogRepository dogRepository;
    @Autowired
    private final BreedRepository breedRepository;
    @Autowired
    private final CustomerInteractionRepository customerInteractionRepository;
    @Autowired
    private final OrganisationRepository organisationRepository;
    @Autowired
    private final CustomerRepository customerRepository;


    DogService(DogRepository dogRepository, BreedRepository breedRepository, CustomerInteractionRepository customerInteractionRepository, OrganisationRepository organisationRepository, CustomerRepository customerRepository) {
        this.dogRepository = dogRepository;
        this.breedRepository = breedRepository;
        this.customerInteractionRepository = customerInteractionRepository;
        this.organisationRepository = organisationRepository;
        this.customerRepository = customerRepository;
    }

//    public List<Dog> filterDogs(DogFilterOptions filterOptions) {
//        HashSet<Dog> uniqueDogs = new HashSet<>();
//        List<Dog> dogList = new ArrayList<>();
//        if (filterOptions.getGender() != null) {
//            List<Dog> genderFiltered = dogRepository.findByGender(filterOptions.getGender());
//            dogList.addAll(genderFiltered);
//        }
//        if (filterOptions.getBreed() != null) {
//            List<Dog> breedFiltered = dogRepository.findByBreed( filterOptions.getBreed().getPrimary() , filterOptions.getBreed().getSecondary(),
//                    filterOptions.getBreed().isMixed(), filterOptions.getBreed().isUnknown());
//
//            dogList.addAll(breedFiltered);
//        }
//        return dogList;
//
//
////        return dogRepository.filterDogs(filterOptions.getOrganizationName(), filterOptions.getGender(),
////                filterOptions.getBreed().getPrimary() , filterOptions.getBreed().getSecondary(),
////                filterOptions.getBreed().isMixed(), filterOptions.getBreed().isUnknown(),
////                filterOptions.getContact().getAddress().getCity(), filterOptions.getContact().getAddress().getState(),
////                filterOptions.getContact().getAddress().getPostcode(), filterOptions.getContact().getAddress().getCountry());
//
//    }

    public List<Dog> filterDogs(DogFilterOptions dogFilterOptions) {

        String organisationName = dogFilterOptions.getOrganizationName();
        String primaryBreed = dogFilterOptions.getBreed().getPrimary();
        String secondaryBreed = dogFilterOptions.getBreed().getSecondary();
        boolean mixed = dogFilterOptions.getBreed().isMixed();
        boolean notKnown = dogFilterOptions.getBreed().isUnknown();
        Gender gender = dogFilterOptions.getGender();
        String city = dogFilterOptions.getContact().getAddress().getCity();
        String state = dogFilterOptions.getContact().getAddress().getState();
        String postCode = dogFilterOptions.getContact().getAddress().getPostcode();
        String country = dogFilterOptions.getContact().getAddress().getCountry();

        return dogRepository.filterDogs(organisationName, gender.name(), primaryBreed, secondaryBreed,
                mixed, notKnown, city, state, postCode, country);

    }

    public void getDogsPetFinder() {
        PetFinder petFinder = new PetFinder();
        PetFinderResponse petFinderResponse = petFinder.getDogsPetFinder();

        // store the response into db .
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

    }

    private void saveDogBreed(Animal animal, Dog newDog) {
        Breed breed = new Breed();
        breed.setPrimary(animal.getBreeds().getPrimary());
        breed.setSecondary(animal.getBreeds().getSecondary());
        breed.setUnknown(animal.getBreeds().isUnknown());
        breed.setMixed(animal.getBreeds().isMixed());
        breed.setDogId(newDog.getId());
        breedRepository.save(breed);
    }

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

    public void saveOrganisation(Animal animal) {
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
