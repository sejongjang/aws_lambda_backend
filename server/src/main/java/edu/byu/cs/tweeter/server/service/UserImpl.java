package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.UserService;
import edu.byu.cs.tweeter.model.service.request.UserRequest;
import edu.byu.cs.tweeter.model.service.response.UserResponse;
import edu.byu.cs.tweeter.server.dao.UserDAO;

import java.io.IOException;

public class UserImpl implements UserService {
  @Override
  public UserResponse getUserByAlias(UserRequest userRequest) throws IOException {
    UserDAO userDAO = new UserDAO();
//    return userDAO.findUserByAlias(userRequest.getAlias());
    return userDAO.findUserByAliasMileStone4(userRequest.getAlias());
  }
}
