import entities.User;
import exceptions.NotFoundUserException;
import exceptions.UnknownFailureException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mongodb.client.MongoDatabase;


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
/*
    @Test
    public void testGetAllUsers_Success_Empty_List() throws Exception {
        // Mock the behavior of listUsers
        Mockito.when(usersQueries.listUsers()).thenReturn(Collections.emptyList());

        // Perform a GET request to the endpoint
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
                /*.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty())
                .andReturn();
                
                String content = result.getResponse().getContentAsString();
            System.out.println("CONTENT");
                System.out.println(content);
    }


    @Test
    public void testGetAllUsers_Success() throws Exception {
        // Mock the behavior of listUsers
        Mockito.when(usersQueries.listUsers())
                .thenReturn(Arrays.asList(
                        new User(UUID.randomUUID(), "username1", "email1"),
                        new User(UUID.randomUUID(), "username2", "email2")
                ));

        // Perform a GET request to the endpoint
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/users")
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].username").value("username1"))
            .andExpect(jsonPath("$[1].username").value("username2"))
            .andExpect(jsonPath("$[0].email").value("email1"))
            .andExpect(jsonPath("$[1].email").value("email2"))
            .andReturn();

    String content = result.getResponse().getContentAsString();
    System.out.println(content);
    }
 */
    @Test
    public void testRetrievedUserById_Success() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "username1", "email1");

        // Mock the behavior of getUserById
        Mockito.when(usersQueries.getUserById(userId)).thenReturn(user);

        // Perform a GET request to the endpoint
        MvcResult result = mockMvc.perform(get("/api/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value("username1"))
                .andExpect(jsonPath("$.email").value("email1"))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

}
