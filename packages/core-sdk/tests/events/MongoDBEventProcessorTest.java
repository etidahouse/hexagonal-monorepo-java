package events;

import dev.begon.hexagonal.core.sdk.entities.User;
import dev.begon.hexagonal.core.sdk.events.MongoDBEventProcessor;
import dev.begon.hexagonal.core.sdk.messaging.ActionKindMessaging;
import dev.begon.hexagonal.core.sdk.messaging.KindMessaging;
import dev.begon.hexagonal.core.sdk.messaging.UserMessaging;
import dev.begon.hexagonal.core.sdk.messaging.UsersMessagingEvents;
import dev.begon.hexagonal.core.sdk.storages.UsersCommands;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.UUID;
import java.util.function.Consumer;

public class MongoDBEventProcessorTest {

    @Test
    public void testReplicateMongoDBEvents_StoreUser() throws Exception {
        UsersMessagingEvents mockUsersMessagingEvents = Mockito.mock(UsersMessagingEvents.class);
        UsersCommands mockUsersCommands = Mockito.mock(UsersCommands.class);

        User user = new User(UUID.randomUUID(), "username", "email");
        UserMessaging event = new UserMessaging(KindMessaging.USER, ActionKindMessaging.STORE, user);

        MongoDBEventProcessor eventProcessor = new MongoDBEventProcessor(mockUsersMessagingEvents, mockUsersCommands);
        eventProcessor.replicateMongoDBEvents();

        ArgumentCaptor<Consumer<UserMessaging>> eventHandlerCaptor = ArgumentCaptor.forClass(Consumer.class);
        verify(mockUsersMessagingEvents).listenUserEvents(eventHandlerCaptor.capture());

        eventHandlerCaptor.getValue().accept(event);

        verify(mockUsersCommands).storeUser(user);
        verify(mockUsersCommands, times(1)).storeUser(user);
        verify(mockUsersMessagingEvents, times(1)).listenUserEvents(any());
    }

    @Test
    public void testReplicateMongoDBEvents_RemoveUser() throws Exception {
        UsersMessagingEvents mockUsersMessagingEvents = Mockito.mock(UsersMessagingEvents.class);
        UsersCommands mockUsersCommands = Mockito.mock(UsersCommands.class);

        User user = new User(UUID.randomUUID(), "username", "email");
        UserMessaging event = new UserMessaging(KindMessaging.USER, ActionKindMessaging.REMOVE, user);

        MongoDBEventProcessor eventProcessor = new MongoDBEventProcessor(mockUsersMessagingEvents, mockUsersCommands);
        eventProcessor.replicateMongoDBEvents();

        ArgumentCaptor<Consumer<UserMessaging>> eventHandlerCaptor = ArgumentCaptor.forClass(Consumer.class);
        verify(mockUsersMessagingEvents).listenUserEvents(eventHandlerCaptor.capture());

        eventHandlerCaptor.getValue().accept(event);

        verify(mockUsersCommands).removeUser(user);
        verify(mockUsersCommands, times(1)).removeUser(user);
        verify(mockUsersMessagingEvents, times(1)).listenUserEvents(any());
    }
}
