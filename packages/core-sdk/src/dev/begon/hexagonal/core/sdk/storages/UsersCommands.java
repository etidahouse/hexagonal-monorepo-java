package dev.begon.hexagonal.core.sdk.storages;

import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;
import dev.begon.hexagonal.core.sdk.entities.User;

public interface UsersCommands {

    void storeUser(User user) throws UnknownFailureException;

    void removeUser(User user) throws UnknownFailureException;

}
