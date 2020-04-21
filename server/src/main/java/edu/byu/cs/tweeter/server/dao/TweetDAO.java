package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.service.request.TweetRequest;
import edu.byu.cs.tweeter.model.service.response.TweetResponse;

import java.util.ArrayList;

public class TweetDAO {
  private TempFacade tempFacade;

  public TweetDAO(){
    tempFacade = TempFacade.getInstance();
  }

  public TweetResponse postTweet(TweetRequest tweetRequest){
    return tempFacade.postMileStone3(tweetRequest);
  }

  private AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
  private DynamoDB db = new DynamoDB(client);
  private static final String TABLE_NAME = "Stories";
  private Table table = db.getTable(TABLE_NAME);


  private static final String STORY_OWNER = "storyOwner";
  private static final String TIME_STAMP = "timeStamp";
  private static final String MESSAGE = "message";
  private static final String DATE = "date";
  private static final String STORY_IMAGE_URL = "url";

  public TweetResponse postTweetMileStone4(TweetRequest tweetRequest){
    Story story = tweetRequest.getStory();

    try {
//      PutItemOutcome outcome = table
//        .putItem(new Item().withPrimaryKey(
//          STORY_OWNER, story.getUser().getAlias(),
//          TIME_STAMP, story.getTimeStamp()).withString(MESSAGE, story.getMessage())
//        );

      PutItemOutcome putItemOutcome = table.putItem(
        new Item().withPrimaryKey(STORY_OWNER, story.getUser().getAlias())
        .with(TIME_STAMP, story.getTimeStamp())
        .with(MESSAGE, story.getMessage())
        .with(DATE, story.getDate())
        .with(STORY_IMAGE_URL, story.getImageUri())
      );

    }
    catch (Exception e) {
      System.err.println("error");
      System.err.println(e.getMessage());
      return new TweetResponse(false, e.getMessage());
    }

    return new TweetResponse(true, "success postMileStone3 tweet " + story.getMessage());
  }

  public void dropTable() throws DataAccessException {
    try {
      Table table = db.getTable(TABLE_NAME);
      if (table != null) {
        table.delete();
        table.waitForDelete();
      }
    }
    catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public void makeTable() throws DataAccessException {
    try {
      // Attribute definitions
      ArrayList<AttributeDefinition> tableAttributeDefinitions = new ArrayList<>();

      tableAttributeDefinitions.add(new AttributeDefinition()
        .withAttributeName(STORY_OWNER)
        .withAttributeType("S"));
      tableAttributeDefinitions.add(new AttributeDefinition()
        .withAttributeName(TIME_STAMP)
        .withAttributeType("N"));

      // Table key schema
      ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
      tableKeySchema.add(new KeySchemaElement()
        .withAttributeName(STORY_OWNER)
        .withKeyType(KeyType.HASH));  //Partition key
      tableKeySchema.add(new KeySchemaElement()
        .withAttributeName(TIME_STAMP)
        .withKeyType(KeyType.RANGE));  //Sort key

      // Index
      GlobalSecondaryIndex index = new GlobalSecondaryIndex()
        .withIndexName("story-index")
        .withProvisionedThroughput(new ProvisionedThroughput()
          .withReadCapacityUnits((long) 1)
          .withWriteCapacityUnits((long) 1))
        .withProjection(new Projection().withProjectionType(ProjectionType.ALL));

      ArrayList<KeySchemaElement> indexKeySchema = new ArrayList<>();

      indexKeySchema.add(new KeySchemaElement()
        .withAttributeName(TIME_STAMP)
        .withKeyType(KeyType.HASH));  //Partition key
      indexKeySchema.add(new KeySchemaElement()
        .withAttributeName(STORY_OWNER)
        .withKeyType(KeyType.RANGE));  //Sort key

      index.setKeySchema(indexKeySchema);

      CreateTableRequest createTableRequest = new CreateTableRequest()
        .withTableName(TABLE_NAME)
        .withProvisionedThroughput(new ProvisionedThroughput()
          .withReadCapacityUnits((long) 1)
          .withWriteCapacityUnits((long) 1))
        .withAttributeDefinitions(tableAttributeDefinitions)
        .withKeySchema(tableKeySchema)
        .withGlobalSecondaryIndexes(index);

      Table table = db.createTable(createTableRequest);
      table.waitForActive();
    }
    catch (Exception e) {
      throw new DataAccessException(e);
    }
  }
}
