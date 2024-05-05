/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.membershipapp;

/**
 *
 * @author SanjayaCF
 */
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class membershipItemsController {

    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private Label expired;

    @FXML
    private Label category;

    @FXML
    private Label benefit;

    @FXML
    private Label payType;

    @FXML
    private Label interval;

    public void setName(String text) {
        name.setText(text);
    }

    public void setPrice(String text) {
        price.setText(text);
    }

    public void setExpired(String text) {
        expired.setText(text);
    }

    public void setCategory(String text) {
        category.setText(text);
    }

    public void setBenefit(String text) {
        benefit.setText(text);
    }

    public void setPayType(String text) {
        payType.setText(text);
    }

    public void setInterval(String text) {
        interval.setText(text);
    }
}


