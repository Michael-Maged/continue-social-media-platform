package com.example.saisoof;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPasswordController {
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
    private Label resetPassLabel;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Button resetButton;

    @FXML
    private Label resetPassErrorLabel;

    @FXML
    public void handleForgotPass(){
        if(ForgotPassword.forgotpass(usernameField.getText().toLowerCase(), newPasswordField.getText()) && UserRegistration.passLen(newPasswordField.getText())){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPagefx.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setResizable(false);
                stage.setTitle("HIVE");
                stage.setScene(new Scene(root));
                stage.show();

                Stage currentStage = (Stage) resetButton.getScene().getWindow();
                currentStage.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            resetPassErrorLabel.setText("Invalid info");
        }
    }
}
