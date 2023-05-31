package com.enviro.assessment.grad001.kamohelomohlabula.controllers;

import com.enviro.assessment.grad001.kamohelomohlabula.entities.AccountProfile;
import com.enviro.assessment.grad001.kamohelomohlabula.services.AccountProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/api/image")
public class ImageController {
    private final AccountProfileService accountProfileService;

    @Autowired
    public ImageController(AccountProfileService accountProfileService) {
        this.accountProfileService = accountProfileService;
    }

    @GetMapping(value = "/{name}/{surname}/{\\w\\.\\w}")
    public ResponseEntity<FileSystemResource> getHttpImageLink(@PathVariable String name, @PathVariable String surname) throws URISyntaxException {
        // Get the account profile
        Optional<AccountProfile> accountProfile = accountProfileService.getByNameAndSurname(name, surname);

        if (accountProfile.isPresent() && !accountProfile.get().getHttpImageLink().isBlank()) {
            String accountProfileHttpImageLink = accountProfile.get().getHttpImageLink();

            // Convert string url to uri
            URI uri = new URI(accountProfileHttpImageLink);

            // Create a fileSystemResource object using the uri
            FileSystemResource fileSystemResource = new FileSystemResource(uri.getPath());

            // Return the resource as a ResponseEntity
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(fileSystemResource);
        }
        return ResponseEntity.notFound().build();
    }
}
