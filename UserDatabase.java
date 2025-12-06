package manager;

import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDatabase {
    private List<User> users;
    private static final String FILENAME = "user_database.txt";

    public UserDatabase() {
        users = new ArrayList<>();
        loadUsersFromFile(); // Load users from file if exists
    }

    public void addUser(String username, String password) {
        users.add(new User(username, password));
        saveUsersToFile(); // Save users to file after adding new user
    }

    public boolean isValidLogin(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    String password = parts[1];
                    users.add(new User(username, password));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading user database from file: " + e.getMessage());
        }
    }

    private void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving user database to file: " + e.getMessage());
        }
    }

    public boolean userExists(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
}
