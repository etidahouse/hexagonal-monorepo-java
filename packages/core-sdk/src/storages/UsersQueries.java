package storages;

import exceptions.NotFoundUserException;
import exceptions.UnknownFailureException;
import entities.User;

import java.util.List;
import java.util.UUID;

public interface UsersQueries {

    List<User> listUsers() throws UnknownFailureException;

    User getUserById(UUID userId) throws NotFoundUserException, UnknownFailureException;

}
