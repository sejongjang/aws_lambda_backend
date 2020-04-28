package edu.byu.cs.tweeter.model.service.request;


import edu.byu.cs.tweeter.model.domain.User;

/**
 * Contains all the information needed to make a request to have the service return the next page of
 * followees for a specified destination.
 */
public class FollowingRequest {

    public User follower;
    public int limit;
    public User lastFollowee;

    /**
     * Allows construction of the object from Json. Private so it won't be called in normal code.
     */
    private FollowingRequest() {}

    /**
     * Creates an instance.
     *
     * @param follower the {@link User} whose followees are to be returned.
     * @param limit the maximum number of followees to return.
     * @param lastFollowee the last destination that was returned in the previous request (null if
     *                     there was no previous request or if no followees were returned in the
     *                     previous request).
     */
    public FollowingRequest(User follower, int limit, User lastFollowee) {
        this.follower = follower;
        this.limit = limit;
        this.lastFollowee = lastFollowee;
    }

    /**
     * Returns the destination whose followees are to be returned by this request.
     *
     * @return the destination.
     */
    public User getFollower() {
        return follower;
    }

    /**
     * Sets the destination.
     *
     * @param follower the destination.
     */
    public void setFollower(User follower) {
        this.follower = follower;
    }

    /**
     * Returns the number representing the maximum number of followees to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Sets the limit.
     *
     * @param limit the limit.
     */
    public void setLimit(int limit) {
        this.limit = limit;
    }

    /**
     * Returns the last destination that was returned in the previous request or null if there was no
     * previous request or if no followees were returned in the previous request.
     *
     * @return the last destination.
     */
    public User getLastFollowee() {
        return lastFollowee;
    }

    /**
     * Sets the last destination.
     *
     * @param lastFollowee the last destination.
     */
    public void setLastFollowee(User lastFollowee) {
        this.lastFollowee = lastFollowee;
    }
}
