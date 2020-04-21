package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class StoryDAO {
  private TempFacade tempFacade;

  public StoryDAO(){
    tempFacade = TempFacade.getInstance();
  }

  public StoryResponse getStories(StoryRequest request){
    return tempFacade.getStoriesMileStone3(request);
  }


  // milestone 4
  private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
  private DynamoDB db = new DynamoDB(client);
  private static final String TABLE_NAME = "Stories";


  private static final String STORY_OWNER = "storyOwner";
  private static final String TIME_STAMP = "timeStamp";

  private static final String STORY_MAP_KEY = ":" + STORY_OWNER;
  private static final String TIME_STAMP_MAP_KEY = ":" + TIME_STAMP;

  private static final String STORY_EXPRESSION = STORY_OWNER + " = " + STORY_MAP_KEY;
  private static final String TIME_STAMP_EXPRESSION = TIME_STAMP + " = " + TIME_STAMP_MAP_KEY;

  private static final String MESSAGE = "message";
  private static final String DATE = "date";
  private static final String STORY_IMAGE_URL = "url";

  private static final String STORY_INDEX = "story-index";

  public StoryResponse getStoriesMileStone4(StoryRequest request){
    boolean hasMorePages= false;
    Table table = db.getTable(TABLE_NAME);

    HashMap<String, Object> valueMap = new HashMap<>();
    valueMap.put(STORY_MAP_KEY, request.getUser().getAlias());

    QuerySpec querySpec = new QuerySpec()
      .withKeyConditionExpression(STORY_EXPRESSION)
      .withValueMap(valueMap)
      .withScanIndexForward(false);

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
    UserDAO userDAO = new UserDAO();

    List<Story> stories = new ArrayList<>();
    for(Item item : outcome.getItems()){
      StringBuilder sb = new StringBuilder();
      if(item.getString(STORY_OWNER).contains("@")){
        String[] split = item.getString(STORY_OWNER).split("@", 3);
        sb.append(split[1]);
      }
      UserResponse userResponse = userDAO.findUserByAliasMileStone4(sb.toString());
      if(userResponse.getUser() == null) {
        stories.add(new Story(sb.toString(), item.getString(MESSAGE), item.getLong(TIME_STAMP), item.getString(STORY_IMAGE_URL)));
      }
      else{
        stories.add(new Story(userResponse.getUser(), item.getString(MESSAGE), item.getLong(TIME_STAMP), item.getString(STORY_IMAGE_URL)));
      }
    }

    return new StoryResponse(stories, hasMorePages);
  }
}
