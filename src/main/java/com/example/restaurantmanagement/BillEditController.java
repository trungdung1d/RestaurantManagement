package com.example.restaurantmanagement;

import com.jfoenix.controls.JFXDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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

public class BillEditController {

    @FXML
    private TextField amountField;

    @FXML
    private Button closebtn;

    @FXML
    private Button confirm;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField idField;

    @FXML
    private TextField priceField;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private StackPane rootPane;

    @FXML
    private TextField totalPriceField;

    @FXML
    private TextField vatField;

    public static LocalDate day;

    @FXML
    void CloseBtn(ActionEvent event) {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void handleConfirm(ActionEvent event) throws SQLException {
        Connection connection = DBConnection.getConnections();
        day = datePicker.getValue();
        String Amount = amountField.getText();
        Double Vat = Double.parseDouble(vatField.getText());
        Double Price = Double.parseDouble(priceField.getText());
        Double TotalPrice = Double.parseDouble(totalPriceField.getText());
        if (day.equals("null") || Amount.isEmpty() || Vat.equals("null") || Price.equals("null") || TotalPrice.equals("null")){
            Main.showJFXAlert(rootPane, rootAnchorPane, "warning", "Warning!", "Text field can't be empty!", JFXDialog.DialogTransition.CENTER);
        } else {
            String sql = "INSERT INTO bill (DATE, AMOUNT, VAT, PRICE, TOTALPRICE) VALUES(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, Date.valueOf(day));
            preparedStatement.setString(2, Amount);
            preparedStatement.setDouble(3, Vat);
            preparedStatement.setDouble(4, Price);
            preparedStatement.setDouble(5,TotalPrice);


            try {
                preparedStatement.execute();
                Main.showAlert(Alert.AlertType.INFORMATION, "Successful", "Bill created!");
            } catch (SQLException e) {
                Main.showAlert(Alert.AlertType.ERROR, "Error", "ID already exists!");
            } finally {
                DBConnection.closeConnections();
            }
        }
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

    public BillModel billModel (){
        BillModel bll = new BillModel(datePicker.getValue()+"",
                amountField.getText(),
                Double.parseDouble(vatField.getText()),
                Double.parseDouble(priceField.getText()),
                Double.parseDouble(totalPriceField.getText()));
        return bll;
    }
}

