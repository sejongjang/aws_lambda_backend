package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class FeedDAO {
  private TempFacade tempFacade;

  public FeedDAO(){
    tempFacade = TempFacade.getInstance();
  }

  public FeedResponse getFeed(FeedRequest request) {
    return tempFacade.getFeedsMileStone3(request);
  }

  // milestone 4
  private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
  private DynamoDB db = new DynamoDB(client);
  private static final String TABLE_NAME = "feed";

  private static final String FOLLOWER = "follower";
  private static final String TIME_STAMP = "time_stamp";
  private static final String MESSAGE = "message";
  private static final String STORY_OWNER_FIRST = "story_owner_first_name";
  private static final String STORY_OWNER_LAST = "story_owner_last_name";
  private static final String STORY_OWNER_ALIAS = "story_owner_alias";
  private static final String PROFILE_URL = "story_owner_url";
  private static final String STORY_IMAGE_URL = "story_image_url";

  private static final String STORY_MAP_KEY = ":" + FOLLOWER;
  private static final String STORY_EXPRESSION = FOLLOWER + " = " + STORY_MAP_KEY;

  private static final String INDEX = "time_stamp-destination-index";

  public void updateFeed(List<Item> items) {
    try {
      TableWriteItems tableWriteItems = new TableWriteItems(TABLE_NAME);
      tableWriteItems.withItemsToPut(items);
      BatchWriteItemOutcome outcome = db.batchWriteItem(tableWriteItems);

      while(outcome.getUnprocessedItems().size() > 0) {
        outcome = db.batchWriteItemUnprocessed(outcome.getUnprocessedItems());
      }
    }
    catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  public FeedResponse getFeedsMileStone4(FeedRequest request){

    boolean hasMorePages= false;
    Table table = db.getTable(TABLE_NAME);

    HashMap<String, Object> valueMap = new HashMap<>();
    valueMap.put(STORY_MAP_KEY, request.getFollower().getAlias());

    QuerySpec querySpec = new QuerySpec()
      .withKeyConditionExpression(STORY_EXPRESSION)
      .withValueMap(valueMap)
      .withScanIndexForward(true);

    ItemCollection<QueryOutcome> items = null;

    try {
      items = table.query(querySpec);
      Iterator<Item> iterator = items.iterator();
      iterator.hasNext();

    } catch (Exception e) {
      System.err.println(e.getMessage());
    }

    QueryOutcome outcome = items.getLastLowLevelResult();
    if(outcome.getQueryResult() != null) {
      hasMorePages = true;
    }

    ArrayList<Item> itemList = new ArrayList<>(outcome.getItems());
    ArrayList<User> followerList = new ArrayList<>();
    if(outcome.getQueryResult().getLastEvaluatedKey() == null){
      hasMorePages = false;
    }
    else {
      hasMorePages = true;
    }

    List<Story> stories = new ArrayList<>();
    UserDAO userDAO = new UserDAO();

    for(Item item : outcome.getItems()){
      StringBuilder sb = new StringBuilder();
      String requetAlias = "";
      if(item.getString(STORY_OWNER_ALIAS).contains("@")){
        String[] split = item.getString(STORY_OWNER_ALIAS).split("@", 3);
        sb.append(split[1]);
        requetAlias = sb.toString();
      }
      else{
        requetAlias = item.getString(STORY_OWNER_ALIAS);
      }

      UserResponse userResponse = userDAO.findUserByAliasMileStone4(requetAlias);
      if(userResponse.getUser() == null) {
        return new FeedResponse(false, "fail to find the user: " + requetAlias);
      }
      else{
        stories.add(new Story(userResponse.getUser(), item.getString(MESSAGE), item.getLong(TIME_STAMP), item.getString(STORY_IMAGE_URL)));
      }
    }

    return new FeedResponse(stories, hasMorePages);

//    boolean hasMorePages= false;
//    Table table = db.getTable(TABLE_NAME);
//
//    HashMap<String, Object> valueMap = new HashMap<>();
//    valueMap.put(STORY_MAP_KEY, request.getDestination().getAlias());
//
//    QuerySpec querySpec = new QuerySpec()
//      .withKeyConditionExpression(STORY_EXPRESSION)
//      .withValueMap(valueMap)
//      .withScanIndexForward(false);
//
//    ItemCollection<QueryOutcome> items = null;
//
//    try {
//      items = table.query(querySpec);
//      Iterator<Item> iterator = items.iterator();
//      iterator.hasNext();
//
//    } catch (Exception e) {
//      System.err.println(e.getMessage());
//    }
//
//    QueryOutcome outcome = items.getLastLowLevelResult();
//    if(outcome.getQueryResult() != null) {
//      hasMorePages = true;
//    }
//    UserDAO userDAO = new UserDAO();
//
//    List<Story> stories = new ArrayList<>();
//    for(Item item : outcome.getItems()){
//      StringBuilder sb = new StringBuilder();
//      String requetAlias = "";
//      if(item.getString(STORY_OWNER_ALIAS).contains("@")){
//        String[] split = item.getString(STORY_OWNER_ALIAS).split("@", 3);
//        sb.append(split[1]);
//        requetAlias = sb.toString();
//      }
//      else{
//        requetAlias = item.getString(STORY_OWNER_ALIAS);
//      }
//
//      UserResponse userResponse = userDAO.findUserByAliasMileStone4(requetAlias);
//      if(userResponse.getUser() == null) {
//        return new FeedResponse(false, "fail to find the user: " + requetAlias);
//      }
//      else{
//        stories.add(new Story(userResponse.getUser(), item.getString(MESSAGE), item.getLong(TIME_STAMP), item.getString(STORY_IMAGE_URL)));
//      }
//    }
//
//    return new FeedResponse(stories, hasMorePages);
  }

  public FeedResponse deleteFeedsMileStone4(User source, List<Story> stories) {
    Table table = db.getTable(TABLE_NAME);

    try {
      for(Story story : stories){
        table.deleteItem(new DeleteItemSpec()
          .withPrimaryKey(FOLLOWER, source.getAlias(), TIME_STAMP, story.getTimeStamp()));
      }
    }
    catch (Exception e) {
      System.err.println(e.getMessage());
    }

    return new FeedResponse(true, "successfully unfollowMileStone3 and delete related feeds");
  }
}
