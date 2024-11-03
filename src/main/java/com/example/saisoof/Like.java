package com.example.saisoof;

public class Like {
    private UserProfile liker;

    public Like(UserProfile liker) {
        this.liker = liker;
    }

    public UserProfile getLiker() {
        return liker;
    }
}