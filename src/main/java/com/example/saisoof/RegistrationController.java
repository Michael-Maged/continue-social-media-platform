package com.example.saisoof;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistrationController {
    @FXML
    private ImageView logo;

    @FXML
    private Label platformName;

    @FXML
    private ImageView profileIcon;

    @FXML
    private TextField usernameField;

    @FXML
    private Label regErrorLabel;

    @FXML
    private Label registerLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    public void handleRegister(){
        if (usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || emailField.getText().isEmpty() || firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()){
            regErrorLabel.setText("Please enter all info");
        }
        else if (usernameField.getText().contains(" ")){
            regErrorLabel.setText("Username cannot contain spaces");
        }
        else if (!UserRegistration.verifyEmail(emailField.getText())){
            regErrorLabel.setText("Invalid email address");
        }
        else if (!UserRegistration.passLen(passwordField.getText())){
            regErrorLabel.setText("Password too short");
        }
        else if (UserRegistration.registerUser(usernameField.getText().toLowerCase(), passwordField.getText(), emailField.getText().toLowerCase(), firstNameField.getText(), lastNameField.getText())){
            Main.selffollow(usernameField.getText().toLowerCase());
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPagefx.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("HIVE");
                stage.setScene(new Scene(root));
                stage.show();

                Stage currentStage = (Stage) registerButton.getScene().getWindow();
                currentStage.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            regErrorLabel.setText("Username taken");
        }

    }

}
