package com.enviro.assessment.grad001.kamohelomohlabula.services;

import com.enviro.assessment.grad001.kamohelomohlabula.entities.AccountProfile;
import com.enviro.assessment.grad001.kamohelomohlabula.repositories.AccountProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * AccountProfileService class
 */
@Service
public class AccountProfileService {
    private final AccountProfileRepository accountProfileRepository;

    @Autowired
    public AccountProfileService(AccountProfileRepository accountProfileRepository) {
        this.accountProfileRepository = accountProfileRepository;
    }

    /***
     * Get AccountProfile by name and surname
     * @param name name of the account profile holder
     * @param surname surname of the account profile holder
     * @return AccountProfile object
     */
    public Optional<AccountProfile> getByNameAndSurname(String name, String surname) {
        return accountProfileRepository.findByNameAndSurname(name, surname).or(Optional::empty);
    }

    /**
     * Saves a list of AccountProfiles
     *
     * @param accountProfiles account profiles being saved
     * @return the list of saved accountProfiles
     */
    public List<AccountProfile> saveAll(List<AccountProfile> accountProfiles) {
        return this.accountProfileRepository.saveAll(accountProfiles).stream().toList();
    }

    public AccountProfile save(AccountProfile accountProfile) {
        return this.accountProfileRepository.save(accountProfile);
    }
}

