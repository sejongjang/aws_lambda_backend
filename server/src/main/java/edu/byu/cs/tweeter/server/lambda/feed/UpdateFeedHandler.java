package edu.byu.cs.tweeter.server.lambda.feed;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.StoryFollowers;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.server.dao.FeedDAO;
import edu.byu.cs.tweeter.server.json.Serializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpdateFeedHandler implements RequestHandler<SQSEvent, Void> {
  public FeedDAO feedDAO = new FeedDAO();
  private static final String STORY_IMAGE_URL = "story_image_url";
  private static final String FOLLOWER = "follower";
  private static final String TIME_STAMP = "time_stamp";
  private static final String MESSAGE = "message";

  @Override
  public Void handleRequest(SQSEvent sqs, Context context) {

    List<SQSEvent.SQSMessage> records = sqs.getRecords();
    for(SQSEvent.SQSMessage record : records){
      StoryFollowers storyFollowers = Serializer.deserialize(record.getBody(), StoryFollowers.class);

      if(storyFollowers != null){
        Story story = storyFollowers.getStory();
        story.setTimeStamp(new Date().getTime());
        List<User> followers = storyFollowers.getUsers();
        List<Item> items = new ArrayList<>();

        for(User follower : followers){
          Item item = getItem(story, follower);
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
      .withString("story_owner_first_name", story.getUser().getFirstName())
      .withString("story_owner_last_name", story.getUser().getLastName())
      .withString("story_owner_alias", story.getUser().getAlias())
      .withString("story_owner_url", story.getUser().getImageUrl());
  }
}
