package com.example.restaurantmanagement;

import com.jfoenix.controls.JFXComboBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import static com.example.restaurantmanagement.EmployeeInfoController.currentID;

public class EmployeeInfoEditController implements Initializable {

    @FXML
    private Button closebtn;

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
    private JFXComboBox jobCbox;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXComboBox statusCbox;

    private String[] jobStats ={"Chef", "Waiters", "Stocker"};
    private String[] statusStats = {"Leave", "Work"};

    @FXML
    void CloseBtn(ActionEvent event) {
        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void employeeConfirmBtn(ActionEvent event) {
        Connection connection = DBConnection.getConnections();
        try {
            if (!connection.isClosed()){
                String sql = "UPDATE staff SET NAME = ?, DATE = ?, ADDRESS = ?, PHONE = ?, SALARY = ?, JOB = ?, STATUS = ? WHERE ID = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, employeeNameField.getText());
                statement.setDate(2, Date.valueOf(employeeDatePicker.getValue()));
                statement.setString(3, employeeAddressField.getText());
                statement.setString(4, employeePhoneField.getText());
                statement.setDouble(5, Double.parseDouble(employeeSalField.getText()));
                statement.setString(6,jobCbox.getValue()+"");
                statement.setString(7, statusCbox.getValue()+"");
                statement.setString(8, currentID);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBConnection.closeConnections();
        }

        Stage stage = (Stage) closebtn.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        jobCbox.getItems().addAll(jobStats);
        statusCbox.getItems().addAll(statusStats);
    }

    public void setEmployeeInfo(EmployeeManagementModel employeeInfoTable){
        employeeNameField.setText(employeeInfoTable.getName());
        employeeIDField.setText(employeeInfoTable.getID());
        employeeIDField.setDisable(true);
        employeeDatePicker.setValue(LocalDate.parse(employeeInfoTable.getDate()));
        employeePhoneField.setText(employeeInfoTable.getPhone());
        employeeSalField.setText(employeeInfoTable.getSalary().toString());
        employeeAddressField.setText(employeeInfoTable.getAddress());
    }

    public EmployeeManagementModel empl (){
        EmployeeManagementModel emp = new EmployeeManagementModel(employeeNameField.getText(),
                employeeIDField.getText(),
                employeeDatePicker.getValue()+"",
                employeePhoneField.getText(),
                employeeAddressField.getText(), Double.parseDouble(employeeSalField.getText()),
                jobCbox.getValue()+"",
                statusCbox.getValue()+"");
        return emp;
    }
}
