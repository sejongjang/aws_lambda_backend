package edu.byu.cs.tweeter.server.dao;

import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.Story;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.*;
import edu.byu.cs.tweeter.model.service.response.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MileStone3Facade {

  private static MileStone3Facade instance;
  private static Map<User, List<User>> followingMap;
  private static Map<User, List<User>> followersMap;
  private static List<Follow> follows;
  private static Map<User, List<Story>> storiesMap;
  private static Map<User, List<Story>> feedsMap;
  private static List<User> mockUserList;
  private static List<User> allMockUsers;
  private static int MOCK_STORIES_NUM = 3;

  final String url = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";
  final User user1 = new User("Daffy", "Duck", url);
  final User user2 = new User("Fred", "Flintstone", url);
  final User user3 = new User("Barney", "Rubble", url); // 2 followees
  final User user4 = new User("Wilma", "Rubble", url);
  final User user5 = new User("Clint", "Eastwood", url); // 6 followees
  final User user6 = new User("Mother", "Teresa", url); // 7 followees
  final User user7 = new User("Harriett", "Hansen", url);
  final User user8 = new User("Zoe", "Zabriski", url);
  final User user9 = new User("Albert", "Awesome", url); // 1  destination
  final User user10 = new User("Star", "Student", url);
  final User user11 = new User("Bo", "Bungle", url);
  final User user12 = new User("Susie", "Sampson", url);
  final User testUser = new User("Test", "User", "@Test User", url);
  final User testUser2 = new User("Test", "User2", "@Test User2", url);

  public static MileStone3Facade getInstance() {
    if(instance == null) {
      instance = new MileStone3Facade();
    }

    return instance;
  }

  private String encrypt(String password) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
    return new String(hash);
  }

  private void setAllMockUsers(){
    allMockUsers = new ArrayList<>();
    allMockUsers.add(user1);
    allMockUsers.add(user2);
    allMockUsers.add(user3);
    allMockUsers.add(user4);
    allMockUsers.add(user5);
    allMockUsers.add(user6);
    allMockUsers.add(user7);
    allMockUsers.add(user8);
    allMockUsers.add(user9);
    allMockUsers.add(user10);
    allMockUsers.add(user11);
    allMockUsers.add(user12);
    allMockUsers.add(testUser);
    allMockUsers.add(testUser2);
  }

  private List<Follow> setFollow(){
    final Follow follow1 = new Follow(testUser, user5);
    final Follow follow2 = new Follow(testUser, user1);
    final Follow follow3 = new Follow(testUser, user8);
    final Follow follow4 = new Follow(testUser,  user9);
    final Follow follow5 = new Follow(user5,  user11);
    final Follow follow6 = new Follow(user5,  user1);
    final Follow follow7 = new Follow(user5,  user2);
    final Follow follow8 = new Follow(user5,  user4);
    final Follow follow9 = new Follow(user5,  testUser);
    final Follow follow10 = new Follow(user6,  user3);
    final Follow follow11 = new Follow(user6,  user5);
    final Follow follow12 = new Follow(user6,  user1);
    final Follow follow13 = new Follow(user6,  user7);
    final Follow follow14 = new Follow(user6,  testUser);
    final Follow follow15 = new Follow(user6,  user12);
    final Follow follow16 = new Follow(user6,  user4);

    final List<Follow> follows = Arrays.asList(follow1, follow2, follow3, follow4, follow5, follow6,
      follow7, follow8, follow9, follow10, follow11, follow12, follow13, follow14, follow15,
      follow16);
    return follows;
  }

  private MileStone3Facade(){
    setFakeFollowing();
    setFakeFollwer();

    List<User> users = new ArrayList<>();
    users.add(testUser);
    followingMap.put(testUser2, users);
    followersMap.put(testUser2, users);

    setFakeStories();
    setFakeFeeds();
    setAllMockUsers();
    mockUserList = new ArrayList<>();
    mockUserList.addAll(allMockUsers);
    testUser.setAlias("123");
    try {
      testUser.setPassword(encrypt("123"));
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }
  }

  private void setFakeFollowing(){
    followingMap = new HashMap<>();
    follows = getFollowGenerator().generateUsersAndFollows(100, 0, 50, FollowGenerator.Sort.FOLLOWER_FOLLOWEE);
    follows.addAll(setFollow());
    for(Follow follow : follows) {
      List<User> followees = followingMap.get(follow.getFollower());

      if(followees == null) {
        followees = new ArrayList<>();
        followingMap.put(follow.getFollower(), followees);
      }

      followees.add(follow.getFollowee());
    }
  }

  private void setFakeFollwer(){
    followersMap = new HashMap<>();
    for(Follow follow : follows) {
      List<User> followers = followersMap.get(follow.getFollowee());

      if(followers == null) {
        followers = new ArrayList<>();
        followersMap.put(follow.getFollowee(), followers);
      }

      followers.add(follow.getFollower());
    }
  }

  private void setFakeStories() {
    storiesMap = new HashMap<>();

    for (Map.Entry<User, List<User>> followingEntry : followingMap.entrySet()) {

      List<Story> stories = new ArrayList<>();
      for (int i = 0; i < MOCK_STORIES_NUM; i++) {
        stories.add(new Story(followingEntry.getKey(), " test message " + i));
      }

      storiesMap.put(followingEntry.getKey(), stories);
    }
  }

  private void setFakeFeeds(){

    feedsMap = new HashMap<>();

    for (Map.Entry<User, List<User>> followingEntry : followingMap.entrySet()) {

      List<Story> stories = new ArrayList<>();
      for (User following: followingEntry.getValue()) {
        if(storiesMap.get(following) != null) {
          stories.addAll(storiesMap.get(following));
        }
      }
      feedsMap.put(followingEntry.getKey(), stories);
    }
  }

  public FollowingResponse getFollowersMileStone3(FollowingRequest request){
    assert request.getLimit() >= 0;
    assert request.getFollower() != null;

    List<User> allFollowers = followersMap.get(findUserByAliasMileStone3(request.getFollower().getAlias()).getUser());
    List<User> responseFollowers = new ArrayList<>(request.getLimit());
    request.setLastFollowee(new User("Clint", "Eastwood", "@Clint Eastwood", url));

    boolean hasMorePages = false;

    if(request.getLimit() > 0) {
      if (allFollowers != null) {
        int followeesIndex = getFolloweesStartingIndex(findUserByAliasMileStone3(request.getLastFollowee().getAlias()).getUser(), allFollowers);

        for(int limitCounter = 0; followeesIndex < allFollowers.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
          responseFollowers.add(allFollowers.get(followeesIndex));
        }

        hasMorePages = followeesIndex < allFollowers.size();
      }
    }

    if(responseFollowers.size() == 0){
      return new FollowingResponse("no follower");
    }
    else{
      responseFollowers.sort(Comparator.comparing(User::getFirstName));
    }

    return new FollowingResponse(responseFollowers, hasMorePages);
  }

  public FollowingResponse getFollowingMileStone3(FollowingRequest request) {

    assert request.getLimit() >= 0;
    assert request.getFollower() != null;

    List<User> allFollowees = followingMap.get(findUserByAliasMileStone3(request.getFollower().getAlias()).getUser());
    List<User> responseFollowees = new ArrayList<>(request.getLimit());
    request.setLastFollowee(new User("Clint", "Eastwood", "@Clint Eastwood", url));

    boolean hasMorePages = false;

    if(request.getLimit() > 0) {
      if (allFollowees != null) {
        int followeesIndex = getFolloweesStartingIndex(findUserByAliasMileStone3(request.getLastFollowee().getAlias()).getUser(), allFollowees);

        for(int limitCounter = 0; followeesIndex < allFollowees.size() && limitCounter < request.getLimit(); followeesIndex++, limitCounter++) {
          responseFollowees.add(allFollowees.get(followeesIndex));
        }
        hasMorePages = followeesIndex < allFollowees.size();
      }
    }

    if(responseFollowees.size() == 0){
      return new FollowingResponse("User has no followers!");
    }
    else{
      responseFollowees.sort(Comparator.comparing(User::getFirstName));
    }


    return new FollowingResponse(responseFollowees, hasMorePages);
  }

  private int getFolloweesStartingIndex(User lastFollowee, List<User> allFollowees) {

    int followeesIndex = 0;
    System.out.println(lastFollowee);

    if(lastFollowee != null) {
      // This is a paged request for something after the first page. Find the first item
      // we should return
      for (int i = 0; i < allFollowees.size(); i++) {
        if(lastFollowee.equals(allFollowees.get(i))) {
          // We found the index of the last item returned last time. Increment to get
          // to the first one we should return
          followeesIndex = i + 1;
        }
      }
    }

    return followeesIndex;
  }

  FollowGenerator getFollowGenerator() {
    return FollowGenerator.getInstance();
  }

  public StoryResponse getStoriesMileStone3(StoryRequest storyRequest){
    User user = storyRequest.getUser();

    assert storyRequest.getUser() != null;

    boolean hasMorePages = false;

    List<Story> stories = storiesMap.get(user);
    List<Story> responseStories = new ArrayList<>();

    if (stories != null) {
      int storyIndex = getStoryStartingIndex(storyRequest.getLastestStory(), stories);

      for(int limitCounter = 0; storyIndex < stories.size() && limitCounter < storyRequest.getLimit(); storyIndex++, limitCounter++) {
        responseStories.add(stories.get(storyIndex));
      }

      hasMorePages = storyIndex < stories.size();
    }

    if(responseStories.size() == 0) return new StoryResponse("no story");

    return new StoryResponse(responseStories, hasMorePages);
  }

  private int getStoryStartingIndex(Story lastStory, List<Story> stories) {

    int statusIndex = 0;

    if(lastStory != null) {
      // This is a paged request for something after the first page. Find the first item
      // we should return
      for (int i = 0; i < stories.size(); i++) {
        if(lastStory.equals(stories.get(i))) {
          // We found the index of the last item returned last time. Increment to get
          // to the first one we should return
          statusIndex = i + 1;
        }
      }
    }

    return statusIndex;
  }

  public FeedResponse getFeedsMileStone3(FeedRequest feedRequest){
    User user = feedRequest.getFollower();

    assert feedRequest.getLimit() >= 0;
    assert feedRequest.getFollower() != null;

    boolean hasMorePages = false;

    List<Story> statusList = feedsMap.get(user);
    List<Story> feedResponse = new ArrayList<>();

    if(feedRequest.getLimit() > 0) {
      if (statusList != null) {
        int storyIndex = getStoryStartingIndex(feedRequest.getLastFeed(), statusList);

        for(int limitCounter = 0; storyIndex < statusList.size() && limitCounter < feedRequest.getLimit(); storyIndex++, limitCounter++) {
          feedResponse.add(statusList.get(storyIndex));
        }

        hasMorePages = storyIndex < statusList.size();
      }
    }

    feedResponse.sort(Comparator.comparingLong(Story::getTimeStamp));

    return new FeedResponse(hasMorePages, feedResponse);
  }

  public LoginResponse loginUserMileStone3(LoginRequest loginRequest){
    String encryptedString = null;
    try {
      encryptedString = encrypt(loginRequest.getPassword());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    }

    String requestedAlias = loginRequest.getUsername();
    for (User user : mockUserList) {
      if (requestedAlias.equals(user.getAlias())) {
        if ((user.getPassword().equals(encryptedString))) {
          return new LoginResponse(user);
        }
        return new LoginResponse("wrong password");
      }
    }

    return new LoginResponse("fail to login user");
  }

  public LoginResponse registerUserMileStone3(SignUpRequest signUpRequest){

    List<User> mockFollowees = new ArrayList<>();
    List<User> mockFollowers = new ArrayList<>();
    mockFollowees.add(testUser2);
    mockFollowers.add(testUser2);

    List<Story> stories = new ArrayList<>();
    List<Story> newStoreis = new ArrayList<>();

    if(signUpRequest.getAlias() == null
      || signUpRequest.getPassword() == null
      ||signUpRequest.getFirstname() == null
      || signUpRequest.getLastname() == null){
      return new LoginResponse(false, "please check input");
    }

    for (User user: mockUserList) {
      if(user.getAlias().equals(signUpRequest.getAlias())){
        return new LoginResponse(false, "user already exist");
      }
    }

    if(signUpRequest.getImageURL() == null) signUpRequest.setImageURL(url);
    User newUser = new User(signUpRequest.getFirstname(), signUpRequest.getLastname(), signUpRequest.getAlias(), signUpRequest.getImageURL());
    Story story = new Story(testUser2,"this is test story");
    stories.add(story);
    newStoreis.add(new Story(newUser, "hello world, this is test story from " + newUser.getAlias()));


    followingMap.put(newUser,mockFollowees);
    followingMap.get(testUser2).add(newUser);

    followersMap.get(testUser2).add(newUser);
    followersMap.put(newUser, mockFollowers);

    storiesMap.get(testUser2).add(story);
    feedsMap.put(newUser, stories);

    storiesMap.put(newUser, newStoreis);
    mockUserList.add(newUser);

    return new LoginResponse(newUser);
  }

  public TweetResponse postMileStone3(TweetRequest tweetRequest){
    Story story = tweetRequest.getStory();
    User user = story.getUser();

    if(storiesMap.get(user) == null){
      return new TweetResponse(false, "fail to get user" + storiesMap.get(new User("Test", "User", "@Test User")).get(0).getMessage());
    }

    storiesMap.get(user).add(story);

    for (User follower : followersMap.get(user)) {
      feedsMap.get(follower).add(story);
    }

    return new TweetResponse(true, "success post mock tweet" + storiesMap.get(user).size(), storiesMap.get(user));
  }

  public UserResponse findUserByAliasMileStone3(String alias){
    for (Map.Entry<User, List<User>> followingMap : followingMap.entrySet()) {
      String s = followingMap.getKey().getAlias();
      if(s.equals(alias)) return new UserResponse(followingMap.getKey());
    }
    for(User user : mockUserList){
      if(user.getAlias().equals(alias)){
        return new UserResponse(user);
      }
    }
    return new UserResponse(alias + " not exist in the mock database");
  }

  public RelationshipResponse isRelatedMileStone3(RelationshipRequest relationshipRequest){
    User source = relationshipRequest.getSource();
    User destination = relationshipRequest.getDestination();
    if(followingMap.get(source) != null){
      boolean following = followingMap.get(source).contains(destination);
      return new RelationshipResponse(following);
    }
    return new RelationshipResponse(false, "check the input");
  }

  public RelationshipResponse followMileStone3(RelationshipRequest relationshipRequest){
    User source = relationshipRequest.getSource();
    User destination = relationshipRequest.getDestination();

    if((findUserByAliasMileStone3(source.getAlias()) == null) || findUserByAliasMileStone3(destination.getAlias()) == null){
      return new RelationshipResponse("source or destination do not exist");
    }

    if (followingMap.get(source).add(destination) && followersMap.get(destination).add(source)){
      return new RelationshipResponse(true, source.getAlias() + " follows " + destination.getAlias());
    }
    return new RelationshipResponse(false, "fail to followMileStone3 user");
  }

  public RelationshipResponse unfollowMileStone3(RelationshipRequest relationshipRequest){

    User source = relationshipRequest.getSource();
    User destination = relationshipRequest.getDestination();

    if((findUserByAliasMileStone3(source.getAlias()) == null) || findUserByAliasMileStone3(destination.getAlias()) == null){
      return new RelationshipResponse("source or destination do not exist");
    }

    if(followMileStone3(relationshipRequest).isSuccess()){
      followingMap.get(source).remove(destination);
      followersMap.get(destination).remove(source);
      List<Story> stories = new ArrayList<>(feedsMap.get(source));

      stories.removeIf(status -> status.getUser() == destination);

      feedsMap.remove(source);
      feedsMap.put(destination, stories);

      return new RelationshipResponse(true, source.getAlias() + " unfollowMileStone3 " + destination.getAlias());
    }

    return new RelationshipResponse(false, "fail to unfollowMileStone3 user");
  }

  public LogoutResponse signOutUserMileStone3(String alias){
    if(mockUserList.contains(findUserByAliasMileStone3(alias))){
      return new LogoutResponse(true, alias + " signed out");
    }
    return new LogoutResponse(false, "fail to sign out");
  }

}

