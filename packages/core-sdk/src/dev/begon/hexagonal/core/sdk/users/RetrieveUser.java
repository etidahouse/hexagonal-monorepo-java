package dev.begon.hexagonal.core.sdk.users;

import java.util.UUID;

import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.exceptions.NotFoundUserException;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;
import dev.begon.hexagonal.core.sdk.storages.UsersQueries;

public class RetrieveUser {
    
    public static User execute(UUID userId, UsersQueries usersQueries) throws UnknownFailureException, NotFoundUserException {
        return usersQueries.getUserById(userId);
    }

}
