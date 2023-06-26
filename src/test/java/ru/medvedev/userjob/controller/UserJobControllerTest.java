package ru.medvedev.userjob.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.LocalHostUriTemplateHandler;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.medvedev.userjob.UserjobApplication;
import ru.medvedev.userjob.resource.controller.UserJobController;

import java.io.File;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "test")
@WebAppConfiguration
@SpringBootTest(classes = UserjobApplication.class)
@Sql("classpath:data/inserts.sql")
@Sql(value = "classpath:data/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserJobControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    private LocalHostUriTemplateHandler uriTemplateHandler;

    @Before
    public void setUpMockMvc() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        uriTemplateHandler = new LocalHostUriTemplateHandler(webApplicationContext.getEnvironment(), "http");
    }

    @Test
    public void createUserJobTest() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(objectMapper
                .readValue(new File("src/test/resources/json/createPositive.json"), Object.class));
        mockMvc.perform(post(uriTemplateHandler.getRootUri() + UserJobController.BASE_PATH + "/create-userjob")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateUserJobTest() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(objectMapper
                .readValue(new File("src/test/resources/json/updatePositive.json"), Object.class));
        mockMvc.perform(patch(uriTemplateHandler.getRootUri() + UserJobController.BASE_PATH + "/update-userjob")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk());
    }

    @Test
    public void getUserJobTest() throws Exception {
        mockMvc.perform(get(uriTemplateHandler.getRootUri() + UserJobController.BASE_PATH + "/get-userjob")
                .queryParam("userId", "3"))
                .andExpect(status().isOk());
    }
}
