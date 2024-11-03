package com.example.saisoof;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.sql.*;
import javafx.scene.control.*;

public class UserRegistration{

    public static boolean registerUser(String username, String password, String email, String firstN, String lastN){

        try (Connection connection = DriverManager.getConnection(Main.url, Main.dbuser, Main.dbpassword)){
            String sql = "INSERT INTO users (email, username, pass, firstN, lastN) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, firstN);
            preparedStatement.setString(5, lastN);

            try{
                if (preparedStatement.executeUpdate() > 0) {
                    Main.allUsers.add(new UserProfile(username, email, password));
                    return true;
                }
                else{
                    return false;
                }
            }
            catch(SQLIntegrityConstraintViolationException e){
                return false;
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean verifyEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern p = Pattern.compile(emailRegex);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static boolean passLen(String password){
        return password.length() >= 6;
    }
}