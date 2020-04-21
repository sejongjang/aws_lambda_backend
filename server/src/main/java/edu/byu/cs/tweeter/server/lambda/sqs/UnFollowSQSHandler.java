package edu.byu.cs.tweeter.server.lambda.sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.google.gson.Gson;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RelationshipRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;

import java.util.List;

public class UnFollowSQSHandler implements RequestHandler<SQSEvent, Void> {
  public AmazonSQS amazonSQS = AmazonSQSClientBuilder.defaultClient();
  public StoryDAO storyDAO = new StoryDAO();
  public FeedDAO feedDAO = new FeedDAO();

  @Override
  public Void handleRequest(SQSEvent event, Context context) {
    for(SQSEvent.SQSMessage msg : event.getRecords()){

      RelationshipRequest relationship = new Gson().fromJson(msg.getBody(), RelationshipRequest.class);

      User source = relationship.getSource();
      User destination = relationship.getDestination();

      StoryResponse storyResponse = storyDAO.getStoriesMileStone4(new StoryRequest(destination));
      List<Story> stories = storyResponse.getStories();

      FeedResponse feedResponse = feedDAO.deleteFeedsMileStone4(source, stories);
    }
    return null;
  }
}
