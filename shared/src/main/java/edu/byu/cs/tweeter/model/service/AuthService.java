package edu.byu.cs.tweeter.model.service;

public interface AuthService {
  boolean isValid(String auth);
  String createAuth();
  void registerValidAuth(String auth, String alias);
}
