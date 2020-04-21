package edu.byu.cs.tweeter.model.service;

import edu.byu.cs.tweeter.model.service.request.RelationshipRequest;
import edu.byu.cs.tweeter.model.service.response.RelationshipResponse;

import java.io.IOException;

public interface RelationshipService {
  RelationshipResponse isRelated(RelationshipRequest request) throws IOException;
  RelationshipResponse follow(RelationshipRequest request) throws IOException;
  RelationshipResponse unfollow(RelationshipRequest request) throws IOException;
}
