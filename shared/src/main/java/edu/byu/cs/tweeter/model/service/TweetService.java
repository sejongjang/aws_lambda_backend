package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;

public interface TweetService {
  TweetResponse postTweet(TweetRequest tweetRequest);
}
