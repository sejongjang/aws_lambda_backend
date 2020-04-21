package edu.byu.cs.tweeter.model.service.response;

public class RelationshipResponse extends Response{
  public RelationshipResponse(boolean success) {
    super(success);
  }

  public RelationshipResponse(boolean success, String message) {
    super(success, message);
  }

  public RelationshipResponse(String s) {
    super(false, s);
  }
}
