package edu.byu.cs.tweeter.server.lambda.logout;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.server.service.LogoutImpl;

public class LogoutHandler implements RequestHandler<LogoutRequest, LogoutResponse> {
  @Override
  public LogoutResponse handleRequest(LogoutRequest logoutRequest, Context context) {
    LogoutService logoutService = new LogoutImpl();
    return logoutService.logout(logoutRequest);
  }
}
