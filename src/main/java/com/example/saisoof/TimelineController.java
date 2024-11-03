package com.example.saisoof;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class TimelineController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private Label userNotFoundLabel;

    @FXML
    private Hyperlink profileLink;

    @FXML
    private Hyperlink addPostLink;

    @FXML
    private ScrollPane scrollPosts;

    @FXML
    private Hyperlink refreshLink;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        VBox posts = new VBox(20);

        for(int i = 0; i < Timeline.allposts.size(); i++){
            int index = i;
            Label usernameFLabel = new Label(Timeline.allposts.get(i).getAuthor().getUsername());
            usernameFLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

            LocalDateTime dateTime = Timeline.allposts.get(i).getTimeStamp().toLocalDateTime();
            LocalDate dateCreated = dateTime.toLocalDate();
            Label timestamp = new Label(dateCreated.toString());
            timestamp.setFont(Font.font("Arial", 12));

            HBox nameTime = new HBox(40, usernameFLabel, timestamp);
            nameTime.setAlignment(Pos.CENTER_LEFT);

            ImageView post = new ImageView(Timeline.allposts.get(i).getUrl());
            post.setFitWidth(200);
            post.setFitHeight(200);

            Label captionLabel = new Label(Timeline.allposts.get(i).getCaption());

            ToggleButton addlikeButton = new ToggleButton("Like");
            addlikeButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-font-weight: bold;");

            Label numberoflikes = new Label(""+Timeline.allposts.get(i).getLikeCount()+ " Likes");
            numberoflikes.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            addlikeButton.setOnAction(e->{
                if (addlikeButton.isSelected() && Timeline.allposts.get(index).Alreadyliked(Timeline.allposts.get(index))) {
                    addlikeButton.setText("Unlike");
                    Timeline.allposts.get(index).addlike(Timeline.allposts.get(index).getPostId());
                    numberoflikes.setText(""+(Timeline.allposts.get(index).getLikeCount())+" Likes");
                }
                else {
                    addlikeButton.setText("Like");
                    Timeline.allposts.get(index).removelike(Timeline.allposts.get(index).getPostId());
                    numberoflikes.setText(""+(Timeline.allposts.get(index).getLikeCount()) + " Likes");
                }
            });
            HBox likessection = new HBox(20,addlikeButton,numberoflikes);

            TextField commenTextField = new TextField();

            commenTextField.setPrefHeight(10);
            commenTextField.setPrefWidth(40);
            commenTextField.setStyle("-fx-background-color: #FAFAFA; -fx-border-width: 0px;");

            Label numberofcomments = new Label(""+Timeline.allposts.get(i).getCommentCount()+ " Comments");
            numberofcomments.setFont(Font.font("Arial", FontWeight.BOLD, 16));

            VBox postComments = new VBox(10);
            for(int j = 0; j < Timeline.allposts.get(index).getCommentCount(); j++){
                Label commentorusername = new Label(""+Timeline.allposts.get(index).getComments().get(j).getAuthor().getUsername());
                commentorusername.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                Label comment = new Label(Timeline.allposts.get(index).getComments().get(j).getContent());
                comment.setFont(Font.font("Arial", 14));
                postComments.getChildren().addAll(commentorusername, comment);
            }

            Button addcommButton = new Button("Comment");
            addcommButton.setStyle("-fx-background-color: #000000; -fx-text-fill: white; -fx-font-weight: bold;");
            addcommButton.setOnAction(e->{
                if (commenTextField.getText().isEmpty()){
                }
                else{
                    Timeline.allposts.get(index).addcomment(commenTextField.getText());
                    numberofcomments.setText(""+ (Timeline.allposts.get(index).getCommentCount())+ " Comments");
                    Label commentorusername = new Label(Login.getCurrentUser().getUsername());
                    commentorusername.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                    Label comment = new Label(commenTextField.getText());
                    comment.setFont(Font.font("Arial", 14));
                    postComments.getChildren().addAll(commentorusername, comment);
                    commenTextField.clear();
                }
            });
            HBox commentssection = new HBox(20,addcommButton,numberofcomments);

            VBox datasection = new VBox(10, nameTime,captionLabel,likessection,commenTextField,commentssection);

            ScrollPane scrollComments = new ScrollPane();
            scrollComments.setMaxHeight(200);
            scrollComments.setStyle("-fx-background-color: #FFFFFF");
            scrollComments.setPrefWidth(150);

            if (Timeline.allposts.get(index).getCommentCount() >0) {
                scrollComments.setContent(postComments);
                HBox Npost = new HBox(15,post, datasection,scrollComments);
                posts.getChildren().add(Npost);
            }
            else{
                HBox Npost = new HBox(15,post, datasection);
                posts.getChildren().add(Npost);
            }


        }

        scrollPosts.setContent(posts);
    }

    @FXML
    public void handleProfile() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfilePage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("HIVE");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) profileLink.getScene().getWindow();
            currentStage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleAddPost() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPostPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("HIVE");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleFollow(){
        if (Main.getProfileUsingUsername(usernameField.getText()) != null){
            if (Login.getCurrentUser().getFollowing().contains(Main.getProfileUsingUsername(usernameField.getText()))){
                userNotFoundLabel.setText("Already Followed");
            }
            else{
                Login.getCurrentUser().send_follow(usernameField.getText().toLowerCase());
                userNotFoundLabel.setText("");
            }
        }
        else{
            userNotFoundLabel.setText("User not found");
        }
    }

    @FXML
    public void handleRefresh(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Timeline.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("HIVE");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) refreshLink.getScene().getWindow();
            currentStage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleFollowing(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("followers.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("HIVE");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
