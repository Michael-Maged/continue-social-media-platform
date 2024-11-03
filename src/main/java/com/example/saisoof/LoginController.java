package com.example.saisoof;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.event.ActionEvent;

public class LoginController {

    @FXML
    private ImageView logo;

    @FXML
    private Label platformName;

    @FXML
    private ImageView profileIcon;

    @FXML
    private TextField usernameField;

    @FXML
    private Label logErrorLabel;

    @FXML
    private Label logLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private void handleLogin(ActionEvent event) {
        if(Login.loginUser(usernameField.getText().toLowerCase(), passwordField.getText()) != null){
            Login.setCurrentUser(Login.loginUser(usernameField.getText().toLowerCase(), passwordField.getText()));
            Main.initializeUserFollowers(Login.getCurrentUser());
            Main.initializeUserPosts(Login.getCurrentUser());
            Timeline.loadAllPosts(Login.getCurrentUser());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfilePage.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("HIVE");
                stage.setScene(new Scene(root));
                stage.show();

                Stage currentStage = (Stage) loginButton.getScene().getWindow();
                currentStage.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            logErrorLabel.setText("Invalid info");
        }
    }

    @FXML
    private void handleForgotPass() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ForgotPassPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("HIVE");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) forgotPasswordLink.getScene().getWindow();
            currentStage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegistrationPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle("HIVE");
            stage.setScene(new Scene(root));
            stage.show();

            Stage currentStage = (Stage) registerLink.getScene().getWindow();
            currentStage.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
