import storages.UsersQueries;

import entities.User;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.FindIterable;

import org.bson.Document;

import exceptions.NotFoundUserException;
import exceptions.UnknownFailureException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MongoUsersQueries implements UsersQueries {
    
    private final MongoDatabase storage;
    private final MongoCollection<Document> usersCollection;

    public MongoUsersQueries(MongoDatabase storage) {
        this.storage = storage;
        usersCollection = this.storage.getCollection(usersTableName);
    }

    @Override
    public List<User> listUsers() throws UnknownFailureException {
        try {
            FindIterable<Document> usersDocuments = usersCollection.find();

            List<User> users = new ArrayList<>();
            for (Document document : usersDocuments) {
                users.add(UsersStorage.userFromDocument(document));
            }

            return users;
        } catch (Exception e) {
            throw new UnknownFailureException("Failed to list users", e);
        }
    }

    @Override
    public User getUserById(UUID userId) throws NotFoundUserException, UnknownFailureException {
        try {
            Document userDocument = usersCollection.find(Filters.eq("_id", userId)).first();
            if (userDocument == null) throw new NotFoundUserException(userId);

            return UsersStorage.userFromDocument(userDocument);
        } catch (NotFoundUserException e) {
            throw e; // Rethrow the custom exception
        } catch (Exception e) {
            throw new UnknownFailureException("Failed to get user by id", e);
        }
    }

}
