import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import entities.User;
import users.ListUsers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private MongoUsersQueries usersQueries;

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = ListUsers.execute(usersQueries);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("An error occurred during the test:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> retrievedUserById(@PathVariable UUID userId) {
        try {
            User user = usersQueries.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.error("An error occurred during the test:", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
