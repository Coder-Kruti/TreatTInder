package com.example.treat.tinder.datasource;

import com.example.treat.tinder.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DogRepository extends JpaRepository<Dog, Integer> {
//    @Query("SELECT d FROM Dog d WHERE (:organizationName IS NULL OR d.organization.name = :organizationName)" +
//            " AND (:gender IS NULL OR d.gender = :gender)" +
//            " AND (:breed IS NULL OR d.breed = :breed)" +
//            " AND (:location IS NULL OR d.location = :location)")
//    List<Dog> findDogsByFilterOptions(@Param("organizationName") String organizationName,
//                                      @Param("gender") String gender,
//                                      @Param("breed") String breed,
//                                      @Param("location") String location);


    Dog findByDogID(Long id);
}