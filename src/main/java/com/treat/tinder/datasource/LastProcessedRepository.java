package com.treat.tinder.datasource;

import com.treat.tinder.entity.LastProcessed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LastProcessedRepository extends JpaRepository<LastProcessed, Integer> {
}
