package com.example.saisoof;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.*;
import java.time.LocalDateTime;

public class AddPostController {

    @FXML
    private ImageView postPhoto;

    @FXML
    private Label noPhotoLabel;

    String photoURL;
    int maxNumber;

    @FXML
    private Button addPhotoButton;

    @FXML
    private TextField captionField;

    @FXML
    private Button savePostButton;

    @FXML
    public void handleAddPhoto(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Photo");
        File selectedFile = fileChooser.showOpenDialog(null);
        photoURL = selectedFile.toURI().toString();
        postPhoto.setImage(new Image(photoURL));
    }

    @FXML
    public void handleSavePost(){
        if (photoURL != null){
            LocalDateTime currentDateTime = LocalDateTime.now();
            Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);

            try {
                Connection connection = DriverManager.getConnection(Main.url, Main.dbuser, Main.dbpassword);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT MAX(postid) FROM posts");

                if (resultSet.next()) {
                    maxNumber = resultSet.getInt(1);
                }

                resultSet.close();
                statement.close();
                connection.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }

            Post post = new Post(Login.getCurrentUser(), captionField.getText(), currentTimestamp , photoURL,maxNumber+1);
            Login.getCurrentUser().updatePostDB(post);
            Login.getCurrentUser().addUserPost(post);
            Stage addPostStage = (Stage) savePostButton.getScene().getWindow();
            addPostStage.close();
        }
        else
        {
            noPhotoLabel.setText("Must choose photo");
        }
    }

}
