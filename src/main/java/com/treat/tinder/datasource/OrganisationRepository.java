package com.treat.tinder.datasource;

import com.treat.tinder.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganisationRepository extends JpaRepository<Organisation, Integer> {
    Organisation findByOrgID(String organizationId);
}