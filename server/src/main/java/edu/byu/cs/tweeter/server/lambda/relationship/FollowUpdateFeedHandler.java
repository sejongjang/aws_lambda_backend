package edu.byu.cs.tweeter.server.lambda.relationship;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import edu.byu.cs.tweeter.model.domain.FeedsToUpdate;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.json.Serializer;

import java.util.ArrayList;
import java.util.List;

public class FollowUpdateFeedHandler implements RequestHandler<SQSEvent, Void> {
  public FeedDAO feedDAO = new FeedDAO();
  private static final String STORY_IMAGE_URL = "story_image_url";
  private static final String FOLLOWER = "follower";
  private static final String STORY_OWNER_ALIAS = "story_owner_alias";
  private static final String STORY_OWNER_FIRST = "story_owner_first_name";
  private static final String STORY_OWNER_LAST = "story_owner_last_name";
  private static final String PROFILE_URL = "story_owner_url";
  private static final String TIME_STAMP = "time_stamp";
  private static final String MESSAGE = "message";

  public AmazonSQS amazonSQS = AmazonSQSClientBuilder.defaultClient();

  @Override
  public Void handleRequest(SQSEvent event, Context context) {

    for(SQSEvent.SQSMessage msg : event.getRecords()){
      FeedsToUpdate feedsToUpdate = Serializer.deserialize(msg.getBody(), FeedsToUpdate.class);

      if(feedsToUpdate != null){
        List<Story> stories = feedsToUpdate.getStories();
        User source = feedsToUpdate.getSource();
        User destination = feedsToUpdate.getDestination();

        List<Item> items = new ArrayList<>();

        for(Story story : stories){
          Item item = getItem(story, source);
          items.add(item);
        }

        feedDAO.updateFeed(items);
      }
    }

    return null;
  }

  private Item getItem(Story story, User user){

    return new Item().withPrimaryKey(FOLLOWER, user.getAlias(), TIME_STAMP, story.getTimeStamp())
      .withString(MESSAGE, story.getMessage())
      .withString(STORY_IMAGE_URL, story.getImageUri())
      .withString(STORY_OWNER_FIRST, story.getUser().getFirstName())
      .withString(STORY_OWNER_LAST, story.getUser().getLastName())
      .withString(STORY_OWNER_ALIAS, story.getUser().getAlias())
      .withString(PROFILE_URL, story.getUser().getImageUrl());
  }
}
