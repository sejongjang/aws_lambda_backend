package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.server.dao.AuthDAO;

public class LogoutImpl implements LogoutService {

  @Override
  public LogoutResponse logout(LogoutRequest logoutRequest) {
    AuthDAO authDAO = new AuthDAO();
//    return authDAO.signOut(logoutRequest.getUser().getAlias());
    return authDAO.signOutMileStone4(logoutRequest.getUser().getAlias());
  }
}
