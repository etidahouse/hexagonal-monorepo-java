import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.exceptions.NotFoundUserException;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;
import dev.begon.hexagonal.core.sdk.storages.UsersQueries;
import dev.begon.hexagonal.core.sdk.users.RetrieveUser;

class RetrieveUserTest {

    @Mock
    private UsersQueries usersQueries;

    @Test
    void testExecute_WhenUserExists_ReturnsUser() throws UnknownFailureException, NotFoundUserException {
        UUID userId = UUID.randomUUID();
        User expectedUser = new User(userId, "username", "email");

        MockitoAnnotations.openMocks(this);
        when(usersQueries.getUserById(userId)).thenReturn(expectedUser);

        User actualUser = RetrieveUser.execute(userId, usersQueries);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    void testExecute_WhenUserDoesNotExist_ThrowsNotFoundUserException() throws UnknownFailureException, NotFoundUserException {
        UUID userId = UUID.randomUUID();

        MockitoAnnotations.openMocks(this);
        when(usersQueries.getUserById(userId)).thenThrow(new NotFoundUserException(userId));

        assertThrows(NotFoundUserException.class, () -> RetrieveUser.execute(userId, usersQueries));
    }

    @Test
    void testExecute_WhenUnknownFailure_ThrowsUnknownFailureException() throws UnknownFailureException, NotFoundUserException {
        UUID userId = UUID.randomUUID();

        MockitoAnnotations.openMocks(this);
        when(usersQueries.getUserById(userId)).thenThrow(new UnknownFailureException("Unknown failure"));

        assertThrows(UnknownFailureException.class, () -> RetrieveUser.execute(userId, usersQueries));
    }
}
