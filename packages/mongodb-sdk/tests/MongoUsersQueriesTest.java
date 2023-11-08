import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.mongodb.client.MongoDatabase;

import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.exceptions.NotFoundUserException;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;
import dev.begon.mongodb.sdk.MongoUsersCommands;
import dev.begon.mongodb.sdk.MongoUsersQueries;

public class MongoUsersQueriesTest {

    @Test
    public void testListUsers_Success_without_user() throws UnknownFailureException {
        MongoDatabase storage = TestUtils.mongoStorage("db_without_users");

        MongoUsersQueries usersQueries = new MongoUsersQueries(storage);

        List<User> usersResult = usersQueries.listUsers();

        assertEquals(0, usersResult.size());
    }

    @Test
    public void testListUsers_Success() throws UnknownFailureException {
        MongoDatabase storage = TestUtils.mongoStorage("db_with_some_users");

        MongoUsersQueries usersQueries = new MongoUsersQueries(storage);

        MongoUsersCommands usersCommands = new MongoUsersCommands(storage);

        User user1 = new User(UUID.randomUUID(), "username", "email");
        User user2 = new User(UUID.randomUUID(), "user-name", "em-ail");

        usersCommands.storeUser(user1);
        usersCommands.storeUser(user2);

        List<User> usersResult = usersQueries.listUsers();

        assertEquals(2, usersResult.size());
        assertEquals(Arrays.asList(user1, user2), usersResult);

        TestUtils.removeUserById(storage, user1.getId());
        TestUtils.removeUserById(storage, user2.getId());
    }

    @Test
    public void testGetUserById_Failed_Not_found() throws UnknownFailureException, NotFoundUserException {
        MongoDatabase storage = TestUtils.mongoStorage("db");

        MongoUsersQueries usersQueries = new MongoUsersQueries(storage);

        assertThrows(NotFoundUserException.class, () ->
            usersQueries.getUserById(UUID.randomUUID())
        );
    }

    @Test
    public void testGetUserById_Success() throws UnknownFailureException, NotFoundUserException {
        MongoDatabase storage = TestUtils.mongoStorage("db");

        MongoUsersQueries usersQueries = new MongoUsersQueries(storage);

        MongoUsersCommands usersCommands = new MongoUsersCommands(storage);
        User user = new User(UUID.randomUUID(), "username", "email");
        usersCommands.storeUser(user);

        User userResult = usersQueries.getUserById(user.getId());

        assertEquals(user, userResult);

        TestUtils.removeUserById(storage, user.getId());
    }

}
