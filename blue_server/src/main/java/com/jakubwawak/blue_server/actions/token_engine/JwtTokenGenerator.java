/**
 * by Jakub Wawak
 * kubawawak@gmail.comś
 * blue server - all rights reserved
 */
package com.jakubwawak.blue_server.actions.token_engine;

import com.jakubwawak.blue_server.BlueServerApplication;
import com.jakubwawak.blue_server.backend.database.DatabaseToken;
import com.jakubwawak.blue_server.entity.BlueToken;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.sql.Timestamp;

/**
 * Object for generating JWT tokens
 */
public class JwtTokenGenerator {

    private String blueSecret;

    private final Key SECRET_KEY;

    long expirationTimeInMillis = Long.parseLong(BlueServerApplication.properties.getValue("tokenExpirationTime"));

    /**
     * Constructor
     */
    public JwtTokenGenerator(String blueSecret) {
        // Check if the blueSecret is a valid 256-bit key
        if (blueSecret.length() != 32) { // Assuming blueSecret is in plain text
            throw new IllegalArgumentException("blueSecret must be a 256-bit key (32 bytes long).");
        }
        this.blueSecret = blueSecret;
        byte[] decodedKey = Base64.getDecoder().decode(blueSecret);
        SECRET_KEY = Keys.hmacShaKeyFor(decodedKey);
    }

    /**
     * Function for generating token
     * 
     * @param user_id
     * @return String
     */
    public String generateToken(ObjectId user_id) {
        // invalidate other tokens
        DatabaseToken dt = new DatabaseToken();
        dt.expireAllUserTokens(user_id);
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expirationTimeInMillis);
        String token = Jwts.builder()
                .setSubject(user_id.toString())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SECRET_KEY)
                .compact();
        BlueServerApplication.database.log("TOKEN-GENERATION", "Token generated for user: " + user_id.toString());
        int ans = dt.insertToken(new BlueToken(user_id, token, new Timestamp(expirationDate.getTime())));
        if (ans == 1) {
            return token;
        }
        return null;
    }
}
