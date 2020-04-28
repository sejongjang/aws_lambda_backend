package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.RelationshipRequest;
import edu.byu.cs.tweeter.model.service.response.RelationshipResponse;

import static com.amazonaws.regions.Regions.US_WEST_2;

public class RelationshipDAO {
  private MileStone3Facade mileStone3Facade;

  public RelationshipDAO(){
    mileStone3Facade = MileStone3Facade.getInstance();
  }

  public RelationshipResponse getIsRelated(RelationshipRequest request){
    return mileStone3Facade.isRelatedMileStone3(request);
  }

  public RelationshipResponse follow(RelationshipRequest request) {
    return mileStone3Facade.followMileStone3(request);
  }

  public RelationshipResponse unfollow(RelationshipRequest request) {
    return mileStone3Facade.unfollowMileStone3(request);
  }

  // milestone4

  private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(US_WEST_2).build();
  private static DynamoDB db = new DynamoDB(client);
  private static final String TABLE_NAME = "follows";
  private static final String FOLLOWER_HANDLE = "follower_handle";
  private static final String FOLLOWEE_HANDLE = "followee_handle";
//  private static final String FOLLOWER_NAME = "follower_name";
//  private static final String FOLLOWEE_NAME = "followee_name";

  private static final String FOLLOWER_FIRST_NAME = "follower_first_name";
  private static final String FOLLOWER_LAST_NAME = "follower_last_name";

  private static final String FOLLOWEE_FIRST_NAME = "followee_first_name";
  private static final String FOLLOWEE_LAST_NAME = "followee_last_name";

  private static final String FOLLOWER_PROFILE_URL = "follower_profile_url";
  private static final String FOLLOWEE_PROFILE_URL = "followee_profile_url";

  public void dropTable(){
    DAOUtil daoUtil = new DAOUtil();
    try {
      daoUtil.dropTable(TABLE_NAME);
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  public void makeTable(){
    DAOUtil daoUtil = new DAOUtil();
    try {
      daoUtil.makeTable(TABLE_NAME, FOLLOWER_HANDLE, FOLLOWEE_HANDLE);
    } catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  public RelationshipResponse followMileStone4(RelationshipRequest request){
    Table table = db.getTable(TABLE_NAME);
    User source = null;
    User destination = null;
    if(request.getSource() != null) source = request.getSource();
    if(request.getDestination() != null) destination = request.getDestination();

//    User supserStar = new User("Iam", "superstar", "superstar", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//    for(int i=0; i<10000; ++i){
//      User sourceUser = new User("person"+i, "test"+i, "follower"+i, "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//      try {
//        PutItemOutcome putItemOutcome = table.putItem(new Item()
//          .withPrimaryKey(FOLLOWER_HANDLE, sourceUser.getAlias())
//          .with(FOLLOWEE_HANDLE, supserStar.getAlias())
//          .with(FOLLOWER_FIRST_NAME, sourceUser.getFirstName())
//          .with(FOLLOWER_LAST_NAME, sourceUser.getLastName())
//          .with(FOLLOWEE_FIRST_NAME, supserStar.getFirstName())
//          .with(FOLLOWEE_LAST_NAME, supserStar.getLastName())
//          .with(FOLLOWER_PROFILE_URL, sourceUser.getImageUrl())
//          .with(FOLLOWEE_PROFILE_URL, supserStar.getImageUrl())
//        );
//        System.out.println(sourceUser.getAlias() + " follows " + supserStar.getAlias());
//      }
//      catch (Exception e){
//        return new RelationshipResponse(false, e.getMessage());
//      }
//    }
    try {
      PutItemOutcome putItemOutcome = table.putItem(new Item()
        .withPrimaryKey(FOLLOWER_HANDLE, source.getAlias())
        .with(FOLLOWEE_HANDLE, destination.getAlias())
        .with(FOLLOWER_FIRST_NAME, source.getFirstName())
        .with(FOLLOWER_LAST_NAME, source.getLastName())
        .with(FOLLOWEE_FIRST_NAME, destination.getFirstName())
        .with(FOLLOWEE_LAST_NAME, destination.getLastName())
        .with(FOLLOWER_PROFILE_URL, source.getImageUrl())
        .with(FOLLOWEE_PROFILE_URL, destination.getImageUrl())
      );
    }
    catch (Exception e){
      return new RelationshipResponse(false, e.getMessage());
    }

    return new RelationshipResponse(true, source.getAlias() + " follows " + destination.getAlias());
  }

  public RelationshipResponse unfollowMileStone4(RelationshipRequest request){
    Table table = db.getTable(TABLE_NAME);
    User source = null;
    User destination = null;
    if(request.getSource() != null) source = request.getSource();
    if(request.getDestination() != null) destination = request.getDestination();

    try {
      if(source != null && source.getAlias() != null && destination != null && destination.getAlias() != null){
        table.deleteItem(new DeleteItemSpec()
          .withPrimaryKey("follower_handle", source.getAlias(), "followee_handle", destination.getAlias()));
      }

    }
    catch (Exception e) {
      return new RelationshipResponse(false, e.getMessage());
    }

    return new RelationshipResponse(source.getAlias() + " now unfollows " + destination.getAlias());
  }

  public RelationshipResponse isRelatedMileStone4(RelationshipRequest request){
    Table table = db.getTable(TABLE_NAME);
    User source = null;
    User destination = null;
    if(request.getSource() != null) source = request.getSource();
    if(request.getDestination() != null) destination = request.getDestination();

    GetItemSpec spec = new GetItemSpec().withPrimaryKey("follower_handle", source.getAlias(),
      "followee_handle", destination.getAlias());

    Item outcome = null;
    try {
      outcome = table.getItem(spec);
    }
    catch (Exception e) {
      return new RelationshipResponse(false, e.getMessage());
    }

    if (outcome == null){
      return new RelationshipResponse(false, "no relationship");
    }

    return new RelationshipResponse(true, source.getAlias() + " currently follows " + destination.getAlias());
  }

}
