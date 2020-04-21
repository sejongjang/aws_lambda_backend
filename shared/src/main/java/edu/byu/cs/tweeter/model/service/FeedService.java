package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

import java.io.IOException;

public interface FeedService {
  FeedResponse getFeed(FeedRequest request) throws IOException;
}
