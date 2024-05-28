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
    

    public void setNo(String text) {
        no.setText(text);
    }

    public void setUsername(String text) {
        username.setText(text);
    }

    public void setName(String text) {
        name.setText(text);
    }

    public void setMembership(String text) {
        membership.setText(text);
    }

    public void setRole(String text) {
        role.setText(text);
    }

}


