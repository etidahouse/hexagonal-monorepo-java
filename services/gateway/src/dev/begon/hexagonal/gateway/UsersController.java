package dev.begon.hexagonal.gateway;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.exceptions.NotFoundUserException;
import dev.begon.hexagonal.core.sdk.storages.UsersQueries;
import dev.begon.hexagonal.core.sdk.users.ListUsers;
import dev.begon.hexagonal.core.sdk.users.RetrieveUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "500", description = "Unknown failure", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getAllUsers() throws JsonProcessingException {
        try {
            List<User> users = ListUsers.execute(usersQueries);
            return ResponseEntity.ok(objectMapper.writeValueAsString(users));
        } catch (Exception e) {
            logger.error("An error occurred during the test:", e);
            return ResponseEntity.status(500).body(objectMapper.writeValueAsString(new ErrorResponse(e)));
        }
    }

    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unknown failure", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(value = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retrievedUserById(@PathVariable UUID userId) throws JsonProcessingException {
        try {
            User user = RetrieveUser.execute(userId, usersQueries);
            return ResponseEntity.ok(objectMapper.writeValueAsString(user));
        } catch (NotFoundUserException e) {
            return ResponseEntity.status(404).body(objectMapper.writeValueAsString(new ErrorResponse(e)));
        } catch (Exception e) {
            logger.error("An error occurred during the test:", e);
            return ResponseEntity.status(500).body(objectMapper.writeValueAsString(new ErrorResponse(e)));
        }
    }

}
