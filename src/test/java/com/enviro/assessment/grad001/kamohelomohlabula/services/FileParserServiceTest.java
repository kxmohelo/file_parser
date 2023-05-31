package com.enviro.assessment.grad001.kamohelomohlabula.services;

import com.enviro.assessment.grad001.kamohelomohlabula.entities.AccountProfile;
import com.enviro.assessment.grad001.kamohelomohlabula.repositories.AccountProfileRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import org.mockito.MockitoAnnotations;

import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class FileParserServiceTest {
    @Autowired
    private FileParserService fileParserService;

    @Autowired
    private AccountProfileRepository accountProfileRepository;

    @Autowired
    private AccountProfileService accountProfileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void parseCSV_ShouldSaveAccountProfiles_WhenCSVFileIsValid() throws IOException {
        File csvFile = ResourceUtils.getFile("classpath:account_profiles.csv");

        // Call parseCSV method
        fileParserService.parseCSV(csvFile);

        // Get all account profiles
        List<AccountProfile> accountProfiles = accountProfileRepository.findAll();

        assertEquals(2, accountProfiles.size());
        assertTrue(accountProfiles.stream()
                .anyMatch(
                        profile -> profile.getName().equals("Enviro365") && profile.getSurname().equals("IT Solutions")
                )
        );
        assertTrue(accountProfiles.stream()
                .anyMatch(
                        profile -> profile.getName().equals("Momentum") && profile.getSurname().equals("Health")
                )
        );
        assertTrue(accountProfiles.stream()
                .anyMatch(
                        profile -> ! profile.getHttpImageLink().isBlank() && ! profile.getHttpImageLink().isBlank()
                )
        );
    }

    @Test
    void parseCSV_ShouldSaveAccountProfiles_WhenCSVFileIsNotValid() throws IOException {
        File csvFile = ResourceUtils.getFile("classpath:Momentum_Health.png");

        assertThrows(RuntimeException.class, () -> fileParserService.parseCSV(csvFile));
    }

    @Test
    void convertCSVDataToImage_ShouldReturnImageFile_WhenCSVDataIsValid() throws IOException, CsvException {
        // Read csv file
        File csvFile = ResourceUtils.getFile("classpath:account_profiles.csv");
        CSVReader csvReader = new CSVReader(new FileReader(csvFile));

        // Get csv row
        String[] csvRow = csvReader.readAll().get(1);

        // Get row attributes
        String name = csvRow[0];
        String surname = csvRow[0];
        String imageFormat = csvRow[2];
        String imageData = csvRow[3];

        // Call convertCSVDataToImage method
        File imageFile = fileParserService.convertCSVDataToImage(imageData, imageFormat, name + "_" + surname);

        assertNotNull(imageFile);
        assertTrue(imageFile.exists());

        String expectedFileName = name + "_" + surname + "." + imageFormat.replaceFirst("(?i)^image/", "");

        assertEquals(expectedFileName, imageFile.getName());

        // Clean up
        imageFile.delete();
    }

    @Test
    void convertCSVDataToImage_ShouldThrowIllegalArgumentException_WhenCSVDataIsInvalid() {
        // Mock csv row attributes
        String name = "some_name";
        String surname = "some_surname";
        String imageFormat = "image/jpg";
        String imageData = "invalid-base64-encoded-image-data";

        assertThrows(
                IllegalArgumentException.class, () ->
                        fileParserService.convertCSVDataToImage(imageData, imageFormat, name + "_" + surname)
        );
    }

    @Test
    void convertCSVDataToImage_ShouldThrowNullPointerException_WhenCSVDataIsNull() {
        // Mock csv row attributes
        String name = "some_name";
        String surname = "some_surname";
        String imageFormat = "image/jpg";

        assertThrows(
                NullPointerException.class, () ->
                        fileParserService.convertCSVDataToImage(null, imageFormat, name + "_" + surname)
        );
    }

    @Test
    void createImageLink_ShouldReturnValidURILink_WhenFileExists() throws FileNotFoundException {
        // Get file
        File imageFile = ResourceUtils.getFile("classpath:Momentum_Health.png");

        // Call createImageLink method
        URI imageLink = fileParserService.createImageLink(imageFile);

        assertNotNull(imageLink);
        assertEquals(imageFile.toURI(), imageLink);
    }

    @Test
    void createImageLink_ShouldReturnValidURILink_WhenFileDoesNotExist() {
        String filePath = "path/to/nonexistent/file.png";
        File nonExistentFile = new File(filePath);

        // Call createImageLink method
        URI imageLink = fileParserService.createImageLink(nonExistentFile);

        assertEquals(nonExistentFile.toURI(), imageLink);
    }

    @Test
    void createImageLink_ShouldThrowRuntimeException_WhenFileIsNull() {
        assertThrows(RuntimeException.class, () -> fileParserService.createImageLink(null));
    }
}
