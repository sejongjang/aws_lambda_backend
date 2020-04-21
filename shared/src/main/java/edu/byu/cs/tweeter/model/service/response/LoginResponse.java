package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class LoginResponse extends Response {

    public User user;
    public String auth;

    public LoginResponse(boolean success, User user) {
        super(success);
        this.user = user;
    }

    public LoginResponse(String reason) {
        super(false, reason);
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public LoginResponse(User user, boolean success, String message) {
        super(success, message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LoginResponse(boolean success) {
        super(success);
    }

    public LoginResponse(User user){
        super(true);
        this.user = user;
    }

    public LoginResponse(boolean success, String message) {
        super(success, message);
    }


}
