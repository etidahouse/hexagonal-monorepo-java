import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import entities.User;
import exceptions.UnknownFailureException;
import storages.UsersQueries;

public class ListUsers {
    
    public List<User> execute(UsersQueries usersQueries) throws UnknownFailureException {
        try {
            CompletableFuture<List<User>> usersFuture = usersQueries.selectUsers();
            return usersFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new UnknownFailureException(e.getMessage(), e.getCause());
        }
    }

}
