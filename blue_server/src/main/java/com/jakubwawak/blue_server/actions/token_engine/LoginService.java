/**
 * by Jakub Wawak
 * kubawawak@gmail.com
 * guideiro app - all rights reserved
 */
package com.jakubwawak.blue_server.actions.token_engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakubwawak.blue_server.BlueServerApplication;
import com.jakubwawak.blue_server.backend.database.DatabaseUser;
import com.jakubwawak.blue_server.entity.BlueUser;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Object for handling login service
 */
public class LoginService {

    DatabaseUser databaseUser;

    /**
     * Constructor
     */
    public LoginService() {
        databaseUser = new DatabaseUser();
    }

    /**
     * Function for logging in user using login page
     * 
     * @param username
     * @param password
     * @return String
     */
    public String loginPlain(String username, String password) {
        try {
            BlueServerApplication.database.log("LOGIN-ATTEMPT", "User: " + username + " trying to login");
            BlueUser user = databaseUser.getUser(username);
            if (user != null) {
                if (user.isActive()) {
                    if (user.validatePassword(password)) {
                        BlueServerApplication.database.log("LOGIN-USER-ACTIVE", "User: " + username + " logged in");
                        return "LOGIN-USER-ACTIVE";
                    } else {
                        BlueServerApplication.database.log("LOGIN-USER-WRONG-PASSWORD",
                                "User: " + username + " failed to login");
                        return "LOGIN-USER-WRONG-PASSWORD";
                    }
                } else {
                    BlueServerApplication.database.log("LOGIN-USER-NOT-ACTIVE",
                            "User: " + username + " failed to login");
                    return "LOGIN-USER-NOT-ACTIVE";
                }
            } else {
                BlueServerApplication.database.log("LOGIN-USER-NOT-FOUND", "User: " + username + " failed to login");
                return "LOGIN-USER-NOT-FOUND";
            }
        } catch (Exception e) {
            BlueServerApplication.database.log("LOGIN-FAILED-API", "Failed to login with api: " + e.toString());
            return "LOGIN-FAILED";
        }
    }

    /**
     * Function for sending get request with json payload
     * 
     * @param endpoint
     * @param payload
     * @param apiKey
     * @return
     * @throws IOException
     */
    JSONObject sendGetRequestWithJson(String endpoint, String payload, String apiKey) throws IOException {
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            ObjectMapper objectMapper = new ObjectMapper();
            // Convert the payload to a JSON string
            String jsonPayload = objectMapper.writeValueAsString(payload);

            // Construct the URI
            URI uri = new URI(endpoint);

            // Log the full request URL
            System.out.println("Request URL: " + uri.toString());

            // Create the GET request with the JSON payload
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .header("apiKey", apiKey)
                    .method("GET", HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Log the response status and body
            System.out.println("Response Status: " + response.statusCode());
            System.out.println("Response Body: " + response.body());

            return new JSONObject(response.body());
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Function for creating a login response
     * 
     * @param token
     * @return JSONObject
     *         response build:
     *         {
     *         "token": String,
     *         "message": String,
     *         "timestamp": String,
     *         "status": String
     *         }
     */
    public JSONObject createALoginResponse(String token) {
        try {
            if (token.contains("LOGIN")) {
                JSONObject response = new JSONObject();
                response.put("token", "none");
                response.put("message", token);
                response.put("timestamp", System.currentTimeMillis());
                response.put("status", "not-logged");
                return response;
            } else {
                JSONObject response = new JSONObject();
                response.put("token", token);
                response.put("message", "none");
                response.put("timestamp", System.currentTimeMillis());
                response.put("status", "logged");
                return response;
            }
        } catch (Exception e) {
            BlueServerApplication.database.log("LOGIN-RESPONSE-FAILED",
                    "Failed to create login response: " + e.toString());
            return null;
        }
    }
}
