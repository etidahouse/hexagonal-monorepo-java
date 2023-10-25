import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.mongodb.client.MongoDatabase;

public class StorageTest {

    @Test
    public void database_Success() {
        MongoDatabase db = Storage.database();
        assertEquals(db.getName(), Storage.DB_NAME);
    }
    
}
