package edu.byu.cs.tweeter.model;

import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public interface UpdateFeedService {
  StoryResponse updateFeed(StoryRequest storyRequest);
}
