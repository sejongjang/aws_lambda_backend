package edu.byu.cs.tweeter.server.lambda.story;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.StoryService;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.service.StoryImpl;

import java.io.IOException;

public class StoryHandler implements RequestHandler<StoryRequest, StoryResponse> {
  @Override
  public StoryResponse handleRequest(StoryRequest input, Context context) {
    StoryService storyService = new StoryImpl();
    try {
      return storyService.getStoreis(input);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new StoryResponse("fail request");
  }
}
