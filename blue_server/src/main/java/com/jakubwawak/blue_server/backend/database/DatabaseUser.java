/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.blue_server.backend.database;

import com.jakubwawak.blue_server.BlueServerApplication;
import com.jakubwawak.blue_server.entity.BlueUser;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

/**
 * Object for maintaining user data on database
 */
public class DatabaseUser {

    Database database;

    /**
     * Constructor
     */
    public DatabaseUser(){
        this.database = BlueServerApplication.database;
    }

    /**
     * Function for getting user
     * @param email
     * @return BlueUser
     */
    public BlueUser getUser(String email) {
        try {
            MongoCollection<Document> collection = database.getCollection("blue_users");
            Document query = new Document("user_email", email);
            Document found = collection.find(query).first();
            if (found != null) {
                BlueServerApplication.database.log("DB-USER-GET", "User found: " + found.getString("user_email"));
                return new BlueUser(found);
            } else {
                BlueServerApplication.database.log("DB-USER-GET", "User not found: " + email);
                return null;
            }
        } catch (Exception e) {
            BlueServerApplication.database.log("DB-USER-GET", "Error: " + e.toString());
            return null;
        }
    }

    /**
     * Function for creating user
     * @param user
     * @return BlueUser
     */
    public BlueUser createUser(BlueUser user){
        try{
            if ( getUser(user.userEmail) != null ){
                BlueServerApplication.database.log("DB-USER-CREATE","User already exists: "+user.userEmail);
                return null;
            }
            else{
                MongoCollection<Document> collection = database.getCollection("blue_users");
                Document doc = user.prepareDocument();
                InsertOneResult result = collection.insertOne(doc);
                if ( result.wasAcknowledged() ){
                    BlueServerApplication.database.log("DB-USER-CREATE","User created: "+user.userEmail);
                    getUser(user.userEmail);
                    return user;
                }
                else{
                    BlueServerApplication.database.log("DB-USER-CREATE","Failed to create user: "+user.userEmail);
                    return null;
                }
            }
        }catch (Exception e){
            BlueServerApplication.database.log("DB-USER-CREATE","Error: "+e.toString());
            return null;
        }
    }
}
