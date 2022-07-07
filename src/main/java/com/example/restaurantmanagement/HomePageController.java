package com.example.restaurantmanagement;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Statement;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private ImageView logout;

    @FXML
    private BorderPane borderpane;

    @FXML
    void createCustomerBill(ActionEvent event) {
        windowLoad("Bill.fxml");
    }

    @FXML
    void createDash(ActionEvent event) {
        windowLoad("Dashboard.fxml");
    }

    @FXML
    void customerBtn(ActionEvent event) {
        windowLoad("ManagerCustomer.fxml");
    }

    @FXML
    void employeeBtn(ActionEvent event) {
        windowLoad("EmplyeeManagement.fxml");
    }

    @FXML
    void createCustomerBill1(ActionEvent event) {
        windowLoad("Dish.fxml");
    }

    @FXML
    void creatStatistic(ActionEvent event) {
        windowLoad("Statistic.fxml");
    }

    @FXML
    void handleLogout(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("Login.fxml"));
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.centerOnScreen();
    }

    @FXML
    void createTable(ActionEvent event) {
        windowLoad("Table1.fxml");
    }

    @FXML
    void warhouseBtn(ActionEvent event) {
        windowLoad("Warehouse.fxml");
    }

    public void windowLoad(String fxml){
        try
        {
            Pane pane = FXMLLoader.load(Main.class.getResource(fxml));
            borderpane.setCenter(pane);
        }
        catch(Exception e)
        {
            System.out.println("Problem : " + e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){

    }
}
