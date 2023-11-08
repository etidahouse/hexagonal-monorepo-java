package dev.begon.hexagonal.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.storages.UsersQueries;
import dev.begon.hexagonal.core.sdk.users.ListUsers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersQueries usersQueries;

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllUsers() {
        try {
            List<User> users = ListUsers.execute(usersQueries);
            return ResponseEntity.ok(objectMapper.writeValueAsString(users));
        } catch (Exception e) {
            logger.error("An error occurred during the test:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrievedUserById(@PathVariable UUID userId) {
        try {
            User user = usersQueries.getUserById(userId);
            return ResponseEntity.ok(objectMapper.writeValueAsString(user));
        } catch (Exception e) {
            logger.error("An error occurred during the test:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}