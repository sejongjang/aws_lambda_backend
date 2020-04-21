package edu.byu.cs.tweeter.model.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Story {

    public User user;
    public String alias;
    public String message;
    public String date;
    public String imageUri;
    public long timeStamp;

    public Story() { }

    public static String convertDateToMilliseconds(long sourceDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dateFormat.format(sourceDate);
    }

    public Story(String alias, String story, long timeStamp) {
        this.alias = alias;
        this.message = story;
        this.date = convertDateToMilliseconds(timeStamp);
        this.timeStamp = timeStamp;
    }

    public Story(String alias, String message, long timeStamp, String imageUri) {
        this.alias = alias;
        this.message = message;
        this.date = convertDateToMilliseconds(timeStamp);
        this.imageUri = imageUri;
        this.timeStamp = timeStamp;
    }

    public Story(User user, String message, long timeStamp, String imageUri) {
        this.user = user;
        this.message = message;
        this.date = convertDateToMilliseconds(timeStamp);
        this.imageUri = imageUri;
        this.timeStamp = timeStamp;
    }

    public Story(User currentUser, String s) {
        this.user = currentUser;
        this.message = s;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
