package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

import java.io.IOException;

public interface StoryService {
  StoryResponse getStoreis(StoryRequest request) throws IOException;
}
