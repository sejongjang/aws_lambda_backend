package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;

public class FeedRequest {
  public User follower;
  public int limit;
  public Story lastFeed;

  public FeedRequest() { }

  public FeedRequest(User follower, int limit, Story lastFeed) {
    this.follower = follower;
    this.limit = limit;
    this.lastFeed = lastFeed;
  }

  public User getFollower() {
    return follower;
  }

  public int getLimit() {
    return limit;
  }

  public Story getLastFeed() {
    return lastFeed;
  }
}
