/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * guideiro app - all rights reserved
 */
package com.jakubwawak.blue_server.actions.token_engine;

import com.jakubwawak.blue_server.BlueServerApplication;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Base64;

/**
 * Object for validating JWT tokens
 */
public class JwtTokenValidator {

    private String secretKey = BlueServerApplication.properties.getValue("blueSecret");
    private final Key SECRET_KEY;


    /**
     * Constructor
     */
    public JwtTokenValidator(){
        byte[] decodedKey = Base64.getDecoder().decode(secretKey);
        SECRET_KEY = Keys.hmacShaKeyFor(decodedKey);
    }

    /**
     * Function for verifying token
     * @param token
     * @return boolean
     */
    public boolean verifyToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String user_id = claims.get("user_id",String.class);
            BlueServerApplication.database.log("TOKEN-VALIDATION","Token valid for user (user_id): "+user_id+" granting access");
            return true; // Token is valid
        } catch (ExpiredJwtException e) {
            BlueServerApplication.database.log("TOKEN-VALIDATION","Token expired for user (user_id): "+e.getClaims().get("user_id"));
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            BlueServerApplication.database.log("TOKEN-VALIDATION","Token invalid: "+e.getMessage());
        }
        return false;
    }
}
