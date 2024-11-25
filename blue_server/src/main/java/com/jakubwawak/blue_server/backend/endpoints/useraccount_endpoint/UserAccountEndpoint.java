package com.jakubwawak.blue_server.backend.endpoints.useraccount_endpoint;

import com.jakubwawak.blue_server.backend.database.DatabaseUser;
import com.jakubwawak.blue_server.backend.maintanance.Response;
import com.jakubwawak.blue_server.entity.BlueUser;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserAccountEndpoint {

    private final DatabaseUser databaseUser;

    public UserAccountEndpoint(DatabaseUser databaseUser) {
        this.databaseUser = databaseUser;
    }

    @PostMapping("/create")
    public Response createUser(@RequestBody UserAccountPayload payload) {
        // Validate payload
        if (payload.getEmail() == null || payload.getPassword() == null || payload.getTelephone() == null) {
            return new Response("Error", "Email, password, and telephone are required.");
        }

        // Use DatabaseUser to create user account
        BlueUser user = new BlueUser();
        user.userEmail = payload.getEmail();
        user.setPassword(payload.getPassword());
        user.setTelephone(payload.getTelephone());

        user = databaseUser.createUser(user);

        if (user.get_id() != null) {
            return new Response("Success", "User account created successfully.");
        } else {
            return new Response("Error", "Failed to create user account.");
        }
    }

    @PostMapping("/disable")
    public Response disableUser(@RequestBody UserAccountPayload payload) {
        // Validate payload
        if (payload.getEmail() == null) {
            return new Response("Error", "Email is required.");
        }

        // Use DatabaseUser to disable user account
        boolean success = databaseUser.disableUser(payload.getEmail());

        if (success) {
            return new Response("Success", "User account disabled successfully.");
        } else {
            return new Response("Error", "Failed to disable user account.");
        }
    }
}