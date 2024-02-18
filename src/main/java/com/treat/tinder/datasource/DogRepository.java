package com.treat.tinder.datasource;

import com.treat.tinder.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogRepository extends JpaRepository<Dog, Integer> {

    @Query(value = "SELECT d.id, d.dogId, d.name, d.url, d.age, d.gender, d.size, d.orgID " +
            "FROM Dog d " +
            "JOIN Organisation o ON d.orgid = o.org_id " +
            "JOIN Breed b ON b.dog_id = d.id " +
            "WHERE (:organisationName IS NULL OR o.name = :organisationName) " +
            "AND (:gender IS NULL OR d.gender = :gender) " +
            "AND ((:primaryBreed IS NULL OR b.primary_breed = :primaryBreed) " +
            "OR (:secondaryBreed IS NULL OR b.secondary_breed = :secondaryBreed) " +
            "OR (:mixed IS NULL OR b.mixed = :mixed) " +
            "OR (:notKnown IS NULL OR b.not_known = :notKnown)) " +
            "AND ((:city IS NULL OR o.city = :city) " +
            "OR (:state IS NULL OR o.state = :state) " +
            "OR (:postcode IS NULL OR o.postcode = :postcode) " +
            "OR (:country IS NULL OR o.country = :country))", nativeQuery = true)
    List<Dog> filterDogs(
            @Param("organisationName") String organisationName,
            @Param("gender") String gender,
            @Param("primaryBreed") String primaryBreed,
            @Param("secondaryBreed") String secondaryBreed,
            @Param("mixed") Boolean mixed,
            @Param("notKnown") Boolean notKnown,
            @Param("city") String city,
            @Param("state") String state,
            @Param("postcode") String postcode,
            @Param("country") String country
    );

    Dog findByDogID(Long id);
}