/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * blue server - all rights reserved
 */
package com.jakubwawak.blue_server.entity;

import java.sql.Timestamp;

import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Object for storing token data
 */
public class BlueToken {

    private ObjectId _id;
    public ObjectId user_id;
    private String token;
    private Timestamp expiration_date;
    private boolean expired;
    private Timestamp creation_date;

    /**
     * Constructor
     */
    public BlueToken(ObjectId user_id, String token, Timestamp expiration_date) {
        this._id = new ObjectId();
        this.user_id = user_id;
        this.token = token;
        this.expiration_date = expiration_date;
        this.expired = false;
        this.creation_date = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Function for preparing document
     * 
     * @return Document
     */
    public Document prepareDocument() {
        Document doc = new Document();
        doc.append("user_id", user_id);
        doc.append("token", token);
        doc.append("expiration_date", expiration_date);
        doc.append("expired", expired);
        doc.append("creation_date", creation_date);
        return doc;
    }

}
