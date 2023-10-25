package users;

import java.util.List;
import java.util.UUID;

import entities.User;
import exceptions.UnknownFailureException;
import storages.UsersQueries;

public class ListUsers {
    
    public static List<User> execute(UsersQueries usersQueries) throws UnknownFailureException {
        return usersQueries.listUsers();
    }

}
