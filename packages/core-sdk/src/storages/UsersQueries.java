package storages;

import exceptions.NotFoundUserException;
import exceptions.UnknownFailureException;
import entities.User;

import java.util.List;
import java.util.UUID;

public interface UsersQueries {

    public static final String usersTableName = "users";

    List<User> listUsers() throws UnknownFailureException;

    User getUserById(UUID userId) throws NotFoundUserException, UnknownFailureException;

}
