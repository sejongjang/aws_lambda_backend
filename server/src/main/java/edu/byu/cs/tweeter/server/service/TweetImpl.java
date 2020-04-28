package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;
import edu.byu.cs.tweeter.model.service.TweetService;
import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;
import edu.byu.cs.tweeter.server.dao.TweetDAO;

public class TweetImpl implements TweetService {

  @Override
  public TweetResponse postTweet(TweetRequest tweetRequest) {
    TweetDAO tweetDAO = new TweetDAO();

    try{
      String item = new Gson().toJson(tweetRequest.getStory());
      String SQSURL = "https://sqs.us-west-2.amazonaws.com/640254099517/FeedSQSHandler";
      SendMessageRequest sendMessageRequest = new SendMessageRequest().withQueueUrl(SQSURL).withMessageBody(item);
      AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
      SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);
    }
    catch (Exception e){
      return new TweetResponse(false, e.getMessage());
    }

    return tweetDAO.postTweetMileStone4(tweetRequest);
  }
}
