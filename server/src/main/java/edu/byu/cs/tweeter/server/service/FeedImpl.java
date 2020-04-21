package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.FeedService;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.server.dao.FeedDAO;

import java.io.IOException;

public class FeedImpl implements FeedService {

  @Override
  public FeedResponse getFeed(FeedRequest request) throws IOException {
    FeedDAO feedDAO = new FeedDAO();
//    return feedDAO.getFeedsMileStone3(request);
    return feedDAO.getFeedsMileStone4(request);
  }
}
