package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public interface SignUpService {
  LoginResponse signUpUser(SignUpRequest signUpRequest);
}
