package edu.byu.cs.tweeter.server.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static com.amazonaws.regions.Regions.US_WEST_2;

public class FollowingDAO {

    private MileStone3Facade mileStone3Facade;

    public FollowingDAO(){
        mileStone3Facade = MileStone3Facade.getInstance();
    }

    public FollowingResponse getFollowees(FollowingRequest request){
        return mileStone3Facade.getFollowingMileStone3(request);
    }

    public FollowingResponse getFollowers(FollowingRequest request){
        return mileStone3Facade.getFollowersMileStone3(request);
    }

    FollowGenerator getFollowGenerator() {
        return FollowGenerator.getInstance();
    }

    // milestone 4
    private static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(US_WEST_2).build();
    private static DynamoDB db = new DynamoDB(client);
    private static final String TABLE_NAME = "follows";
    private static final String FOLLOWER_HANDLE = "follower_handle";
    private static final String FOLLOWEE_HANDLE = "followee_handle";

    private static final String FOLLOWERING_MAP_KEY = ":" + FOLLOWER_HANDLE;
    private static final String FOLLOWING_EXPRESSION = FOLLOWER_HANDLE + " = " + FOLLOWERING_MAP_KEY;

    private static final String FOLLOWER_MAP_KEY = ":" + FOLLOWEE_HANDLE;
    private static final String FOLLOWER_EXPRESSION = FOLLOWEE_HANDLE + " = " + FOLLOWER_MAP_KEY;

    private static final String FOLLOWER_FIRST_NAME = "follower_first_name";
    private static final String FOLLOWER_LAST_NAME = "follower_last_name";

    private static final String FOLLOWEE_FIRST_NAME = "followee_first_name";
    private static final String FOLLOWEE_LAST_NAME = "followee_last_name";

    private static final String FOLLOWER_PROFILE_URL = "follower_profile_url";
    private static final String FOLLOWEE_PROFILE_URL = "followee_profile_url";

    public FollowingResponse getFollowersMileStone4(FollowingRequest request){
        boolean hasMorePages= false;
        Table table = db.getTable(TABLE_NAME);

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(FOLLOWER_MAP_KEY, request.getFollower().getAlias());

        QuerySpec querySpec = new QuerySpec()
          .withKeyConditionExpression(FOLLOWER_EXPRESSION)
          .withValueMap(valueMap)
          .withScanIndexForward(true);

        ItemCollection<QueryOutcome> items = null;

        if(request.limit > 0) querySpec.withMaxPageSize(request.limit);
        if(request.getLastFollowee() != null) querySpec.withExclusiveStartKey(FOLLOWEE_HANDLE, request.getFollower().getAlias(), FOLLOWER_HANDLE, request.getLastFollowee().getAlias());

        try {
            //follows_index
            items = table.getIndex("follows_index").query(querySpec);
            Iterator<Item> iterator = items.iterator();
            iterator.hasNext();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new FollowingResponse(e.getMessage());
        }

        QueryOutcome outcome = items.getLastLowLevelResult();
        ArrayList<Item> itemList = new ArrayList<>(outcome.getItems());
        ArrayList<User> followerList = new ArrayList<>();
        hasMorePages = outcome.getQueryResult().getLastEvaluatedKey() != null;

        for (Item item: itemList) {
            User user = new User(item.getString(FOLLOWER_FIRST_NAME), item.getString(FOLLOWER_LAST_NAME), item.getString(FOLLOWER_HANDLE), item.getString(FOLLOWER_PROFILE_URL));
            followerList.add(user);
        }

        return new FollowingResponse(followerList, hasMorePages);
    }

    public FollowingResponse getFollowingsMileStone4(FollowingRequest request){
        boolean hasMorePages= false;
        Table table = db.getTable(TABLE_NAME);

        HashMap<String, Object> valueMap = new HashMap<>();
        valueMap.put(FOLLOWERING_MAP_KEY, request.getFollower().getAlias());

        QuerySpec querySpec = new QuerySpec()
          .withKeyConditionExpression(FOLLOWING_EXPRESSION)
          .withValueMap(valueMap)
          .withScanIndexForward(true);

        ItemCollection<QueryOutcome> items = null;

        try {
            items = table.query(querySpec);
            Iterator<Item> iterator = items.iterator();
            iterator.hasNext();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new FollowingResponse(e.getMessage());
        }

        QueryOutcome outcome = items.getLastLowLevelResult();
        ArrayList<Item> itemList = new ArrayList<>(outcome.getItems());
        ArrayList<User> followerList = new ArrayList<>();
        hasMorePages = outcome.getQueryResult().getLastEvaluatedKey() != null;

        for (Item item: itemList) {
            User user = new User(item.getString(FOLLOWEE_FIRST_NAME), item.getString(FOLLOWEE_LAST_NAME), item.getString(FOLLOWEE_HANDLE), item.getString(FOLLOWEE_PROFILE_URL));
            followerList.add(user);
        }

        return new FollowingResponse(followerList, hasMorePages);
    }
}