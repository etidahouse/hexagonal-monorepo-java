package storages;

import exceptions.NotFoundUserException;
import exceptions.UnknownFailureException;
import entities.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UsersCommands {

    public interface InsertUser {
        CompletableFuture<Void> execute(User user) throws UnknownFailureException;
    }

    public interface UpdateUser {
        CompletableFuture<Void> execute(User user) throws NotFoundUserException, UnknownFailureException;
    }

}
