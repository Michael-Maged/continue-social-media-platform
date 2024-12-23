package com.example.saisoof;

public class Login {
    private static UserProfile currentUser;

    public static UserProfile loginUser(String username, String password) {
        for (UserProfile user: Main.getAllUsers()){
            if ((user.getUsername().equals(username) || user.getEmail().equals(username) )&& user.getPassword().equals(password)){
                return user;
            }
        }
        return null;
    }

    public static void setCurrentUser(UserProfile user){
        currentUser = user;
    }

    public static UserProfile getCurrentUser(){
        return currentUser;
    }
}
