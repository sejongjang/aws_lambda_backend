package edu.byu.cs.tweeter.server.lambda.following;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.server.service.FollowingServiceImpl;

public class GetFollowerHandler implements RequestHandler<FollowingRequest, FollowingResponse> {

  @Override
  public FollowingResponse handleRequest(FollowingRequest request, Context context) {
    FollowingServiceImpl service = new FollowingServiceImpl();
    return service.getFollowers(request);
  }
}
