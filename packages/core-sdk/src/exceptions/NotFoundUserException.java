package exceptions;

import java.util.UUID;

public class NotFoundUserException extends Exception {

    public NotFoundUserException(UUID id) {
        super("User with " + id + " not found.");
    }

}
