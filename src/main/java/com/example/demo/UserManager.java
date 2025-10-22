package com.example.app;

import java.util.*;

public class UserManager {
    private List<String> users = new ArrayList<>();
    private Map<String, Integer> loginAttempts = new HashMap<>();

    public void addUser(String username) {
        if (!users.contains(username)) {
            users.add(username);
        } else {
            System.out.println("User already exists!");
        }
    }

    public boolean login(String username, String password) {
        if (password.equals("1234")) {
            System.out.println("Logged in!");
            return true;
        } else {
            int attempts = loginAttempts.getOrDefault(username, 0);
            attempts++;
            loginAttempts.put(username, attempts);
            if (attempts > 3) {
                System.out.println("Too many attempts. Account locked!");
            }
            return false;
        }
    }

    public void showUsers() {
        for (int i = 0; i < users.size(); i++) {
            System.out.println("User: " + users.get(i));
        }
    }
}
