package edu.byu.cs.tweeter.server.service;


import edu.byu.cs.tweeter.model.service.AuthService;
import edu.byu.cs.tweeter.server.dao.AuthDAO;

import java.util.UUID;

public class AuthImpl implements AuthService {
  private AuthDAO authDAO;

  public AuthImpl(){
    authDAO = new AuthDAO();
  }

  @Override
  public boolean isValid(String authToken) {
    return authDAO.validateToken(authToken);
  }

  @Override
  public String createAuth() {
    return UUID.randomUUID().toString();
  }

  @Override
  public void registerValidAuth(String auth, String alias) {
    authDAO.registerValidAuth(auth, alias);
  }
}
