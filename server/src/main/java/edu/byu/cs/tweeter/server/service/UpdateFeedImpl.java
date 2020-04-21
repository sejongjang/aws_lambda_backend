package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import edu.byu.cs.tweeter.model.UpdateFeedService;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.json.Serializer;

public class UpdateFeedImpl implements UpdateFeedService {
  // TODO: need to be changed as FeedSQSHandler
  private final String SQSURL = "https://sqs.us-west-2.amazonaws.com/640254099517/getFollowingSQS";

  @Override
  public StoryResponse updateFeed(StoryRequest request) {
    FeedDAO feedDAO = new FeedDAO();

    String queueItem = Serializer.serialize(request.getUser());
    SendMessageRequest sendMessageRequest = new SendMessageRequest()
      .withQueueUrl(SQSURL)
      .withMessageBody(queueItem);

    AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
    SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);

    System.out.println(sendMessageResult);
    FeedResponse feedResponse = feedDAO.getFeed(new FeedRequest(request.getUser(), 10, null));
    return new StoryResponse(feedResponse.getFeeds(), feedResponse.getHasMorePages());
  }
}
