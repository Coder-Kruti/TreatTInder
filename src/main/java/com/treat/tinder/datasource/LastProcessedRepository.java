package com.treat.tinder.datasource;

import com.treat.tinder.entity.LastProcessed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastProcessedRepository extends JpaRepository<LastProcessed, Integer> {
//    @Query("SELECT l.page FROM LastProcessed l ORDER BY l.id DESC LIMIT 1")
//    Integer getLastProcessedPage();
//    public LastProcessed findByID()

}
