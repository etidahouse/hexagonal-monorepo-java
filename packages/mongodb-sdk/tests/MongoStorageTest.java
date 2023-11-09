import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mongodb.client.MongoDatabase;

import dev.begon.mongodb.sdk.MongoStorage;

public class MongoStorageTest {

    @Test
    public void testDatabase_Success() {
        String databaseName = "db";
        MongoDatabase db = MongoStorage.database(
            "localhost",
            27017,
            databaseName, 
            "mongodb",
            "password");
        assertEquals(db.getName(), databaseName);
    }
    
}
