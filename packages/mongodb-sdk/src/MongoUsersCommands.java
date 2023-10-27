import storages.UsersCommands;

import entities.User;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.ReplaceOptions;

import static com.mongodb.client.model.Filters.eq;

import org.bson.Document;
import org.bson.conversions.Bson;

import exceptions.UnknownFailureException;


public class MongoUsersCommands implements UsersCommands {

    private final MongoDatabase storage;
    private final MongoCollection<Document> usersCollection;

    public MongoUsersCommands(MongoDatabase storage) {
        this.storage = storage;
        usersCollection = this.storage.getCollection(usersTableName);
    }

    @Override
    public void storeUser(User user) throws UnknownFailureException {
        try {
            Bson query = eq("_id", user.getId());

            ReplaceOptions options = new ReplaceOptions().upsert(true);

            usersCollection.replaceOne(query, UsersStorage.userToDocument(user), options);
        } catch(Exception e) {
            throw new UnknownFailureException("Failed to store user", e);
        }
    }
    
}
