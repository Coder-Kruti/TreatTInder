package com.example.treat.tinder.datasource;

import com.example.treat.tinder.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrganisationRepository extends JpaRepository<Organisation, Integer> {
    Organisation findByOrgID(String organizationId);
}