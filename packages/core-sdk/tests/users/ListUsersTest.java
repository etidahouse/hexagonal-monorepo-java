import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.Mockito;

import entities.User;
import exceptions.UnknownFailureException;
import storages.UsersQueries;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class ListUsersTest {

    @Test
    public void testListUsers_Success() throws UnknownFailureException {

        UsersQueries mockUsersQueries = Mockito.mock(UsersQueries.class);

        List<User> mockUsersList = new ArrayList<>();
        mockUsersList.add(new User(UUID.randomUUID(), "username", "email"));
        mockUsersList.add(new User(UUID.randomUUID(), "user-name", "em-ail"));
        CompletableFuture<List<User>> mockFuture = CompletableFuture.completedFuture(mockUsersList);
        Mockito.when(mockUsersQueries.selectUsers()).thenReturn(mockFuture);

        ListUsers listUsers = new ListUsers();
        List<User> result = listUsers.execute(mockUsersQueries);

        assertEquals(2, result.size());

        assertEquals(mockUsersList.get(0), result.get(0));
        assertEquals(mockUsersList.get(1), result.get(1));

        Mockito.verify(mockUsersQueries, Mockito.times(1)).selectUsers();
    }

    @Test
    public void testListUsers_UnknownFailureException() throws UnknownFailureException {
        UsersQueries mockUsersQueries = Mockito.mock(UsersQueries.class);

        CompletableFuture<List<User>> mockFuture = new CompletableFuture<>();
        mockFuture.completeExceptionally(new ExecutionException(new UnknownFailureException("Unknown failure occurred")));

        Mockito.when(mockUsersQueries.selectUsers()).thenReturn(mockFuture);

        ListUsers listUsers = new ListUsers();

        assertThrows(UnknownFailureException.class, () -> 
            listUsers.execute(mockUsersQueries),
            "Unknown failure occurred"
        );
    }

}
