import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.mockito.Mockito;

import entities.User;
import exceptions.UnknownFailureException;
import storages.UsersQueries;
import users.ListUsers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListUsersTest {

    @Test
    public void testListUsers_Success() throws UnknownFailureException {

        UsersQueries mockUsersQueries = Mockito.mock(UsersQueries.class);

        List<User> mockUsersList = new ArrayList<>();
        mockUsersList.add(new User(UUID.randomUUID(), "username", "email"));
        mockUsersList.add(new User(UUID.randomUUID(), "user-name", "em-ail"));

        Mockito.when(mockUsersQueries.listUsers()).thenReturn(mockUsersList);
        
        List<User> result = ListUsers.execute(mockUsersQueries);

        assertEquals(2, result.size());

        assertEquals(mockUsersList.get(0), result.get(0));
        assertEquals(mockUsersList.get(1), result.get(1));

        Mockito.verify(mockUsersQueries, Mockito.times(1)).listUsers();
    }


    @Test
    public void testListUsers_UnknownFailureException() throws UnknownFailureException {
        UsersQueries mockUsersQueries = Mockito.mock(UsersQueries.class);

        Mockito.when(mockUsersQueries.listUsers()).thenThrow(new UnknownFailureException("Unknown failure occurred"));

        assertThrows(UnknownFailureException.class, () ->
                ListUsers.execute(mockUsersQueries)
        );
    }

}
