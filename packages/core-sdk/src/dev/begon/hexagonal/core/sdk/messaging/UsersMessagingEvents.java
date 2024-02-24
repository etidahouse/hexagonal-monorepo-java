package dev.begon.hexagonal.core.sdk.messaging;

import java.util.function.Consumer;

import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;

public interface UsersMessagingEvents {
    
    void publishUserEvent(UserMessaging user) throws UnknownFailureException;

    void listenUserEvents(Consumer<UserMessaging> eventHandler) throws UnknownFailureException;

}
