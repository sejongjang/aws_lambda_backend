package edu.byu.cs.tweeter.server.lambda.feed;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.FeedService;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.server.service.FeedImpl;

import java.io.IOException;

public class GetFeedHandler implements RequestHandler<FeedRequest, FeedResponse> {
  @Override
  public FeedResponse handleRequest(FeedRequest input, Context context) {
    FeedService feedService = new FeedImpl();
    try {

      return feedService.getFeed(input);

    } catch (IOException e) {
      e.printStackTrace();
    }
    return new FeedResponse();
  }
}
