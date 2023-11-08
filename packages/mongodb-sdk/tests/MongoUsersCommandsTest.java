import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import com.mongodb.client.MongoDatabase;

import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.exceptions.NotFoundUserException;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;
import dev.begon.mongodb.sdk.MongoUsersCommands;
import dev.begon.mongodb.sdk.MongoUsersQueries;

public class MongoUsersCommandsTest {
    
    @Test
    public void testStoreUser_Success_to_insert() throws UnknownFailureException, NotFoundUserException {
        MongoDatabase storage = TestUtils.mongoStorage("db");

        MongoUsersCommands usersCommands = new MongoUsersCommands(storage);
        User user = new User(UUID.randomUUID(), "username", "email");
        usersCommands.storeUser(user);

        MongoUsersQueries usersQueries = new MongoUsersQueries(storage);
        User userResult = usersQueries.getUserById(user.getId());

        assertEquals(user, userResult);

        TestUtils.removeUserById(storage, user.getId());
    }

    @Test
    public void testStoreUser_Success_to_update() throws UnknownFailureException, NotFoundUserException {
        MongoDatabase storage = TestUtils.mongoStorage("db");

        MongoUsersCommands usersCommands = new MongoUsersCommands(storage);
        User user = new User(UUID.randomUUID(), "username", "email");
        usersCommands.storeUser(user);

        MongoUsersQueries usersQueries = new MongoUsersQueries(storage);
        User userInsertResult = usersQueries.getUserById(user.getId());

        assertEquals(user, userInsertResult);

        user.setUsername("my-new-username");
        usersCommands.storeUser(user);
        User userUpdateResult = usersQueries.getUserById(user.getId());

        assertEquals(user, userUpdateResult);

        TestUtils.removeUserById(storage, userInsertResult.getId());
    }

}
