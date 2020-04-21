package edu.byu.cs.tweeter.server.service;

import edu.byu.cs.tweeter.model.service.FollowingService;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.server.dao.FollowingDAO;

/**
 * Contains the business logic for getting the users a user is following.
 */
public class FollowingServiceImpl implements FollowingService {

    @Override
    public FollowingResponse getFollowings(FollowingRequest request) {
        FollowingDAO dao = new FollowingDAO();
//        return dao.getFollowees(request);
        return dao.getFollowingsMileStone4(request);
    }

    @Override
    public FollowingResponse getFollowers(FollowingRequest request) {
        FollowingDAO dao = new FollowingDAO();
//        return dao.getFollowersMileStone3(request);
        return dao.getFollowersMileStone4(request);
    }
}
