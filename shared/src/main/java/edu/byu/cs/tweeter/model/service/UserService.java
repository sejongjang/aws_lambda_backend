package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.UserResponse;

import java.io.IOException;

public interface UserService {
  UserResponse getUserByAlias(UserRequest userRequest) throws IOException;
}
