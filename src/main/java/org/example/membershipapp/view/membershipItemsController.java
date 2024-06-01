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

public class membershipItemsController {
    private int chosenId;

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
    
    public void setChosenIdToStatic() {
        menuController.choosenMembershipId = chosenId;
        System.out.println(chosenId);
    }
    
    public Label getName() {
        return name;
    }

    public Label getPrice() {
        return price;
    }

    public Label getExpired() {
        return expired;
    }

    public Label getCategory() {
        return category;
    }

    public Label getBenefit() {
        return benefit;
    }

    public Label getPayType() {
        return payType;
    }

    public Label getInterval() {
        return interval;
    }
    
    
    
    public void setChosenId(int chosedId) {
        chosenId = chosedId;
    }

    public void setName(String text) {
        getName().setText(text);
    }

    public void setPrice(String text) {
        getPrice().setText(text);
    }

    public void setExpired(String text) {
        getExpired().setText(text);
    }

    public void setCategory(String text) {
        getCategory().setText(text);
    }

    public void setBenefit(String text) {
        getBenefit().setText(text);
    }

    public void setPayType(String text) {
        getPayType().setText(text);
    }

    public void setInterval(String text) {
        getInterval().setText(text);
    }
}


