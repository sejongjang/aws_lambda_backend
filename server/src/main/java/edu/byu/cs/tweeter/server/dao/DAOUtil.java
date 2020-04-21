package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;

import java.util.ArrayList;

import static com.amazonaws.regions.Regions.US_WEST_2;

public class DAOUtil {

  private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(US_WEST_2).build();
  private static DynamoDB db = new DynamoDB(client);

  public void dropTable(String tableName) throws DataAccessException {
    try {
      Table table = db.getTable(tableName);
      if (table != null) {
        table.delete();
        table.waitForDelete();
      }
    }
    catch (Exception e) {
      throw new DataAccessException(e);
    }
  }

  public void makeTable(String tableName, String attr1, String attr2) throws DataAccessException {

    String attrIndex = attr1 + "-index";

    try {
      // Attribute definitions
      ArrayList<AttributeDefinition> tableAttributeDefinitions = new ArrayList<>();

      tableAttributeDefinitions.add(new AttributeDefinition()
        .withAttributeName(attr1)
        .withAttributeType("S"));
      tableAttributeDefinitions.add(new AttributeDefinition()
        .withAttributeName(attr2)
        .withAttributeType("S"));

      // Table key schema
      ArrayList<KeySchemaElement> tableKeySchema = new ArrayList<>();
      tableKeySchema.add(new KeySchemaElement()
        .withAttributeName(attr1)
        .withKeyType(KeyType.HASH));  //Partition key
      tableKeySchema.add(new KeySchemaElement()
        .withAttributeName(attr2)
        .withKeyType(KeyType.RANGE));  //Sort key

      // Index
      GlobalSecondaryIndex index = new GlobalSecondaryIndex()
        .withIndexName(attrIndex)
        .withProvisionedThroughput(new ProvisionedThroughput()
          .withReadCapacityUnits((long) 1)
          .withWriteCapacityUnits((long) 1))
        .withProjection(new Projection().withProjectionType(ProjectionType.ALL));

      ArrayList<KeySchemaElement> indexKeySchema = new ArrayList<>();

      indexKeySchema.add(new KeySchemaElement()
        .withAttributeName(attr2)
        .withKeyType(KeyType.HASH));  //Partition key
      indexKeySchema.add(new KeySchemaElement()
        .withAttributeName(attr1)
        .withKeyType(KeyType.RANGE));  //Sort key

      index.setKeySchema(indexKeySchema);

      CreateTableRequest createTableRequest = new CreateTableRequest()
        .withTableName(tableName)
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
