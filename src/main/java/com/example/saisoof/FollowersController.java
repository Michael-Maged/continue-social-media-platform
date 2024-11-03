package com.example.saisoof;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class FollowersController implements Initializable {

    @FXML
    private ScrollPane scrollFollowing;

    VBox allFollowing = new VBox(15);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (UserProfile following : Login.getCurrentUser().getFollowing()){
            Label usernameLabel = new Label(following.getUsername());
            usernameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

            allFollowing.getChildren().add(usernameLabel);
            allFollowing.setAlignment(Pos.CENTER);

        }

        scrollFollowing.setContent(allFollowing);
        scrollFollowing.setFitToWidth(true);
    }
}