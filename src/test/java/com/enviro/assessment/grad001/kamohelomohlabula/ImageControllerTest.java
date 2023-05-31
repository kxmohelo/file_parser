package com.enviro.assessment.grad001.kamohelomohlabula;

import com.enviro.assessment.grad001.kamohelomohlabula.entities.AccountProfile;
import com.enviro.assessment.grad001.kamohelomohlabula.services.AccountProfileService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.ResourceUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountProfileService accountProfileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getHttpImageLink_ShouldReturnImageFile_WhenAccountProfileExists() throws Exception {
        // Create an account profile
        AccountProfile accountProfile = new AccountProfile();

        // Set the account profile httpImageLink
        String httpImageLink = ResourceUtils.getFile("classpath:Momentum_Health.png").toURI().toString();
        accountProfile.setHttpImageLink(httpImageLink);

        // Mock the accountProfileService.getByNameAndSurname method
        when(accountProfileService.getByNameAndSurname(anyString(), anyString()))
                .thenReturn(Optional.of(accountProfile));

        // Perform the GET request
        MvcResult result = mockMvc.perform(get("/v1/api/image/{name}/{surname}/{filename}", "Momentum", "Health", "image.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE))
                .andReturn();

        byte[] responseBody = result.getResponse().getContentAsByteArray();
        assertNotNull(responseBody);
    }


    @Test
    void getHttpImageLink_ShouldReturnNotFound_WhenAccountProfileDoesNotExist() throws Exception {
        // Mock the accountProfileService.getByNameAndSurname method
        when(accountProfileService.getByNameAndSurname(anyString(), anyString()))
                .thenReturn(Optional.empty());

        // Perform the GET request
        mockMvc.perform(get("/v1/api/image/{name}/{surname}/{filename}", "John", "Doe", "image.jpg"))
                .andExpect(status().isNotFound());
    }
}
