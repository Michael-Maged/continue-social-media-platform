package com.example.saisoof;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class Post {
    private UserProfile author;
    private String caption;
    private Timestamp timestamp;
    private String url;
    private int postId;
    private ArrayList<Like> likes;
    private ArrayList<Comment> comments;

    Post(UserProfile author, String caption, Timestamp timestamp, String url, int postId) {
        this.author = author;
        this.caption = caption;
        this.timestamp = timestamp;
        this.url=url;
        this.postId= postId;
        this.likes = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public UserProfile getAuthor() {
        return this.author;
    }

    public String getCaption() {
        return this.caption;
    }

    public Timestamp getTimeStamp() {
        return this.timestamp;
    }

    public void addlike( int id){
        setlikes(Login.getCurrentUser(), true);
        try (Connection connection = DriverManager.getConnection(Main.url, Main.dbuser, Main.dbpassword)){
            String sql = "INSERT INTO likes (username, postid) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Login.getCurrentUser().getUsername());
            preparedStatement.setInt(2, id);

            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removelike(int id){
        setlikes(Login.getCurrentUser() , false);
        try (Connection connection = DriverManager.getConnection(Main.url, Main.dbuser, Main.dbpassword)){
            String sql = "DELETE FROM likes WHERE username = ? and postid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Login.getCurrentUser().getUsername());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addcomment(String content){
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);

        setcomments(content, currentTimestamp,Login.getCurrentUser());

        try (Connection connection = DriverManager.getConnection(Main.url, Main.dbuser, Main.dbpassword)){
            String sql = "INSERT INTO comments (username, postid, timestmp, content) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Login.getCurrentUser().getUsername());
            preparedStatement.setInt(2, getPostId());
            preparedStatement.setTimestamp(3, currentTimestamp);
            preparedStatement.setString(4, content);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int getLikeCount(){
        return likes.size();
    }

    public int getCommentCount(){
        return comments.size();
    }

    public ArrayList<Like> getLikes() {
        return likes;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPostId() {
        return postId;
    }

    public void setlikes(UserProfile user , boolean addORremove){
        if (addORremove){
            likes.add(new Like(user));
        }
        else{
            likes.remove(searchLike(user));
        }
    }

    public void setcomments(String content,Timestamp currentTimestamp,UserProfile user){
        comments.add(new Comment(content,user,currentTimestamp));
    }

    public boolean Alreadyliked(Post post){
        for(Like like: getLikes()){
            if (like.getLiker().equals(Login.getCurrentUser())){
                return false;
            }
        }
        return true;
    }

    public Like searchLike(UserProfile U) {
        for (Like like : getLikes()){
            if (like.getLiker().equals(U)){
                return like;
            }
        }
        return null;
    }
}
