package edu.byu.cs.tweeter.server.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.google.gson.Gson;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.RelationshipService;
import edu.byu.cs.tweeter.model.service.request.RelationshipRequest;
import edu.byu.cs.tweeter.model.service.response.RelationshipResponse;
import edu.byu.cs.tweeter.server.dao.RelationshipDAO;

import java.io.IOException;

public class RelationshipImpl implements RelationshipService {
  @Override
  public RelationshipResponse isRelated(RelationshipRequest request) throws IOException {
    RelationshipDAO dao = new RelationshipDAO();
//    RelationshipResponse relationshipResponse = dao.getIsRelated(request);
//    return relationshipResponse;
    return dao.isRelatedMileStone4(request);
  }

  @Override
  public RelationshipResponse follow(RelationshipRequest request) throws IOException {
    RelationshipDAO dao = new RelationshipDAO();
//    return dao.followMileStone3(request);

    // update feeds, need to get stories of destination
    User destination = request.getDestination();

    try{
      // need source and destination
      String item = new Gson().toJson(request);
      String SQSURL = "https://sqs.us-west-2.amazonaws.com/640254099517/FollowSQSHandler";
      SendMessageRequest sendMessageRequest = new SendMessageRequest().withQueueUrl(SQSURL).withMessageBody(item);
      AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
      SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);

    }
    catch (Exception e){
      return new RelationshipResponse(false, e.getMessage());
    }

    return dao.followMileStone4(request);
  }

  @Override
  public RelationshipResponse unfollow(RelationshipRequest request) throws IOException {
    RelationshipDAO dao = new RelationshipDAO();
//    return dao.unfollowMileStone3(request);

    try{
      // need source and destination
      String item = new Gson().toJson(request);
      String SQSURL = "https://sqs.us-west-2.amazonaws.com/640254099517/UnFollowSQSHandler";
      SendMessageRequest sendMessageRequest = new SendMessageRequest().withQueueUrl(SQSURL).withMessageBody(item);
      AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
      SendMessageResult sendMessageResult = sqs.sendMessage(sendMessageRequest);

    }
    catch (Exception e){
      return new RelationshipResponse(false, e.getMessage());
    }
    return dao.unfollowMileStone4(request);
  }
}
