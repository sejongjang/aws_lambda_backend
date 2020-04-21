package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.User;

public class UserResponse extends Response {
  private User user;

  public UserResponse(boolean success, String message) {
    super(success, message);
  }

  public UserResponse(User user) {
    super(true, "success");
    this.user = user;
  }

  public UserResponse(String s) {
    super(false, s);
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
