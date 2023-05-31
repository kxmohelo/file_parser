package com.enviro.assessment.grad001.kamohelomohlabula.services;

import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ResourcesCleanUpService {

    private static final String RESOURCES_DIRECTORY_PATH = System.getProperty("user.dir") + "/src/main/resources/tmp/";

    /**
     * Removes resources that do not need to be saved locally on the filesystem
     */
    @PreDestroy
    public void cleanUpResources() {
        File resourcesDirectory = new File(RESOURCES_DIRECTORY_PATH);
        if (resourcesDirectory.exists()) {
            deleteDirectoryContents(resourcesDirectory);
        }
    }

    /**
     * Deletes contents of the specified directory
     * @param directory the directory with the contents to delete
     */
    private void deleteDirectoryContents(File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Delete all files and subdirectories recursively
                    deleteDirectoryContents(file);
                } else {
                    file.delete(); // Delete file
                }
            }
        }
    }
}

