import java.util.UUID;

import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

import org.bson.conversions.Bson;

import dev.begon.hexagonal.mongodb.sdk.MongoStorage;
import static dev.begon.hexagonal.mongodb.sdk.UsersStorage.usersTableName;

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
        storage.getCollection(usersTableName).deleteOne(query);
    }
 
}
