package com.citizen.server.mgr;

import java.util.List;

/***
 *
 */
public class VUserProfile {
    private String username;
    private String name;
    private List<String> roles;
    private String jwtToken;

    // getter and setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public String toString() {
        return "VUserProfile [roles=" + roles + ", username=" + username + ", name=" + name + ", jwtToken=" + jwtToken
                + "]";
    }
}
