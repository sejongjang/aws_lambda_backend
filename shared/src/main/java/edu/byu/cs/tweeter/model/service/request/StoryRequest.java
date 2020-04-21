package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;

public class StoryRequest {
  public User user;
  public int limit;
  public Story lastStory;

  public StoryRequest() {
  }

  public StoryRequest(User user) {
    this.user = user;
  }

  public StoryRequest(User user, int limit, Story lastStory) {
    this.user = user;
    this.limit = limit;
    this.lastStory = lastStory;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public Story getLastestStory() {
    return lastStory;
  }

  public void setLastStory(Story lastStory) {
    this.lastStory = lastStory;
  }
}
