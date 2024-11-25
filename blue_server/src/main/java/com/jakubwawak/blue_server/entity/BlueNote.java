/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * all rights reserved
 */
package com.jakubwawak.blue_server.entity;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.Timestamp;
import java.util.List;
import org.json.JSONObject;

/**
 * Object for storing note data
 */
public class BlueNote {

    public ObjectId _id;
    public String note_title;
    public ObjectId note_owner_id;
    public boolean note_public;
    public String note_private_password;
    public Timestamp note_expiration_time;
    public Timestamp note_creation_time;
    public String note_resources_url;
    public String note_private_url;
    public List<JSONObject> note_raw_history;
    public String note_raw_text;

    /**
     * No-argument constructor
     */
    public BlueNote() {
        this._id = new ObjectId();
        this.note_title = "";
        this.note_owner_id = new ObjectId();
        this.note_public = false;
        this.note_private_password = "";
        this.note_expiration_time = new Timestamp(System.currentTimeMillis());
        this.note_creation_time = new Timestamp(System.currentTimeMillis());
        this.note_resources_url = "";
        this.note_private_url = "";
        this.note_raw_history = null;
        this.note_raw_text = "";
    }

    /**
     * Prepares a MongoDB document from the BlueNote object
     * @return Document
     */
    public Document prepareDocument() {
        Document document = new Document();
        document.append("_id", _id);
        document.append("note_title", note_title);
        document.append("note_owner_id", note_owner_id);
        document.append("note_public", note_public);
        document.append("note_private_password", note_private_password);
        document.append("note_expiration_time", note_expiration_time);
        document.append("note_creation_time", note_creation_time);
        document.append("note_resources_url", note_resources_url);
        document.append("note_private_url", note_private_url);
        document.append("note_raw_history", note_raw_history);
        document.append("note_raw_text", note_raw_text);
        return document;
    }
}
