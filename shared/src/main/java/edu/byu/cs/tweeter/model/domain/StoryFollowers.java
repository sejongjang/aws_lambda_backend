package edu.byu.cs.tweeter.model.domain;

import java.util.List;

public class StoryFollowers {
  public List<User> users;
  public Story story;

  public StoryFollowers() {
  }

  public StoryFollowers(List<User> users, Story story) {
    this.users = users;
    this.story = story;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(List<User> users) {
    this.users = users;
  }

  public Story getStory() {
    return story;
  }

  public void setStory(Story story) {
    this.story = story;
  }
}
