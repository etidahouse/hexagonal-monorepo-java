package dev.begon.hexagonal.core.sdk.messaging;

import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;

public interface UsersMessagingQueries {

    void listenUserStoredEvent(User user) throws UnknownFailureException;

    void listenUserRemovedEvent(User user) throws UnknownFailureException;

}
