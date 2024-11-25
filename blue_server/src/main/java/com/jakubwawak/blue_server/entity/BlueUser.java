/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.blue_server.entity;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.jakubwawak.blue_server.BlueServerApplication;

import java.sql.Timestamp;

/**
 * Object for storing user data
 */
public class BlueUser {

    private ObjectId _id;
    public boolean userActive;
    public boolean userProfilePublic;
    public String userApiKey;
    public String userEmail;
    private String userPassword;
    private String salt;
    private Timestamp userRegistrationDate;
    private String userDescription;
    private String userPhotoUrl;

    /**
     * Constructor
     */
    public BlueUser() {
        _id = new ObjectId();
        userActive = false;
        userProfilePublic = false;
        userApiKey = "";
        userEmail = "";
        userPassword = "";
        userRegistrationDate = new Timestamp(System.currentTimeMillis());
        userDescription = "";
        userPhotoUrl = "";
        salt = "";
    }

    /**
     * Constructor with database support
     * 
     * @param document
     */
    public BlueUser(Document document) {
        _id = document.getObjectId("_id");
        userActive = document.getBoolean("user_active");
        userProfilePublic = document.getBoolean("user_profile_public");
        userApiKey = document.getString("user_api_key");
        userEmail = document.getString("user_email");
        userPassword = document.getString("user_password");
        userRegistrationDate = document.get("user_registration_date", Timestamp.class);
        userDescription = document.getString("user_description");
        userPhotoUrl = document.getString("user_photo_url");
        salt = document.getString("salt");
    }

    /**
     * Function for preparing document
     */
    public Document prepareDocument() {
        Document doc = new Document();
        doc.append("user_id", _id);
        doc.append("user_active", userActive);
        doc.append("user_profile_public", userProfilePublic);
        doc.append("user_api_key", userApiKey);
        doc.append("user_email", userEmail);
        doc.append("user_password", userPassword);
        doc.append("user_registration_date", userRegistrationDate);
        doc.append("user_description", userDescription);
        doc.append("user_photo_url", userPhotoUrl);
        doc.append("salt", salt);
        return doc;
    }

    /**
     * Function for setting user password
     * 
     * @param password
     */
    public void setPassword(String password) {
        try {
            String salt = BCrypt.gensalt();
            this.salt = salt;
            this.userPassword = BCrypt.hashpw(password, salt);
        } catch (Exception e) {
            BlueServerApplication.database.log("PASSWORD-HASHING-FAILED",
                    "Failed to hash password: " + e.toString());
        }
    }

    /**
     * Function for validating password
     * 
     * @param password
     * @return boolean
     */
    public boolean validatePassword(String password) {
        try {
            BlueServerApplication.database.log("PASSWORD-VALIDATION",
                    "id: " + _id.toString() + " - trying to validate password");
            return BCrypt.checkpw(password, this.userPassword);
        } catch (Exception e) {
            BlueServerApplication.database.log("PASSWORD-VALIDATION-FAILED",
                    "Failed to validate password: " + e.toString());
            return false;
        }
    }

    public void setUserPhotoUrl(String userPhotoUrl) {
        this.userPhotoUrl = userPhotoUrl;
    }

    public void setUserDescription(String userDescription) {
        this.userDescription = userDescription;
    }

    public void setUserRegistrationDate(Timestamp userRegistrationDate) {
        this.userRegistrationDate = userRegistrationDate;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public ObjectId get_id() {
        return _id;
    }

    public boolean isActive() {
        return userActive;
    }

    public boolean isUserProfilePublic() {
        return userProfilePublic;
    }

    public String getUserApiKey() {
        return userApiKey;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public Timestamp getUserRegistrationDate() {
        return userRegistrationDate;
    }

    public String getUserDescription() {
        return userDescription;
    }

    public String getUserPhotoUrl() {
        return userPhotoUrl;
    }
}
