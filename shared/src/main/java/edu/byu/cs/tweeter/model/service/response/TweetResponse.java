package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.Story;

import java.util.List;

public class TweetResponse extends Response {

  private List<Story> tweets;

  public TweetResponse(boolean success) {
    super(success);
  }

  public TweetResponse(boolean success, String message) {
    super(success, message);
  }

  public TweetResponse(boolean success, String message, List<Story> tweets) {
    super(success, message);
    this.tweets = tweets;
  }

  public List<Story> getTweets() {
    return tweets;
  }

  public void setTweets(List<Story> tweets) {
    this.tweets = tweets;
  }
}
