package edu.byu.cs.tweeter.server.lambda.login;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.AuthService;
import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.server.service.AuthImpl;
import edu.byu.cs.tweeter.server.service.LoginImpl;

public class LoginHandler implements RequestHandler<LoginRequest, LoginResponse> {

  @Override
  public LoginResponse handleRequest(LoginRequest loginRequest, Context context) {
    AuthService authService = new AuthImpl();
    LoginService loginService = new LoginImpl();

    LoginResponse loginResponse = loginService.login(loginRequest);
    if(loginResponse.isSuccess()) {
      String auth = authService.createAuth();
      authService.registerValidAuth(auth, loginResponse.getUser().getAlias());
      loginResponse.setAuth(auth);
      return loginResponse;
    }
    return new LoginResponse(false, loginResponse.getMessage());
  }
}
