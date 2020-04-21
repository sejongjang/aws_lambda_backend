package edu.byu.cs.tweeter.model.service.request;

public class UserRequest {
  private String alias;

  public UserRequest() {
  }

  public UserRequest(String alias) {
    this.alias = alias;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }
}
