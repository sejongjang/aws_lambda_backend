package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.model.*;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

import java.util.ArrayList;

import static com.amazonaws.regions.Regions.US_WEST_2;

public class AuthDAO {

  private TempFacade tempFacade;

  public AuthDAO(){
    tempFacade = TempFacade.getInstance();
  }

  public LogoutResponse signOut(String alias) {
    return tempFacade.signOutUserMileStone3(alias);
  }

  public boolean validateToken(String token) {
    return token.equals("token");
  }

  // milestone4
  private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(US_WEST_2).build();
  private static DynamoDB db = new DynamoDB(client);
  private static final String TABLE_NAME = "UserAuth";
  private static final String PRIMARY_KEY = "alias";
  private static final String AUTH = "auth";

  public LogoutResponse signOutMileStone4(String alias){
    Table table = db.getTable(TABLE_NAME);
    DeleteItemSpec spec = new DeleteItemSpec().withPrimaryKey(PRIMARY_KEY, alias);

    try {
      table.deleteItem(spec);
    }
    catch (Exception e){
      return new LogoutResponse(false, e.getMessage());
    }

    return new LogoutResponse(true, alias + " successfully signed out");
  }

  public void registerValidAuth(String auth, String alias){
    Table table = db.getTable(TABLE_NAME);

    try {
      PutItemOutcome putItemOutcome = table.putItem(new Item()
        .withPrimaryKey(PRIMARY_KEY, alias)
        .with(AUTH, auth)
      );
    } catch (Exception e) {
      System.err.println(e.getMessage());
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
        .withAttributeName("auth")
        .withAttributeType("S"));

      // Table key schema
      ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
      tableKeySchema.add(new KeySchemaElement()
        .withAttributeName("alias")
        .withKeyType(KeyType.HASH));  //Partition key
      tableKeySchema.add(new KeySchemaElement()
        .withAttributeName("auth")
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
        .withAttributeName("auth")
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
