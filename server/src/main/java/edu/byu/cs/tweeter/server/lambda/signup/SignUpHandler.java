package edu.byu.cs.tweeter.server.lambda.signup;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import edu.byu.cs.tweeter.model.service.AuthService;
import edu.byu.cs.tweeter.model.service.SignUpService;
import edu.byu.cs.tweeter.model.service.request.SignUpRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.server.service.AuthImpl;
import edu.byu.cs.tweeter.server.service.SignUpImpl;

public class SignUpHandler implements RequestHandler<SignUpRequest, LoginResponse> {

  @Override
  public LoginResponse handleRequest(SignUpRequest signUpRequest, Context context) {
    AuthService authService = new AuthImpl();
    SignUpService signUpService = new SignUpImpl();

    LoginResponse loginResponse = signUpService.signUpUser(signUpRequest);
    if(loginResponse.isSuccess()){
      String auth = authService.createAuth();
      authService.registerValidAuth(auth, loginResponse.getUser().getAlias());
      loginResponse.setAuth(auth);
      return loginResponse;
    }

    return new LoginResponse(false, loginResponse.getMessage());
  }
}