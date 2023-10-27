import java.util.UUID;

import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

import org.bson.conversions.Bson;

import storages.UsersCommands;

public class TestUtils {
    
    public static MongoDatabase mongoStorage(String databaseName) {
        return MongoStorage.database(
        "localhost",
        27017,
        databaseName, 
        "mongodb",
        "password");
    }

    public static void removeUserById(MongoDatabase storage, UUID userId) {
        Bson query = eq("_id", userId);
        storage.getCollection(UsersCommands.usersTableName).deleteOne(query);
    }
 
}
