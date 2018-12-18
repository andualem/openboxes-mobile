package org.health.supplychain.security.entities;

import java.util.Set;

/**
 * Created by Aworkneh on 6/7/2018.
 */

public class UserSession {

    private String username;
    private String password;
    private String sessionId;
    private String locationId;

    private String userId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getUserId() {
        //TODO: fix this issue; how can I get userId/personId from a username
        userId = "3";
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
