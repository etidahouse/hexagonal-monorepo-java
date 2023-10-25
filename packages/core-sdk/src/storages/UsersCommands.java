package storages;

import exceptions.NotFoundUserException;
import exceptions.UnknownFailureException;
import entities.User;

import java.util.List;

public interface UsersCommands {

    Void storeUser(User user) throws UnknownFailureException;

    Void updateUser(User user) throws NotFoundUserException, UnknownFailureException;

}
