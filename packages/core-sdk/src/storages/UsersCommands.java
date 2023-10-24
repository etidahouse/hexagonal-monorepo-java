package storages;

import exceptions.NotFoundUserException;
import exceptions.UnknownFailureException;
import entities.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UsersCommands {

    CompletableFuture<Void> insertUser(User user) throws UnknownFailureException;

    CompletableFuture<Void> updateUser(User user) throws NotFoundUserException, UnknownFailureException;

}
