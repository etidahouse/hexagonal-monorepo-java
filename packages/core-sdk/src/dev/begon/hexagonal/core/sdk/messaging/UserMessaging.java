package dev.begon.hexagonal.core.sdk.messaging;

import dev.begon.hexagonal.core.sdk.entities.User;

public class UserMessaging {
    
    private KindMessaging kind;
    private ActionKindMessaging actionKind;
    private User user;

    public UserMessaging() { }

    public UserMessaging(KindMessaging kind, ActionKindMessaging actionKind, User user) {
        this.kind = kind;
        this.actionKind = actionKind;
        this.user = user;
    }

    public UserMessaging userMessagingFromUser(KindMessaging kind, ActionKindMessaging actionKind, User user) {
        return new UserMessaging(kind, actionKind, user);
    }

    public KindMessaging getKindMessaging() {
        return this.kind;
    }

    public ActionKindMessaging getActionKindMessaging() {
        return this.actionKind;
    }

    public User getUser() {
        return this.user;
    }

}
