package edu.byu.cs.tweeter.server;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.*;
import edu.byu.cs.tweeter.model.service.response.*;
import edu.byu.cs.tweeter.server.dao.*;
import edu.byu.cs.tweeter.server.lambda.sqs.UnFollowSQSHandler;

import java.util.ArrayList;
import java.util.List;

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
////      tweetDAO.dropTable();
////      tweetDAO.makeTable();
//      authDAO.dropTable();
//      authDAO.makeTable();
//    } catch (DataAccessException e) {
//      e.printStackTrace();
//    }
//    LoginResponse loginResponse = userDAO.registerUserMileStone4(new SignUpRequest("test8", "123", "first", "last", "url"));
//    System.out.println(loginResponse.isSuccess());
//    userDAO.findUserByAliasMileStone4("test1");
//    LoginResponse loginResponse = userDAO.loginUserMileStone4(new LoginRequest("test1", "123"));
//    System.out.println(loginResponse.isSuccess());

//    User user = new User("first", "last", "tiger5", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//    Story story = new Story(user, "tiger5 test with pic 9", new Date().getTime(), "content://com.android.providers.media.documents/document/image%3A81");

//    TweetResponse tweetResponse = tweetDAO.postTweetMileStone4(new TweetRequest(story));
//    System.out.println(tweetResponse.getMessage());

//    StoryResponse storyResponse = storyDAO.getStoriesMileStone4(new StoryRequest(user));
//    System.out.println(storyResponse.getStoriesMileStone3().size());

    // {TableName: UserAuth,Key: {alias={S: @test1,}},}
//    LogoutResponse logoutResponse = authDAO.signOutMileStone4("@test1");
//    System.out.println(logoutResponse.getMessage());

//    User s = new User("souce", "test", "tiger4", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//    User d = new User("destination", "test", "tiger5", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//
//    RelationshipResponse relationshipResponse = relationshipDAO.followMileStone4(new RelationshipRequest(s, d));
//    System.out.println(relationshipResponse.getMessage())

//    UserResponse userResponse = userDAO.findUserByAliasMileStone4("test1");
//    System.out.println(userResponse.getUser().getAlias());

    User user = new User("aa", "bb", "@aa", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");
//    FollowingResponse followingResponse = followingDAO.getFollowersMileStone4(new FollowingRequest(user, 10, null));
//    System.out.println(followingResponse.getFollowees().size());

//    TweetService tweetService = new TweetImpl();
//    TweetResponse tweetResponse = tweetService.postTweet(new TweetRequest(story));

//    FeedSQSHandler feedSQSHandler = new FeedSQSHandler();
//    SQSEvent event = new SQSEvent();
//    SQSEvent.SQSMessage msg = new SQSEvent.SQSMessage();
//    msg.setBody("{ \"user\": {\"firstName\": \"Test\",\"lastName\": \"User\",\"alias\": \"@tiger5\",\"imgageUrl\": \"urlhere\"},\"message\": \"test\",\"date\": \"2010 06 12\",\"imageUri\": \"123@123\"}");
//    List<SQSEvent.SQSMessage> msgs = new ArrayList<>();
//    msgs.add(msg);
//    event.setRecords(msgs);

//    feedSQSHandler.handleRequest(event, null);

//    SQSEvent event = new SQSEvent();
//    SQSEvent.SQSMessage msg = new SQSEvent.SQSMessage();
//    msg.setBody("{\"story\": {\"user\": {\"firstName\": \"Test\",\"lastName\": \"User\",\"alias\": \"@tiger5\",\"imageUrl\": \"urlhere\"},\"message\": \"tiger5 test 4\",\"date\": \"2020-43-14 07:43:09\",\"imageUri\": \"123@123\"},\"users\": [{\"firstName\": \"black\",\"lastName\": \"tiger\",\"alias\": \"@tiger4\",\"imageUrl\": \"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\",\"name\": \"black tiger\"}]}");
//    List<SQSEvent.SQSMessage> msgs = new ArrayList<>();
//    msgs.add(msg);
//    event.setRecords(msgs);
//    UpdateFeedHandler updateFeedHandler = new UpdateFeedHandler();
//    updateFeedHandler.handleRequest(event, null);

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


    UnFollowSQSHandler unFollowSQSHandler = new UnFollowSQSHandler();
    SQSEvent event = new SQSEvent();
    SQSEvent.SQSMessage msg = new SQSEvent.SQSMessage();
    msg.setBody("{ \"source\": {\"firstName\": \"aa\",\"lastName\": \"bb\",\"alias\": \"@user1\",\"imageUrl\": \"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"}, \"destination\": {\"firstName\": \"Test\",\"lastName\": \"User\",\"alias\": \"@sejong\",\"imageUrl\": \"https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png\"}} ");
    List<SQSEvent.SQSMessage> msgs = new ArrayList<>();
    msgs.add(msg);
    event.setRecords(msgs);

    unFollowSQSHandler.handleRequest(event, null);
  }
}
