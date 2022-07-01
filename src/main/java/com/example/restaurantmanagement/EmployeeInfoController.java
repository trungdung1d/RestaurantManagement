package com.example.restaurantmanagement;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class EmployeeInfoController implements Initializable {

    public static String currentID;

    @FXML
    private JFXComboBox jobCbox;

    @FXML
    private JFXComboBox statusCbox;

    @FXML
    private TextArea employeeAddressField;

    @FXML
    private Button employeeConfirm;

    @FXML
    private DatePicker employeeDatePicker;

    @FXML
    private TextField employeeIDField;

    @FXML
    private TextField employeeNameField;

    @FXML
    private TextField employeePhoneField;

    @FXML
    private TextField employeeSalField;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private StackPane rootPane;

    public static LocalDate day;
    private String[] jobStats ={"Chef", "Waiters", "Stocker"};
    private String[] statusStats = {"Leave", "Work"};

    @FXML
    Button closebtn;

    @FXML
    void CloseBtn(ActionEvent event){
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void employeeConfirmBtn(ActionEvent event) throws SQLException{
        Connection connection = DBConnection.getConnections();
        String Name = employeeNameField.getText();
        String ID = employeeIDField.getText();
        currentID = ID;
        Double Salary = Double.parseDouble(employeeSalField.getText());
        day = employeeDatePicker.getValue();
        String Phone = employeePhoneField.getText();
        String Address = employeeAddressField.getText();
        String Job = jobCbox.getValue()+"";
        String Status = statusCbox.getValue()+"";
        if (Name.isEmpty() || ID.isEmpty() || day.equals("null") || Phone.isEmpty() || Address.isEmpty() || Salary.equals("null") || Job.equals("null") || Status.equals("null")){
            Main.showJFXAlert(rootPane, rootAnchorPane, "warning", "Warning!", "Text field can't be empty!", JFXDialog.DialogTransition.CENTER);
        } else {
            String sql = "INSERT INTO staff (ID, NAME, DATE, PHONE, ADDRESS, SALARY, JOB, STATUS) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, ID);
            preparedStatement.setString(2, Name);
            preparedStatement.setDate(3, Date.valueOf(day));
            preparedStatement.setString(4, Phone);
            preparedStatement.setString(5, Address);
            preparedStatement.setDouble(6, Salary);
            preparedStatement.setString(7,Job);
            preparedStatement.setString(8,Status);

            try {
                preparedStatement.execute();
                Main.showAlert(Alert.AlertType.INFORMATION, "Successful", "Employee created!");
            } catch (SQLException e) {
                Main.showAlert(Alert.AlertType.ERROR, "Error", "Account already exists with this ID!");
            } finally {
                DBConnection.closeConnections();
            }
        }
    }

    public EmployeeManagementModel empl (){
        EmployeeManagementModel emp = new EmployeeManagementModel(employeeIDField.getText(), employeeNameField.getText(),
                employeeDatePicker.getValue()+"",
                employeeAddressField.getText(),
                employeePhoneField.getText(),
                Double.parseDouble(employeeSalField.getText()),
            jobCbox.getValue()+"",
            statusCbox.getValue()+"");
        return emp;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jobCbox.getItems().addAll(jobStats);
        statusCbox.getItems().addAll(statusStats);
    }


}