package edu.byu.cs.tweeter.server.lambda.sqs;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.StoryFollowers;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;
import edu.byu.cs.tweeter.server.dao.StoryDAO;
import edu.byu.cs.tweeter.server.json.Serializer;

import java.util.List;

public class FeedSQSHandler implements RequestHandler<SQSEvent, Void> {
  public FollowingDAO followingDAO = new FollowingDAO();
  public StoryDAO storyDAO = new StoryDAO();
  public AmazonSQS amazonSQS = AmazonSQSClientBuilder.defaultClient();

  @Override
  public Void handleRequest(SQSEvent event, Context context) {

    for(SQSEvent.SQSMessage msg : event.getRecords()){
      Story story = new Gson().fromJson(msg.getBody(), Story.class);
      User user = story.getUser();

      // get followers from the user
      FollowingResponse followersResponse = followingDAO.getFollowingsMileStone4(new FollowingRequest(user, 100, null));
      if(followersResponse != null){
        List<User> followers = followersResponse.getFollowees();

        StoryFollowers storyFollowers = new StoryFollowers();
        storyFollowers.setStory(story);
        storyFollowers.setUsers(followers);

        String body = Serializer.serialize(storyFollowers);
        String SQSURL = "https://sqs.us-west-2.amazonaws.com/640254099517/UpdateFeedHandler";
        SendMessageRequest sendMessageRequest = new SendMessageRequest().withQueueUrl(SQSURL).withMessageBody(body);
        SendMessageResult sendMessageResult = amazonSQS.sendMessage(sendMessageRequest);
      }
    }
    return null;
  }
}
