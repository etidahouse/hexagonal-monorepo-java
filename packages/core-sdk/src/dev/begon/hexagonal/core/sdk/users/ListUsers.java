package dev.begon.hexagonal.core.sdk.users;

import java.util.List;

import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;
import dev.begon.hexagonal.core.sdk.storages.UsersQueries;

public class ListUsers {
    
    public static List<User> execute(UsersQueries usersQueries) throws UnknownFailureException {
        return usersQueries.listUsers();
    }

}
