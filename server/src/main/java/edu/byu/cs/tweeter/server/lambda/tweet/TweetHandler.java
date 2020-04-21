package edu.byu.cs.tweeter.server.lambda.tweet;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.TweetService;
import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;
import edu.byu.cs.tweeter.server.service.TweetImpl;

public class TweetHandler implements RequestHandler<TweetRequest, TweetResponse> {

  @Override
  public TweetResponse handleRequest(TweetRequest tweetRequest, Context context) {
    TweetService tweetService = new TweetImpl();
    return tweetService.postTweet(tweetRequest);
  }
}
