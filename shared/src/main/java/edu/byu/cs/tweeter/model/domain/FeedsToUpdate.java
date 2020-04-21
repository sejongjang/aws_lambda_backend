package edu.byu.cs.tweeter.model.domain;

import java.util.List;

public class FeedsToUpdate {
  public User destination;
  public User source;
  public List<Story> stories;

  public FeedsToUpdate() { }

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

  public List<Story> getStories() {
    return stories;
  }

  public void setStories(List<Story> stories) {
    this.stories = stories;
  }
}
