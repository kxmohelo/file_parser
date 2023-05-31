package com.enviro.assessment.grad001.kamohelomohlabula.repositories;

import com.enviro.assessment.grad001.kamohelomohlabula.entities.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountProfileRepository extends JpaRepository<AccountProfile, Long> {
    Optional<AccountProfile> findByNameAndSurname(String name, String surname);
}
