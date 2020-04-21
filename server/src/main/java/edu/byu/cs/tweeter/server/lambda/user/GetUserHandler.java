package edu.byu.cs.tweeter.server.lambda.user;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.UserService;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.UserResponse;
import edu.byu.cs.tweeter.server.service.UserImpl;

import java.io.IOException;

public class GetUserHandler implements RequestHandler<UserRequest, UserResponse> {

  @Override
  public UserResponse handleRequest(UserRequest userRequest, Context context) {
    UserService userService = new UserImpl();
    try {
      return userService.getUserByAlias(userRequest);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new UserResponse(false, "internal server Error");
  }
}