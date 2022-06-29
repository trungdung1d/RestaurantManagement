package com.example.restaurantmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ManagerLoginController implements Initializable {

    Main m = new Main();
    @FXML
    private PasswordField passField;

    @FXML
    private TextField userField;

    @FXML
    void ForgetPass(ActionEvent event) throws IOException{
        m.changeScene(event,"ForgotPass.fxml", "Forgot Password");
    }

    @FXML
    void LogIn(ActionEvent event) throws IOException {
        String username = userField.getText();
        String pass = passField.getText();
        try{
            Connection connection = DBConnection.getConnections();
            if(username.isEmpty() || pass.isEmpty()){
                Main.showAlert(Alert.AlertType.WARNING, "Error", "Field Can't be Empty!");
            } else {
                String sql = "SELECT * FROM manager WHERE USERNAME = ? AND PASSWORD = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, pass);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    Main.showAlert(Alert.AlertType.INFORMATION, "Login Success!", "Successfully Logged In!");
                    m.changeScene(event, "Dashboard.fxml", "Admin Dashboard");
                } else {
                    Main.showAlert(Alert.AlertType.ERROR, "Login Failed!", "Incorrect Username or Password!");
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            DBConnection.closeConnections();
        }
    }

    @FXML
    void Register(ActionEvent event) throws IOException{
        m.changeScene(event, "Register.fxml", "Register");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
