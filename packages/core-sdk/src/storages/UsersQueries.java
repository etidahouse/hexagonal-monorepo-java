package storages;

import exceptions.UnknownFailureException;
import entities.User;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UsersQueries {

    public interface SelectUsers {
        CompletableFuture<List<User>> execute() throws UnknownFailureException;
    }

}
