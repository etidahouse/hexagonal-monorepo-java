import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import com.mongodb.client.MongoDatabase;

import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.exceptions.NotFoundUserException;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;
import dev.begon.hexagonal.mongodb.sdk.MongoUsersCommands;
import dev.begon.hexagonal.mongodb.sdk.MongoUsersQueries;

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

    @Test
    public void testRemoveUser_Success_to_remove_user() throws UnknownFailureException, NotFoundUserException {
        MongoDatabase storage = TestUtils.mongoStorage("db");

        MongoUsersCommands usersCommands = new MongoUsersCommands(storage);
        User user = new User(UUID.randomUUID(), "username", "email");
        usersCommands.storeUser(user);

        MongoUsersQueries usersQueries = new MongoUsersQueries(storage);
        User userResult = usersQueries.getUserById(user.getId());

        assertEquals(user, userResult);

        usersCommands.removeUser(user);

        assertThrows(NotFoundUserException.class, () ->
            usersQueries.getUserById(UUID.randomUUID())
        );
    }

    @Test
    public void testRemoveUser_Success_without_user() throws UnknownFailureException, NotFoundUserException {
        MongoDatabase storage = TestUtils.mongoStorage("db");

        MongoUsersCommands usersCommands = new MongoUsersCommands(storage);
        User user = new User(UUID.randomUUID(), "username", "email");

        MongoUsersQueries usersQueries = new MongoUsersQueries(storage);
        assertThrows(NotFoundUserException.class, () ->
            usersQueries.getUserById(UUID.randomUUID())
        );

        usersCommands.removeUser(user);
    }

}
