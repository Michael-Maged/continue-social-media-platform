package com.example.saisoof;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;


public class Timeline {

    public static ArrayList<Post> allposts = new ArrayList<>();

    public static ArrayList<Post> getAllposts() {
        return allposts;
    }

    public static void setAllposts(ArrayList<Post> allposts) {
        Timeline.allposts = allposts;
    }

    public static void loadAllPosts(UserProfile currentUser){
        try (Connection connection = DriverManager.getConnection(Main.url, Main.dbuser, Main.dbpassword)) {
            String sql = "SELECT followss FROM followers where username = ?";
            try (PreparedStatement followingS = connection.prepareStatement(sql)) {
                followingS.setString(1, currentUser.getUsername());
                try (ResultSet followingRes = followingS.executeQuery()) {
                    while (followingRes.next()) {
                        String followingUsername = followingRes.getString("followss");

                        String sql2 = "SELECT * FROM posts where username = ?";
                        try (PreparedStatement postS = connection.prepareStatement(sql2)) {
                            postS.setString(1, followingUsername);
                            try (ResultSet postRes = postS.executeQuery()) {
                                while (postRes.next()) {
                                    int postid = postRes.getInt("postid");
                                    String url = postRes.getString("url");
                                    String caption = postRes.getString("caption");
                                    Timestamp timestamp = postRes.getTimestamp("timestmp");

                                    Post post = new Post(Main.getProfileUsingUsername(followingUsername), caption, timestamp, url,postid);
                                    allposts.add(post);

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
                                                String content = commentRes.getString("content");
                                                Timestamp commDate = commentRes.getTimestamp("timestmp");
                                                post.getComments().add(new Comment(content, Main.getProfileUsingUsername(commentor), commDate));
                                            }
                                        }
                                    }
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
}
