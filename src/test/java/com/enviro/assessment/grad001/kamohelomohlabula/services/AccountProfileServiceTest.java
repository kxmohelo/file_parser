package com.enviro.assessment.grad001.kamohelomohlabula.services;

import com.enviro.assessment.grad001.kamohelomohlabula.entities.AccountProfile;
import com.enviro.assessment.grad001.kamohelomohlabula.repositories.AccountProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class AccountProfileServiceTest {

    @Autowired
    private AccountProfileService accountProfileService;

    @Autowired
    private AccountProfileRepository accountProfileRepository;

    @BeforeEach
    void setUp() {
        // Clear the repository before each test
        accountProfileRepository.deleteAll();
    }

    @Test
    void getByNameAndSurname_ShouldReturnAccountProfile_WhenProfileExists() {
        // Create account profile
        AccountProfile accountProfile = new AccountProfile();
        accountProfile.setName("John");
        accountProfile.setSurname("Doe");
        accountProfile.setHttpImageLink("http://example.com/image.jpg");
        accountProfileRepository.save(accountProfile);

        // Call getByNameAndSurname method
        Optional<AccountProfile> result = accountProfileService.getByNameAndSurname("John", "Doe");

        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
        assertEquals("Doe", result.get().getSurname());
        assertEquals("http://example.com/image.jpg", result.get().getHttpImageLink());
    }

    @Test
    void getByNameAndSurname_ShouldReturnEmptyOptional_WhenProfileDoesNotExist() {
        // Call getByNameAndSurname method
        Optional<AccountProfile> result = accountProfileService.getByNameAndSurname("Jane", "Smith");

        assertTrue(result.isEmpty());
    }

    @Test
    void saveAll_ShouldSaveAllAccountProfiles() {
        // Create a list of account profiles
        List<AccountProfile> accountProfiles = new ArrayList<>();
        AccountProfile profile1 = new AccountProfile();
        profile1.setName("John");
        profile1.setSurname("Doe");
        profile1.setHttpImageLink("http://example.com/image1.jpg");
        accountProfiles.add(profile1);

        AccountProfile profile2 = new AccountProfile();
        profile2.setName("Jane");
        profile2.setSurname("Smith");
        profile2.setHttpImageLink("http://example.com/image2.jpg");
        accountProfiles.add(profile2);

        // Call saveAll method
        List<AccountProfile> savedProfiles = accountProfileService.saveAll(accountProfiles);

        assertEquals(2, savedProfiles.size());
        assertTrue(savedProfiles.contains(profile1));
        assertTrue(savedProfiles.contains(profile2));
    }

    @Test
    void save_ShouldSaveAccountProfile() {
        // Create account profile
        AccountProfile accountProfile = new AccountProfile();
        accountProfile.setName("John");
        accountProfile.setSurname("Doe");
        accountProfile.setHttpImageLink("http://example.com/image.jpg");

        // Call save method
        AccountProfile savedProfile = accountProfileService.save(accountProfile);

        assertNotNull(savedProfile.getId());
        assertEquals("John", savedProfile.getName());
        assertEquals("Doe", savedProfile.getSurname());
        assertEquals("http://example.com/image.jpg", savedProfile.getHttpImageLink());
    }
}

