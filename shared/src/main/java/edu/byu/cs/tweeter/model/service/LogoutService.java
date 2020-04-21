package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;

public interface LogoutService {
  LogoutResponse logout(LogoutRequest logoutRequest);
}
