package com.example.saisoof;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import java.io.File;

public class ProfilePageController implements Initializable {

    public static GridPane postGrid = new GridPane();
    public static int rowIndex = 0, colIndex = 0;

    @FXML
    private ImageView profilePic;

    @FXML
    private Hyperlink refreshLink;

    @FXML
    private Label usernameLabel;

    @FXML
    private TextArea bio;

    @FXML
    private Button editBio;

    @FXML
    private Hyperlink editPic;

    @FXML
    private Button saveButton;

    @FXML
    private Label postCountLabel;

    @FXML
    private Label followerCountLabel;

    @FXML
    private ScrollPane scrollGrid;

    @FXML
    private Hyperlink timelineLink;

    @FXML
    private Hyperlink addPostLink;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(Login.getCurrentUser().getUsername());
        if (Login.getCurrentUser().getProfilePicture() != null) {
            profilePic.setImage(new Image(Login.getCurrentUser().getProfilePicture()));
        }
        postCountLabel.setText(String.valueOf(Login.getCurrentUser().getPostCount()) + " Posts");
        followerCountLabel.setText(String.valueOf(Login.getCurrentUser().getFollowerCount()) + " Following");
        bio.setText(Login.getCurrentUser().getBio());
        bio.setEditable(false);
        saveButton.setVisible(false);
        scrollGrid.setContent(postGrid);
    }


    @FXML
    public void handleEditPic(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Photo");
        File selectedFile = fileChooser.showOpenDialog(null);
        Login.getCurrentUser().setProfilePicture(selectedFile.toURI().toString());
        profilePic.setImage(new Image(selectedFile.toURI().toString()));
        Login.getCurrentUser().updateDB();
    }

    @FXML
    public void handleEditBio(){
        bio.setEditable(true);
        saveButton.setVisible(true);
    }

    @FXML
    public void handleSaveBio(){
        Login.getCurrentUser().setBio(bio.getText());
        bio.setEditable(false);
        saveButton.setVisible(false);
        Login.getCurrentUser().updateDB();
    }

    @FXML
    public void handleTimeline(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Timeline.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("HIVE");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) timelineLink.getScene().getWindow();
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
    public void handleRefresh(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfilePage.fxml"));
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
