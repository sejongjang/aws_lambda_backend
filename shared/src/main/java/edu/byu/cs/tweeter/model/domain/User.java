package edu.byu.cs.tweeter.model.domain;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a user in the system.
 */
public class User implements Comparable<User> {

    public String firstName;
    public String lastName;
    public String alias;
    public String imageUrl;
    public String password;
    public String username;
    public ArrayList<Story> tweets;

    public User() {
    }

    public User(String firstName, String lastName, String imageURL) {
        this(firstName, lastName, String.format("@%s%s", firstName, lastName), imageURL);
    }

    public User(String firstName, String lastName, String alias, String imageURL) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageURL;
//        this.alias = alias;
        if (alias.contains("@")){
            this.alias = alias;
        }
        else {
            this.alias = String.format("@%s", alias);
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getAlias() {
        return alias;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return String.format("%s %s", firstName, lastName);
    }


    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return alias.equals(user.alias);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias);
    }

    @Override
    public String toString() {
        return "User{" +
          "firstName='" + firstName + '\'' +
          ", lastName='" + lastName + '\'' +
          ", alias='" + alias + '\'' +
          ", imageUrl='" + imageUrl + '\'' +
          '}';
    }

    @Override
    public int compareTo(User user) {
        return this.getAlias().compareTo(user.getAlias());
    }

    public void addTweet(Story tweet){
        if(tweets == null) tweets = new ArrayList<>();
        tweets.add(tweet);
    }
}
