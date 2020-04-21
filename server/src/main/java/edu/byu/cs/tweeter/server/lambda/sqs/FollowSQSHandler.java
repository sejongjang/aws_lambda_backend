package edu.byu.cs.tweeter.server.lambda.sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;
import edu.byu.cs.tweeter.model.domain.FeedsToUpdate;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RelationshipRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.json.Serializer;

import java.util.List;

public class FollowSQSHandler implements RequestHandler<SQSEvent, Void> {

  public AmazonSQS amazonSQS = AmazonSQSClientBuilder.defaultClient();
  public StoryDAO storyDAO = new StoryDAO();

  @Override
  public Void handleRequest(SQSEvent event, Context context) {

    for(SQSEvent.SQSMessage msg : event.getRecords()){
      RelationshipRequest relationship = new Gson().fromJson(msg.getBody(), RelationshipRequest.class);

      User source = relationship.getSource();
      User destination = relationship.getDestination();

      StoryResponse storyResponse = storyDAO.getStoriesMileStone4(new StoryRequest(destination));

      if(storyResponse != null && storyResponse.getStories().size() != 0){
        List<Story> storyList = storyResponse.getStories();
        FeedsToUpdate feedsToUpdate = new FeedsToUpdate();

        feedsToUpdate.setStories(storyList);
        feedsToUpdate.setSource(source);
        feedsToUpdate.setDestination(destination);

        String body = Serializer.serialize(feedsToUpdate);

        String SQSURL = "https://sqs.us-west-2.amazonaws.com/640254099517/FollowUpdateFeedHandler";
        SendMessageRequest sendMessageRequest = new SendMessageRequest().withQueueUrl(SQSURL).withMessageBody(body);
        SendMessageResult sendMessageResult = amazonSQS.sendMessage(sendMessageRequest);
      }
    }
    return null;
  }
}
