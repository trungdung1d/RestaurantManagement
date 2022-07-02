package com.example.restaurantmanagement;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DishInfoController implements Initializable {

    @FXML
    private Button confirm;

    @FXML
    private TextField nameField;

    @FXML
    private TextField amountField;

    @FXML
    private TextField vatField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField totalPriceField;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private StackPane rootPane;

    @FXML
    private Button closebtn;

    private int id = -1;

    @FXML
    void CloseBtn(ActionEvent event) {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleConfirm(ActionEvent event) throws SQLException {
        Connection connection = DBConnection.getConnections();

        String Name = nameField.getText();
        Integer Amount;
        Double VAT, Price, TotalPrice;
        try {
            Amount = Integer.parseInt(amountField.getText());
            VAT = Double.parseDouble(vatField.getText());
            Price = Double.parseDouble(priceField.getText());
            TotalPrice = Double.parseDouble(totalPriceField.getText());
        } catch (Exception exc) {
            Main.showJFXAlert(rootPane, rootAnchorPane, "warning", "Warning!", "Text field can't be empty!", JFXDialog.DialogTransition.CENTER);
            return;
        }

        if (Name.isEmpty()) {
            Main.showJFXAlert(rootPane, rootAnchorPane, "warning", "Warning!", "Text field can't be empty!", JFXDialog.DialogTransition.CENTER);
        } else if (id == -1) {
            String sql = "INSERT INTO dish (NAME, AMOUNT, VAT, PRICE, TOTALPRICE) VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Name);
            preparedStatement.setInt(2, Amount);
            preparedStatement.setDouble(3, VAT);
            preparedStatement.setDouble(4, Price);
            preparedStatement.setDouble(5, TotalPrice);

            try {
                preparedStatement.execute();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
                Main.showAlert(Alert.AlertType.INFORMATION, "Successful", "Dish created!");
            } catch (Exception e) {
                Main.showAlert(Alert.AlertType.ERROR, "Error", "Account already exists with this ID!");
            } finally {
                DBConnection.closeConnections();
            }

            Stage stage = (Stage) closebtn.getScene().getWindow();
            stage.close();
        } else {
            try {
                if (!connection.isClosed()) {
                    String sql = "UPDATE dish SET NAME = ?, AMOUNT = ?, VAT = ?, PRICE = ?, TOTALPRICE = ? WHERE ID = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, Name);
                    statement.setInt(2, Amount);
                    statement.setDouble(3, VAT);
                    statement.setDouble(4, Price);
                    statement.setDouble(5, TotalPrice);
                    statement.setInt(6, id);
                    statement.executeUpdate();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                DBConnection.closeConnections();
            }

            Stage stage = (Stage) closebtn.getScene().getWindow();
            stage.close();
        }
    }

    public void setDish(DishModel dishModel) {
        id = Integer.parseInt(dishModel.ID);
        nameField.setText(dishModel.getName());
        amountField.setText(dishModel.getAmount());
        vatField.setText(dishModel.getVAT().toString());
        priceField.setText(dishModel.getPrice().toString());
        totalPriceField.setText(dishModel.getTotalPrice().toString());
    }

    public DishModel dish() {
        if (id <= 0) {
            return null;
        }

        return new DishModel(String.valueOf(id),
                nameField.getText(),
                amountField.getText(),
                Double.parseDouble(vatField.getText()),
                Double.parseDouble(priceField.getText()),
                Double.parseDouble(totalPriceField.getText()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}
}
