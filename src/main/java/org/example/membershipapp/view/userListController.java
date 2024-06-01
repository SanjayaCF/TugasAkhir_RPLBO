/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.membershipapp.view;

/**
 *
 * @author SanjayaCF
 */
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class userListController {

    @FXML
    private Label name;

    @FXML
    private Label no;

    @FXML
    private Label username;

    @FXML
    private Label membership;

    @FXML
    private Label role;
    
    
    public Label getName() {
        return name;
    }

    public Label getNo() {
        return no;
    }

    public Label getUsername() {
        return username;
    }

    public Label getMembership() {
        return membership;
    }

    public Label getRole() {
        return role;
    }

    public void setNo(String text) {
        getNo().setText(text);
    }

    public void setUsername(String text) {
        getUsername().setText(text);
    }

    public void setName(String text) {
        getName().setText(text);
    }

    public void setMembership(String text) {
        getMembership().setText(text);
    }

    public void setRole(String text) {
        getRole().setText(text);
    }

}


