package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;

import java.util.List;

public class FeedResponse extends PagedResponse {

  private List<Story> feeds;
  private List<User> following;

  FeedResponse(boolean success, boolean hasMorePages) {
    super(success, hasMorePages);
  }

  public FeedResponse(List<Story> feeds, boolean hasMorePages){
    super(true, hasMorePages);
    this.feeds = feeds;
  }

  public FeedResponse() {
    super(false, "fail to getting feeds", false);
  }

  public FeedResponse(boolean hasMorePages, List<Story> feeds, List<User> following) {
    super(true, hasMorePages);
    this.feeds = feeds;
    this.following = following;
  }

  public FeedResponse(boolean hasMorePages, List<Story> feeds) {
    super(true, "successfully getting feeds" ,hasMorePages);
    this.feeds = feeds;
  }

  public FeedResponse(boolean b, String s) {
    super(b, s, false);
  }

  public List<Story> getFeeds() {
    return feeds;
  }

  public void setFeeds(List<Story> feeds) {
    this.feeds = feeds;
  }
}