package org.example.membershipapp.manager;

import java.io.*;

public class SessionManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SESSION_FILE = "session.ser";

    private static volatile SessionManager instance;
    private boolean isLoggedIn;
    private int userId;
    private String userName;

    // Private constructor to prevent instantiation from outside
    private SessionManager() {
        isLoggedIn = false;
    }

    // Static method to get the singleton instance
    public static SessionManager getInstance() {
        if (instance == null) {
            synchronized (SessionManager.class) {
                if (instance == null) {
                    instance = new SessionManager();
                    instance.createSessionFile();
                }
            }
        }
        return instance;
    }

    // Method to check if the session file doesn't exist
    public void createSessionFile() {
        File file = new File(SESSION_FILE);
        if (!file.exists()) {
            saveSession();
        } else {
            loadSession();
        }
    }

    private void loadSession() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SESSION_FILE))) {
            SessionManager sessionManager = (SessionManager) ois.readObject();
            this.isLoggedIn = sessionManager.isLoggedIn;
            this.userId = sessionManager.userId;
            this.userName = sessionManager.userName;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading session: " + e.getMessage());
        }
    }

    private void saveSession() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SESSION_FILE))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to check if user is logged in
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    // Method to get the user ID
    public int getUserId() {
        return userId;
    }
    
    public String getUserName() {
        return userName;
    }

    public void login(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        isLoggedIn = true;
        saveSession();
    }

    // Method to simulate logout
    public void logout() {
        isLoggedIn = false;
        saveSession();
    }
}
