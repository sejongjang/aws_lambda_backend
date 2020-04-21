package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Story;

public class TweetRequest {
  public Story story;

  public TweetRequest() { }

  public TweetRequest(Story story) {
    this.story = story;
  }

  public Story getStory() {
    return story;
  }

  public void setStory(Story story) {
    this.story = story;
  }
}

