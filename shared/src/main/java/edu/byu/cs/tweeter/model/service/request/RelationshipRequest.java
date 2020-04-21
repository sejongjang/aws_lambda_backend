package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class RelationshipRequest {
  public User source;
  public User destination;
  public boolean isFollowed;

  public RelationshipRequest() {
  }

  public RelationshipRequest(User source, User destination, boolean isFollowed) {
    this.source = source;
    this.destination = destination;
    this.isFollowed = isFollowed;
  }

  public RelationshipRequest(User source, User destination) {
    this.source = source;
    this.destination = destination;
  }

  public User getSource() {
    return source;
  }

  public void setSource(User source) {
    this.source = source;
  }

  public User getDestination() {
    return destination;
  }

  public void setDestination(User destination) {
    this.destination = destination;
  }

  public boolean isFollowed() {
    return isFollowed;
  }

  public void setFollowed(boolean followed) {
    isFollowed = followed;
  }
}
