package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.StoryService;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.StoryDAO;

import java.io.IOException;

public class StoryImpl implements StoryService {
  @Override
  public StoryResponse getStoreis(StoryRequest request) throws IOException {
    StoryDAO storyDAO = new StoryDAO();
//    return storyDAO.getStoriesMileStone3(request);
    return storyDAO.getStoriesMileStone4(request);
  }
}
