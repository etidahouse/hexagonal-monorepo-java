package dev.begon.hexagonal.gateway;

import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.exceptions.NotFoundUserException;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import dev.begon.mongodb.sdk.MongoUsersQueries;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {UsersController.class})
@AutoConfigureMockMvc
public class UsersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MongoUsersQueries usersQueries;

    static {
        System.setProperty("MONGODB_USER", "mongodb_user");
        System.setProperty("MONGODB_PASSWORD", "mongodb_password");
        System.setProperty("MONGODB_HOSTNAME", "mongodb_hostname");
        System.setProperty("MONGODB_DATABASE_NAME", "mongodb_database_name");
        System.setProperty("MONGODB_PORT", "1234");
    }

    @Test
    public void testGetAllUsers_Success_Empty_List() throws Exception {
        Mockito.when(usersQueries.listUsers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty())
                .andReturn();
    }


    @Test
    public void testGetAllUsers_Success() throws Exception {
        Mockito.when(usersQueries.listUsers())
                .thenReturn(Arrays.asList(
                        new User(UUID.randomUUID(), "username1", "email1"),
                        new User(UUID.randomUUID(), "username2", "email2")
                ));


        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].username").value("username1"))
            .andExpect(jsonPath("$[1].username").value("username2"))
            .andExpect(jsonPath("$[0].email").value("email1"))
            .andExpect(jsonPath("$[1].email").value("email2"))
            .andReturn();
    }

    @Test
    public void testGetAllUsers_UnknownFailureException() throws Exception {
        Mockito.when(usersQueries.listUsers()).thenThrow(new UnknownFailureException("Something went wrong"));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("UnknownFailureException"))
                .andExpect(jsonPath("$.message").value("Something went wrong"));
    }
 
    @Test
    public void testRetrievedUserById_Success() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "username1", "email1");

        Mockito.when(usersQueries.getUserById(userId)).thenReturn(user);

         mockMvc.perform(get("/api/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.username").value("username1"))
                .andExpect(jsonPath("$.email").value("email1"))
                .andReturn();
    }

    @Test
    public void testRetrievedUserById_UnknownFailureException() throws Exception {
        UUID userId = UUID.randomUUID();
        Mockito.when(usersQueries.getUserById(userId)).thenThrow(new UnknownFailureException("Something went wrong"));

        mockMvc.perform(get("/api/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("UnknownFailureException"))
                .andExpect(jsonPath("$.message").value("Something went wrong"));
    }

    @Test
    public void testRetrievedUserById_NotFoundUserException() throws Exception {
        UUID userId = UUID.randomUUID();
        Mockito.when(usersQueries.getUserById(userId)).thenThrow(new NotFoundUserException(userId));

        mockMvc.perform(get("/api/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("NotFoundUserException"))
                .andExpect(jsonPath("$.message").value("User with " + userId + " not found."));
    }

}
