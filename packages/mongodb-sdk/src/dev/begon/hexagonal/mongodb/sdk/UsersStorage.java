package dev.begon.hexagonal.mongodb.sdk;

import dev.begon.hexagonal.core.sdk.entities.User;

import java.util.UUID;

import org.bson.Document;

public class UsersStorage {

    public static final String usersTableName = "users";

    public static User userFromDocument(Document document) {
        UUID id = (UUID) document.get("_id");
        String username = document.getString("username");
        String email = document.getString("email");
        
        return new User(id, username, email);
    }

    public static Document userToDocument(User user) {
        Document document = new Document();
        document.append("_id", user.getId());
        document.append("username", user.getUsername());
        document.append("email", user.getEmail());
        return document;
    }
    
}
