package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.SignUpService;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.server.dao.UserDAO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpImpl implements SignUpService {
  @Override
  public LoginResponse signUpUser(SignUpRequest signUpRequest) {
    UserDAO userDAO = new UserDAO();

//    return userDAO.registerUser(signUpRequest);
    return userDAO.registerUserMileStone4(signUpRequest);
  }
}
