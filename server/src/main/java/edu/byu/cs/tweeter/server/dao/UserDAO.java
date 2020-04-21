package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.model.*;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.amazonaws.regions.Regions.US_WEST_2;

public class UserDAO {


  // milestone 3
  private TempFacade tempFacade;

  public UserDAO(){
    tempFacade = TempFacade.getInstance();
  }

  public LoginResponse loginUser(LoginRequest request){
    return tempFacade.loginUserMileStone3(request);
  }

  public LoginResponse registerUser(SignUpRequest request){
    return tempFacade.registerUserMileStone3(request);
  }

  public UserResponse findUserByAlias(String alias){
    return tempFacade.findUserByAliasMileStone3(alias);
  }


  // milestone4
  private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(US_WEST_2).build();
  private static DynamoDB db = new DynamoDB(client);
  private static final String TABLE_NAME = "Users";
  private static final String USER_ALIAS = "alias";
  private static final String FIRST_NAME = "firstName";
  private static final String LAST_NAME = "lastName";
  private static final String PROFILE_IMAGE = "profileImage";
  private static final String PASSWORD = "password";

  private String encrypt(String password) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
    return new String(hash);
  }

  public LoginResponse loginUserMileStone4(LoginRequest request){

    String requestedAlias = request.getUsername();
    ItemCollection<QueryOutcome> items = getUserDetailMileStone4(requestedAlias);

    if(items == null) return new LoginResponse(false, requestedAlias + " not found");

    QueryOutcome outcome = items.getLastLowLevelResult();
    if (outcome.getItems().size() == 0){
      System.out.println(requestedAlias + " does not exist");
      return new LoginResponse(requestedAlias + " does not exist");
    }
    else{
      Item item = outcome.getItems().get(0);
      String encryptedPassword = null;
      try {
        encryptedPassword = encrypt(request.getPassword());
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        return new LoginResponse(false, e.getMessage());
      }
      if(!item.getString(PASSWORD).equals(encryptedPassword)){
        System.out.println("wrong password");
        return new LoginResponse(false, "wrong password");
      }
      else {
        User u = new User(item.getString(FIRST_NAME), item.getString(LAST_NAME), item.getString(USER_ALIAS), item.getString(PROFILE_IMAGE));
        System.out.println(u.getAlias());
        return new LoginResponse(u);
      }
    }
  }

  public LoginResponse registerUserMileStone4(SignUpRequest request) {

    Table table = db.getTable(TABLE_NAME);

    try {
      request.setPassword(encrypt(request.getPassword()));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    try {
      System.out.println("Registering user " + request.alias);

      PutItemOutcome putItemOutcome = table.putItem(new Item().withPrimaryKey(USER_ALIAS, request.alias)
          .with(PASSWORD, request.password)
          .with(PROFILE_IMAGE, request.imageURL)
          .with(FIRST_NAME, request.firstname)
          .with(LAST_NAME,  request.lastname)
        );

      System.out.println(request.alias + " successfully registered");
      return new LoginResponse(true, new User(request.getFirstname(), request.getLastname(), request.getAlias(), request.getImageURL()));

    } catch (Exception e) {
      System.err.println(e.getMessage());
      return new LoginResponse(false, e.getMessage());
    }
  }

  private ItemCollection<QueryOutcome> getUserDetailMileStone4(String requestedAlias){
    Table table = db.getTable(TABLE_NAME);

    HashMap<String, Object> valueMap = new HashMap<>();
    valueMap.put(":alias", requestedAlias);

    QuerySpec querySpec = new QuerySpec()
      .withKeyConditionExpression("alias = :alias")
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
    return items;
  }

  public UserResponse findUserByAliasMileStone4(String requestedAlias){

    ItemCollection<QueryOutcome> items = getUserDetailMileStone4(requestedAlias);
    if(items == null) return new UserResponse(requestedAlias + " not found");

    QueryOutcome outcome = items.getLastLowLevelResult();
    if(outcome == null) return new UserResponse(requestedAlias + " not found");

    if (outcome.getItems().size() == 0){
      System.out.println(requestedAlias + " does not exist");
      return new UserResponse(requestedAlias + " does not exist");
    }
    else{
      Item item = outcome.getItems().get(0);
      User u = new User(item.getString(FIRST_NAME), item.getString(LAST_NAME), item.getString(USER_ALIAS), item.getString(PROFILE_IMAGE));
      System.out.println(u.getAlias());
      return new UserResponse(u);
    }
  }

  public void makeTable() throws DataAccessException {
    try {
      // Attribute definitions
      ArrayList<AttributeDefinition> tableAttributeDefinitions = new ArrayList<>();

      tableAttributeDefinitions.add(new AttributeDefinition()
        .withAttributeName("alias")
        .withAttributeType("S"));
      tableAttributeDefinitions.add(new AttributeDefinition()
        .withAttributeName("lastName")
        .withAttributeType("S"));

      // Table key schema
      ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
      tableKeySchema.add(new KeySchemaElement()
        .withAttributeName("alias")
        .withKeyType(KeyType.HASH));  //Partition key
      tableKeySchema.add(new KeySchemaElement()
        .withAttributeName("lastName")
        .withKeyType(KeyType.RANGE));  //Sort key

      // Index
      GlobalSecondaryIndex index = new GlobalSecondaryIndex()
        .withIndexName("alias-index")
        .withProvisionedThroughput(new ProvisionedThroughput()
          .withReadCapacityUnits((long) 1)
          .withWriteCapacityUnits((long) 1))
        .withProjection(new Projection().withProjectionType(ProjectionType.ALL));

      ArrayList<KeySchemaElement> indexKeySchema = new ArrayList<>();

      indexKeySchema.add(new KeySchemaElement()
        .withAttributeName("lastName")
        .withKeyType(KeyType.HASH));  //Partition key
      indexKeySchema.add(new KeySchemaElement()
        .withAttributeName("alias")
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

}
