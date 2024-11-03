package com.example.saisoof;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.io.IOException;

public class Main extends Application{

    public static final String url = "jdbc:mysql://127.0.0.1:3306/socialdb";
    public static final String dbuser = "root";
    public static final String dbpassword = "mypass";

    public static ArrayList<UserProfile> allUsers = new ArrayList<>();

    @Override
    public void start(Stage primaryStage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("LoginPagefx.fxml"));
            primaryStage.setTitle("HIVE");
            primaryStage.setScene(new Scene(root, 550, 600));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        initializeUserArray();
        Application.launch(args);
    }


    public static void initializeUserArray(){
        try (Connection connection = DriverManager.getConnection(url, dbuser, dbpassword)) {
            String sql = "SELECT * FROM users";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        String username = rs.getString("username");
                        String email = rs.getString("email");
                        String password = rs.getString("pass");
                        String bio = rs.getString("bio");
                        String profilePic = rs.getString("profpic");

                        UserProfile user = new UserProfile(username, email, password);
                        user.setBio(bio);
                        user.setProfilePicture(profilePic);
                        allUsers.add(user);
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("EEEEEEEERRRRRR");
        }
    }

    public static void initializeUserFollowers(UserProfile currentUser){
        try (Connection connection = DriverManager.getConnection(url, dbuser, dbpassword)) {
            String sql = "SELECT followss FROM followers WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, currentUser.getUsername());
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        String following = rs.getString("followss");
                        currentUser.getFollowing().add(getProfileUsingUsername(following));
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("EEEEEEEEEEERRROORRRRRRRRRRRRRR");
        }

    }

    public static void initializeUserPosts(UserProfile currentUser) {
        try (Connection connection = DriverManager.getConnection(url, dbuser, dbpassword)) {
            String sql = "SELECT * FROM posts WHERE username = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, currentUser.getUsername());
                try (ResultSet rs = statement.executeQuery()) {
                    while (rs.next()) {
                        int postid = rs.getInt("postid");
                        String url = rs.getString("url");
                        String caption = rs.getString("caption");
                        Timestamp timestamp = rs.getTimestamp("timestmp");


                        Post post = new Post(currentUser, caption, timestamp, url,postid);

                        currentUser.addUserPost(post);

                        String sql3 = "SELECT * FROM likes WHERE postid = ?";
                        try (PreparedStatement likeS = connection.prepareStatement(sql3)){
                            likeS.setInt(1, postid);
                            try (ResultSet likeRes = likeS.executeQuery()){
                                while (likeRes.next()){
                                    String liker = likeRes.getString("username");
                                    post.getLikes().add(new Like(Main.getProfileUsingUsername(liker)));
                                }
                            }
                        }

                        String sql4 = "SELECT * FROM comments WHERE postid = ?";
                        try (PreparedStatement commentS = connection.prepareStatement(sql4)){
                            commentS.setInt(1, postid);
                            try (ResultSet commentRes = commentS.executeQuery()){
                                while (commentRes.next()){
                                    String commentor = commentRes.getString("username");
                                    String content =  commentRes.getString("content");
                                    Timestamp commDate = commentRes.getTimestamp("timestmp");
                                    post.getComments().add(new Comment(content, Main.getProfileUsingUsername(commentor), commDate));
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("EEEEEEEEEEERRROORRRRRRRRRRRRRR");
        }
    }

    public static UserProfile getProfileUsingUsername(String username){
        for(UserProfile user:Main.getAllUsers()){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public static Post getPostUsingid(int id){
        for(Post post:Timeline.getAllposts()){
            if (post.getPostId() == id){
                return post;
            }
        }
        return null;
    }

    public static ArrayList<UserProfile> getAllUsers(){
        return allUsers;
    }

    public static void setAllUsers(UserProfile user){
        allUsers.add(user);
    }

    public static void selffollow(String username){
        try (Connection connection = DriverManager.getConnection(Main.url, Main.dbuser, Main.dbpassword)){
            String sql = "INSERT INTO followers (username , followss) VALUES (?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, username);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

