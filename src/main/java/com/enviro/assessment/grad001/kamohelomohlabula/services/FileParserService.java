package com.enviro.assessment.grad001.kamohelomohlabula.services;

import com.enviro.assessment.grad001.kamohelomohlabula.entities.AccountProfile;
import com.enviro.assessment.grad001.kamohelomohlabula.interfaces.FileParser;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.*;

/**
 * FileParserService class
 */
@Component
public class FileParserService implements FileParser {
    private final AccountProfileService accountProfileService;
    private static final String RESOURCES_DIRECTORY_PATH = System.getProperty("user.dir") + "/src/main/resources/tmp";

    @Autowired
    public FileParserService(AccountProfileService accountProfileService) {
        this.accountProfileService = accountProfileService;
    }

    /**
     * Convert the contents of the file and store the records into a database
     * @param csvFile file to convert and store contents
     */
    @Override
    public void parseCSV(File csvFile) {
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {
            // Get all the rows in the csv
            List<String[]> csvRows = csvReader.readAll();

            // Remove the header row from the entries rows
            csvRows.remove(0);

            // Create a list of accountProfiles, so we can save them all at once in the database record
            List<AccountProfile> accountProfiles = new ArrayList<>();

            for (String[] csvRow : csvRows) {
                // Get the following fields assuming the CSV structure: ['name', 'surname', 'imageFormat', 'imageData']
                String accountHolderName = csvRow[0];
                String accountHolderSurname = csvRow[1];
                String imageFormat = csvRow[2];
                String imageData = csvRow[3];

                // Get the image file
                File imageFile = convertCSVDataToImage(
                        imageData,
                        imageFormat,
                        accountHolderName + "_" + accountHolderSurname
                );

                // Get the image link
                URI imageLink = createImageLink(imageFile);
                URL httpImageLink = imageLink.toURL();

                // Create accountProfile
                AccountProfile accountProfile = new AccountProfile();
                accountProfile.setName(accountHolderName);
                accountProfile.setSurname(accountHolderSurname);
                accountProfile.setHttpImageLink(httpImageLink.toString());
                accountProfiles.add(accountProfile);
            }

            // Save the accountProfiles in the database
            accountProfileService.saveAll(accountProfiles);
        } catch (CsvException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts base64 image data from a flat file into a physical file with the format specified
     * @param base64ImageData the image data from the flat file
     * @param imageFormat format the image must be converted into
     * @param fileName name of the physical file
     * @return the image physical file
     */
    @Override
    public File convertCSVDataToImage(String base64ImageData, String imageFormat, String fileName) {
        // Convert Base64 image data to a physical image file
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageData);

        // Make 'png' the default format if imageFormat is empty
        imageFormat = imageFormat.isBlank() ? "png" : imageFormat;

        // Remove the prefix 'image/' if it exists
        imageFormat = imageFormat.replaceFirst("(?i)^image/", "");

        // Create the filepath located under the ../resources/tmp directory
        String filePath = RESOURCES_DIRECTORY_PATH + "/" + fileName.replaceAll("/s", "_") + "." + imageFormat;

        File imageFile = new File(filePath);

        try (FileOutputStream outputStream = new FileOutputStream(imageFile)) {
            outputStream.write(imageBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return imageFile;
    }

    /**
     * Creates the link to access the physical image file
     * @param fileImage image file to create link from
     * @return link for accessing the physical image file
     */
    @Override
    public URI createImageLink(File fileImage) {
        try {
            return fileImage.toURI();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
