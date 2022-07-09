package com.example.restaurantmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import static com.example.restaurantmanagement.BillEditController.currentBillID;

public class BillEditController1 {

    @FXML
    private TextField idField;

    @FXML
    private TextField amountField;

    @FXML
    private Button closebtn;

    @FXML
    private Button confirm;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField priceField;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private StackPane rootPane;

    @FXML
    private TextField vatField;

    @FXML
    void CloseBtn(ActionEvent event) {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleConfirm(ActionEvent event) {
        Connection connection = DBConnection.getConnections();
        try {
            if (!connection.isClosed()){
                String sql = "UPDATE bill SET DATE = ?, AMOUNT = ?, VAT = ?, PRICE = ?, TOTALPRICE = ? WHERE ID=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setDate(1, Date.valueOf(datePicker.getValue()));
                statement.setString(2, amountField.getText());
                statement.setDouble(3, Double.parseDouble(vatField.getText()));
                statement.setDouble(4, Double.parseDouble(priceField.getText()));
                statement.setDouble(5, ((Double.parseDouble(vatField.getText()))*(Double.parseDouble(priceField.getText())))+Double.parseDouble(priceField.getText()));
                statement.setString(6, currentBillID);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBConnection.closeConnections();
        }
    }

    public void setBillInfo(BillModel bllTable) {
        idField.setText(bllTable.getID());
        idField.setDisable(true);
        datePicker.setValue(LocalDate.parse(bllTable.getDate()));
        amountField.setText(bllTable.getAmount());
        vatField.setText(bllTable.getVAT().toString());
        priceField.setText(bllTable.getPrice().toString());
    }
}
