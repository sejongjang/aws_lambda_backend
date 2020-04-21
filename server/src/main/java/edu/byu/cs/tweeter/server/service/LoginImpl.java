package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.AuthDAO;
import edu.byu.cs.tweeter.server.dao.UserDAO;

public class LoginImpl implements LoginService {
  @Override
  public LoginResponse login(LoginRequest loginRequest) {
    UserDAO userDAO = new UserDAO();

//    return userDAO.loginUser(loginRequest);
    return userDAO.loginUserMileStone4(loginRequest);
  }
}
