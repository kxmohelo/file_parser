package com.enviro.assessment.grad001.kamohelomohlabula.services;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
class ResourcesCleanUpServiceTest {

    @Autowired
    private ResourcesCleanUpService resourcesCleanUpService;

    @Test
    void cleanUpResources_ShouldDeleteAllFilesInResourcesDirectory() throws IOException {
        // Create a temporary file in the resources directory
        File tempFile = new File(System.getProperty("user.dir") + "/src/main/resources/tmp/test.txt");

        boolean fileCreated = tempFile.createNewFile();
        assertTrue(fileCreated);

        // Call cleanUpResources method
        resourcesCleanUpService.cleanUpResources();

        // Check if the file has been deleted
        assertFalse(tempFile.exists());
    }
}

