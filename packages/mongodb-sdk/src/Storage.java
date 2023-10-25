import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class Storage {
    
    public static final String DB_NAME = "db";

    public static MongoDatabase database() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        return mongoClient.getDatabase(DB_NAME);
    }

}
