package com.treat.tinder.datasource;

import com.treat.tinder.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Integer> {

    @Query(value = "SELECT d.id, d.dogId,d.name,d.url, d.age, d.gender, d.size, d.orgID FROM Dog d " +
            "JOIN organisation o on d.orgid=o.org_id " +
            "JOIN breed b on b.dog_id = d.id " +
            "WHERE o.name = :organisationName " +
            "and d.gender = :gender " +
            "and (b.primary_breed = :primaryBreed OR b.secondary_breed = :secondaryBreed " +
            "and b.mixed = :mixed OR b.not_known = :notKnown) " +
            "and (o.city = :city OR o.state = :state OR o.postcode = :postcode OR o.country = :country)", nativeQuery = true)
    List<Dog> filterDogs(String organisationName, String gender, String primaryBreed,
                         String secondaryBreed, boolean mixed, boolean notKnown,
                         String city, String state, String postcode, String country);

    Dog findByDogID(Long id);
}