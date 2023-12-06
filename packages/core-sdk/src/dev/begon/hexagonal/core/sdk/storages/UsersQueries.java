package dev.begon.hexagonal.core.sdk.storages;

import dev.begon.hexagonal.core.sdk.exceptions.NotFoundUserException;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;
import dev.begon.hexagonal.core.sdk.entities.User;

import java.util.List;
import java.util.UUID;

public interface UsersQueries {

    List<User> listUsers() throws UnknownFailureException;

    User getUserById(UUID userId) throws NotFoundUserException, UnknownFailureException;

}
