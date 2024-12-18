package com.example.saisoof;
import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDateTime;
import javafx.scene.image.ImageView;

public class UserProfile {
    private String username;
    private String password;
    private String email;
    private String bio;
    private String profilePicture;
    private ArrayList<UserProfile> following;
    private ArrayList<Post> userPosts;
    private LocalDateTime lastActive;

    public UserProfile(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.following = new ArrayList<>();
        this.userPosts = new ArrayList<>();
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public String getBio() {
        return this.bio;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }

    public ArrayList<UserProfile> getFollowing() {
        return this.following;
    }

    public LocalDateTime getLastActive() {
        return this.lastActive;
    }

    public int getFollowerCount() {
        return this.following.size();
    }

    public int getPostCount(){
        return this.userPosts.size();
    }

    public void addUserPost(Post post){
        this.userPosts.add(post);

        ImageView postImage = new ImageView(post.getUrl());
        postImage.setFitWidth(175);
        postImage.setFitHeight(200);

        ProfilePageController.postGrid.add(postImage, ProfilePageController.colIndex, ProfilePageController.rowIndex);

        ProfilePageController.colIndex++;
        if (ProfilePageController.colIndex >= 3) {
            ProfilePageController.colIndex = 0;
            ProfilePageController.rowIndex++;
        }
    }

    public void updatePostDB(Post post){
        try (Connection connection = DriverManager.getConnection(Main.url, Main.dbuser, Main.dbpassword)){
            String sql = "INSERT INTO posts (postid, username, url, caption, timestmp) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, post.getPostId());
            preparedStatement.setString(2, post.getAuthor().getUsername());
            preparedStatement.setString(3, post.getUrl());
            preparedStatement.setString(4, post.getCaption());
            preparedStatement.setTimestamp(5, post.getTimeStamp());

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateLastActive() {
        LocalDateTime formattedTime = LocalDateTime.now();
        this.lastActive = formattedTime;
    }

    public void send_follow(String follower) {
        try (Connection connection = DriverManager.getConnection(Main.url, Main.dbuser, Main.dbpassword)){
            String sql = "INSERT INTO followers (username, followss) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Login.getCurrentUser().getUsername());
            preparedStatement.setString(2, follower);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        following.add(Main.getProfileUsingUsername(follower));
    }

    public void remove_follow(UserProfile follower) {
        if (following.contains(follower)){
            following.remove(follower);
        }
    }

    public void updateDB(){
        try (Connection connection = DriverManager.getConnection(Main.url, Main.dbuser, Main.dbpassword)){
            String sql = "update users set bio = ?, profpic = ? where username = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, this.bio);
            statement.setString(2, this.profilePicture);
            statement.setString(3, this.username);

            statement.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            e.getMessage();
        }
    }
}
