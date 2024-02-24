package dev.begon.hexagonal.core.sdk.events;

import dev.begon.hexagonal.core.sdk.exceptions.UnknownFailureException;
import dev.begon.hexagonal.core.sdk.messaging.UserMessaging;
import dev.begon.hexagonal.core.sdk.messaging.UsersMessagingEvents;
import dev.begon.hexagonal.core.sdk.storages.UsersCommands;

public class MongoDBEventProcessor {
    
    private final UsersMessagingEvents usersMessagingEvents;
    private final UsersCommands usersCommands;

    public MongoDBEventProcessor(UsersMessagingEvents usersMessagingEvents, UsersCommands usersCommands) {
        this.usersMessagingEvents = usersMessagingEvents;
        this.usersCommands = usersCommands;
    }

    public void replicateMongoDBEvents() throws UnknownFailureException {
        usersMessagingEvents.listenUserEvents(t -> {
            try {
                handleUserEvent(t);
            } catch (UnknownFailureException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleUserEvent(UserMessaging userMessaging) throws UnknownFailureException {
        switch (userMessaging.getActionKindMessaging()) {
            case STORE:
                usersCommands.storeUser(userMessaging.getUser());
                break;
            case REMOVE:
                usersCommands.removeUser(userMessaging.getUser());
                break;
            default:
                break;
        }
    }

}
