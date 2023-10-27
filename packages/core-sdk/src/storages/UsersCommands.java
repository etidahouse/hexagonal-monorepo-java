package storages;

import exceptions.NotFoundUserException;
import exceptions.UnknownFailureException;
import entities.User;

import java.util.List;

public interface UsersCommands {

    public static final String usersTableName = "users";

    void storeUser(User user) throws UnknownFailureException;

}
