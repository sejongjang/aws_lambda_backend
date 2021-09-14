package edu.byu.cs.tweeter.server;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.TweetService;
import edu.byu.cs.tweeter.model.service.request.*;
import edu.byu.cs.tweeter.model.service.response.*;
import edu.byu.cs.tweeter.server.dao.*;
import edu.byu.cs.tweeter.server.lambda.feed.UpdateFeedHandler;
import edu.byu.cs.tweeter.server.lambda.sqs.FeedSQSHandler;
import edu.byu.cs.tweeter.server.lambda.sqs.UnFollowSQSHandler;
import edu.byu.cs.tweeter.server.service.TweetImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {
  public static void main(String[] args) {
    UserDAO userDAO = new UserDAO();
    TweetDAO tweetDAO = new TweetDAO();
    StoryDAO storyDAO = new StoryDAO();
    AuthDAO authDAO = new AuthDAO();
    FeedDAO feedDAO = new FeedDAO();
    RelationshipDAO relationshipDAO = new RelationshipDAO();
    FollowingDAO followingDAO = new FollowingDAO();

//    try {
////      userDAO.dropTable();
////      userDAO.makeTable();
//
////      tweetDAO.dropTable();
////      tweetDAO.makeTable();
////      authDAO.dropTable();
////      authDAO.makeTable();
//    } catch (DataAccessException e) {
//      e.printStackTrace();
//    }

//    String timeStamp = authDAO.getTimeStamp("@superstar");
//    System.out.println(timeStamp);
//    System.out.println(TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(timeStamp) - System.currentTimeMillis()+20*60*1000));

//    LoginResponse loginResponse = userDAO.registerUserMileStone4(new SignUpRequest("@follower100", "123", "first", "last", "url"));
//    System.out.println(loginResponse.isSuccess());
//    userDAO.findUserByAliasMileStone4("test1");
//    LoginResponse loginResponse = userDAO.loginUserMileStone4(new LoginRequest("@follower100", "123"));
//    System.out.println(loginResponse.isSuccess());

//    User user = new User("first", "last", "@follower100", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//    Story story = new Story(user, "1", new Date().getTime(), "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//
//    TweetResponse tweetResponse = tweetDAO.postTweetMileStone4(new TweetRequest(story));
//    System.out.println(tweetResponse.getMessage());

//    StoryResponse storyResponse = storyDAO.getStoriesMileStone4(new StoryRequest(user));
//    System.out.println(storyResponse.getMessage());
//    System.out.println(storyResponse.getStoriesMileStone3().size());

    // {TableName: UserAuth,Key: {alias={S: @test1,}},}
//    LogoutResponse logoutResponse = authDAO.signOutMileStone4("@test1");
//    System.out.println(logoutResponse.getMessage());

//    User superStar = new User("Iam", "superstar", "superstar", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//    User lastFollower = new User("person1005", "test1005", "follower1005", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

    //follower_handle
    //followee_handle

//    for(int i=0; i<10000; ++i){
//      User d = new User("person"+i, "test"+i, "follower"+i, "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//      RelationshipResponse relationshipResponse = relationshipDAO.followMileStone4(new RelationshipRequest(s, d));
////      userDAO.registerUserMileStone4(new SignUpRequest("follower"+i, "123", "person"+i, "test"+i, "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png"));
//    }

//    UserResponse userResponse = userDAO.findUserByAliasMileStone4("test1");
//    System.out.println(userResponse.getUser().getAlias());

//    User user = new User("aa", "bb", "@follower101", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//    Story story = new Story(user, "1", new Date().getTime(), "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//    FollowingResponse followingResponse = followingDAO.getFollowersMileStone4(new FollowingRequest(superStar, 10, lastFollower));
//    System.out.println(followingResponse.getFollowees().size());

//    TweetService tweetService = new TweetImpl();
//    TweetResponse tweetResponse = tweetService.postTweet(new TweetRequest(story));

//    FeedSQSHandler feedSQSHandler = new FeedSQSHandler();
//    SQSEvent event = new SQSEvent();
//    SQSEvent.SQSMessage msg = new SQSEvent.SQSMessage();
//    msg.setBody("{ \"user\": {\"firstName\": \"Test\",\"lastName\": \"User\",\"alias\": \"@follower101\",\"imageUrl\": \"urlhere\"},\"message\": \"tttest\",\"date\": \"2010 06 12\",\"imageUri\": \"123@123\"}");
//    List<SQSEvent.SQSMessage> msgs = new ArrayList<>();
//    msgs.add(msg);
//    event.setRecords(msgs);
//
//    feedSQSHandler.handleRequest(event, null);

    SQSEvent event = new SQSEvent();
    SQSEvent.SQSMessage msg = new SQSEvent.SQSMessage();
    msg.setBody("{\"story\": {\"user\": {\"firstName\": \"Test\",\"lastName\": \"User\",\"alias\": \"@follower101\",\"imageUrl\": \"urlhere\"},\"message\": \"tiger5 test 4\",\"date\": \"2020-43-14 07:43:09\",\"imageUri\": \"123@123\"},\"users\": [{\"firstName\": \"black\",\"lastName\": \"tiger\",\"alias\": \"@tiger4\",\"imageUrl\": \"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\",\"firstName\": \"black tiger\", \"lastName\": \"black tiger\"}]}");
//    msg.setBody("{\"users\":[{\"firstName\":\"person1\",\"lastName\":\"test1\",\"alias\":\"@follower1\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person10\",\"lastName\":\"test10\",\"alias\":\"@follower10\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person100\",\"lastName\":\"test100\",\"alias\":\"@follower100\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person1000\",\"lastName\":\"test1000\",\"alias\":\"@follower1000\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person1001\",\"lastName\":\"test1001\",\"alias\":\"@follower1001\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person1002\",\"lastName\":\"test1002\",\"alias\":\"@follower1002\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person1003\",\"lastName\":\"test1003\",\"alias\":\"@follower1003\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person1004\",\"lastName\":\"test1004\",\"alias\":\"@follower1004\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person1005\",\"lastName\":\"test1005\",\"alias\":\"@follower1005\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person1006\",\"lastName\":\"test1006\",\"alias\":\"@follower1006\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person1007\",\"lastName\":\"test1007\",\"alias\":\"@follower1007\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person1008\",\"lastName\":\"test1008\",\"alias\":\"@follower1008\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person1009\",\"lastName\":\"test1009\",\"alias\":\"@follower1009\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person101\",\"lastName\":\"test101\",\"alias\":\"@follower101\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"},{\"firstName\":\"person1010\",\"lastName\":\"test1010\",\"alias\":\"@follower1010\",\"imageUrl\":\"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"}],\"story\":{\"user\":{\"firstName\":\"Test\",\"lastName\":\"User\",\"alias\":\"@superstar\",\"imageUrl\":\"urlhere\"},\"message\":\"test with 15 data\",\"date\":\"2010 06 12\",\"imageUri\":\"123@123\",\"timeStamp\":0}}");
    List<SQSEvent.SQSMessage> msgs = new ArrayList<>();
    msgs.add(msg);
    event.setRecords(msgs);
    UpdateFeedHandler updateFeedHandler = new UpdateFeedHandler();
    updateFeedHandler.handleRequest(event, null);

//    FeedResponse feedResponse = feedDAO.getFeedsMileStone4(new FeedRequest(user, 10, null));
//    System.out.println(feedResponse.getFeedsMileStone3().size());

    // when user follows user, find all of their stories and update feeds

//    FollowSQSHandler followSQSHandler = new FollowSQSHandler();
//    SQSEvent event = new SQSEvent();
//    SQSEvent.SQSMessage msg = new SQSEvent.SQSMessage();
//    msg.setBody("{ \"source\": {\"firstName\": \"aa\",\"lastName\": \"bb\",\"alias\": \"@aa\",\"imageUrl\": \"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"}, \"destination\": {\"firstName\": \"Test\",\"lastName\": \"User\",\"alias\": \"@tiger5\",\"imageUrl\": \"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"}} ");
//    List<SQSEvent.SQSMessage> msgs = new ArrayList<>();
//    msgs.add(msg);
//    event.setRecords(msgs);
//
//    followSQSHandler.handleRequest(event, null);


//    FollowUpdateFeedHandler followUpdateFeedHandler = new FollowUpdateFeedHandler();
//    SQSEvent event = new SQSEvent();
//    SQSEvent.SQSMessage msg = new SQSEvent.SQSMessage();
//    msg.setBody("{ \"source\": {\"firstName\": \"aa\",\"lastName\": \"bb\",\"alias\": \"@aa\",\"imageUrl\": \"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"}, \"destination\": {\"firstName\": \"Test\",\"lastName\": \"User\",\"alias\": \"@tiger5\",\"imageUrl\": \"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"}, \"stories\": [{\"user\": {\"firstName\": \"Test\",\"lastName\": \"User\",\"alias\": \"@tiger5\",\"imageUrl\": \"urlhere\"},\"message\": \"test3\",\"date\": \"2010 06 13\",\"imageUri\": \"123@123\",\"timeStamp\": \"1586926474726\"}, {\"user\": {\"firstName\": \"Test\",\"lastName\": \"User\",\"alias\": \"@tiger5\",\"imageUrl\": \"urlhere\"},\"message\": \"test4\",\"date\": \"2010 06 13\",\"imageUri\": \"123@123\",\"timeStamp\": \"1586924980685\"}] } ");
//    List<SQSEvent.SQSMessage> msgs = new ArrayList<>();
//    msgs.add(msg);
//    event.setRecords(msgs);
//    followUpdateFeedHandler.handleRequest(event, null);


//    UnFollowSQSHandler unFollowSQSHandler = new UnFollowSQSHandler();
//    SQSEvent event = new SQSEvent();
//    SQSEvent.SQSMessage msg = new SQSEvent.SQSMessage();
//    msg.setBody("{ \"source\": {\"firstName\": \"aa\",\"lastName\": \"bb\",\"alias\": \"@user1\",\"imageUrl\": \"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"}, \"destination\": {\"firstName\": \"Test\",\"lastName\": \"User\",\"alias\": \"@sejong\",\"imageUrl\": \"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"}} ");
//    List<SQSEvent.SQSMessage> msgs = new ArrayList<>();
//    msgs.add(msg);
//    event.setRecords(msgs);
//
//    unFollowSQSHandler.handleRequest(event, null);
  }
}
