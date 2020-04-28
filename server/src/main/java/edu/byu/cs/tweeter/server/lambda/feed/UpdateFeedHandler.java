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
import java.util.Stack;

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

        for(int i=0; i<followers.size(); ++i){ //User follower : followers
          Item item = getItem(story, followers.get(i));
          items.add(item);

          if(followers.size() -1 == i){
            feedDAO.updateFeed(items);
            items.clear();
          }
          else if(items.size() == 24){
            feedDAO.updateFeed(items);
            items.clear();
          }
        }
//        feedDAO.updateFeed(items);
      }
    }

    return null;
  }

  private Item getItem(Story story, User user){

    String firstname = "";
    firstname = story.getUser().getFirstName();

    String lastname = "";
    lastname = story.getUser().getLastName();

    String url = "";
    url = story.getUser().getImageUrl();

    return new Item().withPrimaryKey(FOLLOWER, user.getAlias(), TIME_STAMP, story.getTimeStamp())
      .withString(MESSAGE, story.getMessage())
      .withString(STORY_IMAGE_URL, story.getImageUri())
      .withString("story_owner_first_name", firstname)
      .withString("story_owner_last_name", lastname)
      .withString("story_owner_alias", story.getUser().getAlias())
      .withString("story_owner_url", url);
  }

//  public class StackArrayList<T> implements Stack<T> {
//
//    private List<T> arrList;
//
//    private int total;
//
//    public StackArrayList()
//    {
//      arrList = new ArrayList<T>();
//    }
//
//    public StackArrayList<T> push(T ele)
//    {
//      arrList.add(ele);
//      return this;
//    }
//
//    public T pop()
//    {
//      if (total == 0) throw new java.util.NoSuchElementException();
//      return arrList.remove(arrList.size()-1);
//    }
//
//    @Override
//    public String toString()
//    {
//      return java.util.Arrays.toString(new List[]{arrList});
//    }
//  }
}
