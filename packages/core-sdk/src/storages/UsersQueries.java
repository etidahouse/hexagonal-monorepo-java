package storages;

import exceptions.UnknownFailureException;
import entities.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UsersQueries {

    CompletableFuture<List<User>> selectUsers() throws UnknownFailureException;

}
