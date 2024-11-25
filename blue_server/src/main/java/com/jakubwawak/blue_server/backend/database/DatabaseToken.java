/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * blue server - all rights reserved
 */
package com.jakubwawak.blue_server.backend.database;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.jakubwawak.blue_server.BlueServerApplication;
import com.jakubwawak.blue_server.entity.BlueToken;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

/**
 * Object for handling token database
 */
public class DatabaseToken {

    Database database;

    /**
     * Constructor
     */
    public DatabaseToken() {
        database = BlueServerApplication.database;
    }

    /**
     * Function for inserting token
     * 
     * @param token
     * @return int
     */
    public int insertToken(BlueToken token) {
        MongoCollection<Document> collection = database.getCollection("tokens");
        Document doc = token.prepareDocument();
        InsertOneResult result = collection.insertOne(doc);
        if (result.getInsertedId() != null) {
            BlueServerApplication.database.log("TOKEN-INSERTION",
                    "Token inserted for user: " + token.user_id.toString());
            return 1;
        } else {
            BlueServerApplication.database.log("TOKEN-INSERTION-FAILED",
                    "Token insertion failed for user: " + token.user_id.toString());
            return 0;
        }
    }

    /**
     * Function for expiring all tokens for user
     * 
     * @param user_id
     * @return int
     */
    public void expireAllUserTokens(ObjectId user_id) {
        MongoCollection<Document> collection = database.getCollection("tokens");
        UpdateResult result = collection.updateMany(Filters.eq("user_id", user_id), Updates.set("expired", true));
        if (result.getModifiedCount() > 0) {
            BlueServerApplication.database.log("TOKEN-EXPIRATION",
                    "All tokens expired for user: " + user_id.toString());
        }
    }

}
