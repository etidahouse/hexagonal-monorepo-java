package dev.begon.hexagonal.core.sdk.messaging;

import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;

public interface UsersMessagingCommands {
    
    void publishUserStoredEvent(User user) throws UnknownFailureException;

    void publishUserRemovedEvent(User user) throws UnknownFailureException;

}
