package edu.byu.cs.tweeter.server.lambda.relationship;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.RelationshipService;
import edu.byu.cs.tweeter.model.service.request.RelationshipRequest;
import edu.byu.cs.tweeter.model.service.response.RelationshipResponse;
import edu.byu.cs.tweeter.server.service.RelationshipImpl;

import java.io.IOException;

public class UnfollowHandler implements RequestHandler<RelationshipRequest, RelationshipResponse> {
  @Override
  public RelationshipResponse handleRequest(RelationshipRequest input, Context context) {
    RelationshipService relationshipService = new RelationshipImpl();
    try {
      return relationshipService.unfollow(input);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return new RelationshipResponse(false);
  }
}