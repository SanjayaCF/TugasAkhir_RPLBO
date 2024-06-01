package org.example.membershipapp.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.example.membershipapp.manager.SessionManager;
import org.example.membershipapp.manager.databaseConnect;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.lang.reflect.Member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class menuController extends switchScenesController {

    private static Connection connection;

    public menuController() {
        connection = databaseConnect.getConnection();
    }
    

    public static int userID = SessionManager.getInstance().getUserId();
    public static String userName = SessionManager.getInstance().getUserName();
    public static int choosenMembershipId;

    @FXML
    private VBox scrollPaneContent;
    
    @FXML
    private Button btnAdmin;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private Label welcomeNama;
    @FXML
    private ComboBox<MembershipCategory> comboboxCategories;
    @FXML
    private ComboBox<String> comboboxTypes;

    ObservableList<String> types; 
    ObservableList<MembershipCategory> categories = FXCollections.observableArrayList();
    @FXML DatePicker tanggal;

    public void initialize() throws SQLException, IOException {
        loadMemberships("");
        userInformation();
        categories = FXCollections.observableArrayList(this.getMembershipCategories());
        comboboxCategories.setItems(categories);
        comboboxCategories.setOnAction(e -> {
            MembershipCategory category = comboboxCategories.getSelectionModel().getSelectedItem();
            try {
                loadMembershipsOnCategory(category);
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        });

        comboboxCategories.setCellFactory(e -> new ListCell<MembershipCategory>() {
            protected void updateItem(MembershipCategory membershipCategory, boolean empty) {
                super.updateItem(membershipCategory, empty);
                if (membershipCategory != null && !empty) {
                    setText(membershipCategory.getName());
                } else {
                    setText(null);
                }
            }
        });

        comboboxCategories.setButtonCell(new ListCell<MembershipCategory>() {
            protected void updateItem(MembershipCategory membershipCategory, boolean empty) {
                super.updateItem(membershipCategory, empty);
                if (membershipCategory != null && !empty) {
                    setText(membershipCategory.getName());
                } else {
                    setText(null);
                }
            }
        });
        String[] types = {"Yearly", "Monthly"};
        this.types = FXCollections.observableArrayList(new ArrayList<String>(Arrays.asList(types)));
        comboboxTypes.setItems(this.types);
        comboboxTypes.setOnAction(e -> {
            String type = comboboxTypes.getSelectionModel().getSelectedItem();
            try {
                loadMembershipsOnType(type);
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        });

        tanggal.valueProperty().addListener((o, old, baru) -> {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String tgl = baru.format(formatter);
                System.out.println(tgl);
                loadMembershipsOnDate(tgl);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                loadMemberships(newValue);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(menuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    private void userInformation() throws SQLException {
        welcomeNama.setText("Welecome, "+ menuController.userName);
        final String SQL = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL);) {
                ps.setInt(1, menuController.userID);
                ResultSet rs = ps.executeQuery();
                
                btnAdmin.setVisible(rs.getBoolean("privilege"));
                               
        }
    }

    private void loadMembershipsOnType(String type) throws SQLException {
        try {
            scrollPaneContent.getChildren().clear();
            final String SQL = "SELECT * FROM users_membership WHERE userID = ? AND paymentInterval = ?";
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, menuController.userID);
                ps.setString(2,  type);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("userMembershipItems.fxml"));
                        AnchorPane userBox = loader.load();      
    
                        membershipItemsController itemController = loader.getController();
                        itemController.setName(rs.getString("membershipName"));
                        String currency = rs.getString("currency");
                        itemController.setPrice(currency + " " + rs.getString("price"));
                        itemController.setExpired("Expired " + rs.getString("dateEnd"));
                        itemController.setCategory(rs.getString("category"));
                        itemController.setBenefit(rs.getString("benefit"));
                        itemController.setPayType(rs.getBoolean("autoPayment") ? "Auto Paid" : "Paid");
                        itemController.setInterval(rs.getString("paymentInterval"));
    
                        int membershipID = rs.getInt("membershipID");
    
                        userBox.setOnMouseEntered(event -> userBox.setStyle("-fx-background-color: #ddddff"));
                        userBox.setOnMouseExited(event -> userBox.setStyle("-fx-background-color: white"));
                        userBox.setOnMouseClicked(event -> {
                            try {
                                menuController.choosenMembershipId = membershipID;
                                switchToDetailMembership();
                            } catch (IOException ex) {
                                Logger.getLogger(menuController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
    
                        scrollPaneContent.getChildren().add(userBox);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadMembershipsOnDate(String exp) throws SQLException {
        try {
            scrollPaneContent.getChildren().clear();
            final String SQL = "SELECT * FROM users_membership WHERE userID = ? AND dateEnd = ?";
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, menuController.userID);
                ps.setString(2,  exp);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("userMembershipItems.fxml"));
                        AnchorPane userBox = loader.load();      
    
                        membershipItemsController itemController = loader.getController();
                        itemController.setName(rs.getString("membershipName"));
                        String currency = rs.getString("currency");
                        itemController.setPrice(currency + " " + rs.getString("price"));
                        itemController.setExpired("Expired " + rs.getString("dateEnd"));
                        itemController.setCategory(rs.getString("category"));
                        itemController.setBenefit(rs.getString("benefit"));
                        itemController.setPayType(rs.getBoolean("autoPayment") ? "Auto Paid" : "Paid");
                        itemController.setInterval(rs.getString("paymentInterval"));
    
                        int membershipID = rs.getInt("membershipID");
    
                        userBox.setOnMouseEntered(event -> userBox.setStyle("-fx-background-color: #ddddff"));
                        userBox.setOnMouseExited(event -> userBox.setStyle("-fx-background-color: white"));
                        userBox.setOnMouseClicked(event -> {
                            try {
                                menuController.choosenMembershipId = membershipID;
                                switchToDetailMembership();
                            } catch (IOException ex) {
                                Logger.getLogger(menuController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
    
                        scrollPaneContent.getChildren().add(userBox);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<MembershipCategory> getMembershipCategories() {
        ArrayList<MembershipCategory> membershipCategories = new ArrayList<>();
        String query = "SELECT * FROM memberships_category ;";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("membershipCategoryId");
                String name = rs.getString("membershipCategory");
                MembershipCategory membershipCategory = new MembershipCategory(id, name);
                membershipCategories.add(membershipCategory);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return membershipCategories;
    }

    private void loadMembershipsOnCategory(MembershipCategory category) throws SQLException, IOException {
        try {
            scrollPaneContent.getChildren().clear();
            final String SQL = " SELECT * FROM users_membership inner join memberships \r\n" + //
                                " on memberships.membershipId = users_membership.membershipID \r\n" + //
                                " inner join memberships_category \r\n" + //
                                " on memberships_category.membershipCategoryId = memberships.membershipCategoryId \r\n" + //
                                " WHERE userID = ? AND memberships_category.membershipCategoryId = ?";
            try (PreparedStatement ps = connection.prepareStatement(SQL)) {
                ps.setInt(1, menuController.userID);
                ps.setInt(2,   category.getId() );
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("userMembershipItems.fxml"));
                        AnchorPane userBox = loader.load();

                        membershipItemsController itemController = loader.getController();
                        itemController.setName(rs.getString("membershipName"));
                        String currency = rs.getString("currency");
                        itemController.setPrice(currency + " " + rs.getString("price"));
                        itemController.setExpired("Expired " + rs.getString("dateEnd"));
                        itemController.setCategory(rs.getString("category"));
                        itemController.setBenefit(rs.getString("benefit"));
                        itemController.setPayType(rs.getBoolean("autoPayment") ? "Auto Paid" : "Paid");
                        itemController.setInterval(rs.getString("paymentInterval"));

                        int membershipID = rs.getInt("membershipID");

                        userBox.setOnMouseEntered(event -> userBox.setStyle("-fx-background-color: #ddddff"));
                        userBox.setOnMouseExited(event -> userBox.setStyle("-fx-background-color: white"));
                        userBox.setOnMouseClicked(event -> {
                            try {
                                menuController.choosenMembershipId = membershipID;
                                switchToDetailMembership();
                            } catch (IOException ex) {
                                Logger.getLogger(menuController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });

                        scrollPaneContent.getChildren().add(userBox);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void loadMemberships(String filter) throws SQLException {
//        try {
//            
//
//            final String SQL = "SELECT * FROM users_membership WHERE userID = ? AND membershipName LIKE ?";
//            try (PreparedStatement ps = connection.prepareStatement(SQL);) {
//                ps.setInt(1, menuController.userID);
//                ps.setString(2, "%" + filter + "%");
//                ResultSet rs = ps.executeQuery();
//                while (rs.next()) {
//                    FXMLLoader loader = new FXMLLoader(getClass().getResource("userMembershipItems.fxml"));
//                    AnchorPane membershipItem = loader.load();
//                    membershipItemsController itemController = loader.getController();
//
//                    itemController.setName(rs.getString("membershipName"));
//                    String currency = rs.getString("currency");
//                    itemController.setPrice(currency + " " + rs.getString("price"));
//                    itemController.setExpired("Expired " + rs.getString("dateEnd"));
//                    itemController.setCategory(rs.getString("category"));
//                    itemController.setBenefit(rs.getString("benefit"));
//                    itemController.setPayType(rs.getBoolean("autoPayment") ? "Auto Paid" : "Paid");
//                    itemController.setInterval(rs.getString("paymentInterval"));
//                    scrollPaneContent.getChildren().add(membershipItem);
//
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    
private void loadMemberships(String filter) throws SQLException, IOException {
    try {
        scrollPaneContent.getChildren().clear();
        final String SQL = "SELECT * FROM users_membership WHERE userID = ? AND membershipName LIKE ?";
        try (PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setInt(1, menuController.userID);
            ps.setString(2, "%" + filter + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("userMembershipItems.fxml"));
                    AnchorPane userBox = loader.load();      

                    membershipItemsController itemController = loader.getController();
                    itemController.setChosenId(rs.getInt("membershipId"));
                    itemController.setName(rs.getString("membershipName"));
                    String currency = rs.getString("currency");
                    itemController.setPrice(currency + " " + rs.getString("price"));
                    itemController.setExpired("Expired " + rs.getString("dateEnd"));
                    itemController.setCategory(rs.getString("category"));
                    itemController.setBenefit(rs.getString("benefit"));
                    itemController.setPayType(rs.getBoolean("autoPayment") ? "Auto Paid" : "Paid");
                    itemController.setInterval(rs.getString("paymentInterval"));


                    userBox.setOnMouseEntered(event -> userBox.setStyle("-fx-background-color: #ddddff"));
                    userBox.setOnMouseExited(event -> userBox.setStyle("-fx-background-color: white"));
                    userBox.setOnMouseClicked(event -> {
                        try {
                            itemController.setChosenIdToStatic();
                            switchToDetailMembership();
                        } catch (IOException ex) {
                            Logger.getLogger(menuController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    scrollPaneContent.getChildren().add(userBox);
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}

protected void switchToDetailMembership() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("detailInformation.fxml"));
    Pane newMembershipPane = loader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(newMembershipPane));
    stage.setTitle("Membership Detail");
    stage.show();
}

    
    @FXML
    protected void SwitchToAddMembership() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addMembership.fxml"));
        Pane newMembershipPane = loader.load();

        addMembershipController controller = loader.getController();
        controller.setOnMembershipSaved(this::refreshMemberships);
        Stage stage = new Stage();
        stage.setScene(new Scene(newMembershipPane));
        stage.setTitle("Add Membership");
        stage.show();
    }
    
//    protected void switchToDetailMembership() throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("detailInformation.fxml"));
//        Pane newMembershipPane = loader.load();
//
//        addMembershipController controller = loader.getController();
//        controller.setOnMembershipSaved(this::refreshMemberships);
//        Stage stage = new Stage();
//        stage.setScene(new Scene(newMembershipPane));
//        stage.setTitle("Membership Detail");
//        stage.show();
//    }
    
    public void refreshMemberships() {
        try {
            loadMemberships("");
        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void SwitchToPayment(ActionEvent event) throws IOException {
        switchToPayment(event);
    }

    @FXML
    protected void SwitchToMenuPage(ActionEvent event) throws IOException {
        switchToMenuPage(event);
    }

    @FXML
    protected void SwitchToAboutUs(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("aboutUs.fxml"));
        Pane newPane = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(newPane));
        stage.setTitle("About Us");
        stage.show();
    }

    @FXML
    protected void SwitchToPrivacyPolicy(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("privacyPolicy.fxml"));
        Pane newPane = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(newPane));
        stage.setTitle("Privacy & Policy");
        stage.show();
    }

    @FXML
    protected void SwitchToLogOut(ActionEvent event) throws IOException {
        switchToLogOut(event);
    }

    @FXML
    protected void SwitchToAccount(ActionEvent event) throws IOException {
        switchToAccount(event);
    }
    
    @FXML
    protected void SwitchToAdminPage(ActionEvent event) throws IOException {
        switchToAdminPage(event);
    }
    
}
