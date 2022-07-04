package com.example.demo11;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import com.jfoenix.controls.JFXDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManagerCustomer implements Initializable {
        @FXML
        private TableView table;
        @FXML
        private TextField search;
        @FXML
        private TableColumn<Customer, Integer> id;
        @FXML
        private TableColumn<Customer, String> name;
        @FXML
        private TableColumn<Customer, Integer> IdentifyCard;
        @FXML
        private TableColumn<Customer, Integer> point;
        @FXML
        private TableColumn<Customer, String> phoneNumber;
        @FXML
        private TableColumn<Customer, String> address;
        @FXML
        private TextField nameText;
        @FXML
        private TextField idText;
        @FXML
        private TextField phoneNumberText;
        @FXML
        private TextField addressText;
        @FXML
        private TextField identyCardText;
        @FXML
        private TextField pointText;

        public ObservableList<Customer> customers = FXCollections.observableArrayList(
                new Customer(23,"Duy",100,1234534123,"Ha Noi","092312341234"),
                 new Customer(63,"Minh",10,1999999999,"Ha Noi","082341334"),
                 new Customer(45,"Chien",160,45243423,"Ha Noi","0239123112"),
                 new Customer(73,"Khanh",190,333213333,"Ha Noi","084124124"),
                 new Customer(32,"Tu",120,234534123,"Ha Noi","0145138991")
        );

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
            table.setEditable(true);
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        point.setCellValueFactory(new PropertyValueFactory<>("point"));
        IdentifyCard.setCellValueFactory(new PropertyValueFactory<>("identyCard"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        table.setItems(customers);
        FindCustomer();

    }

        public void AddCustomer(ActionEvent e) throws IOException {
            if(nameText.getText().isEmpty() || addressText.getText().isEmpty() || idText.getText().isEmpty() || pointText.getText().isEmpty() || phoneNumberText.getText().isEmpty() || identyCardText.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Information Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Please Enter Into Text Fields");
                alert.show();
            }else {
                Customer customer = new Customer();
                customer.setName(nameText.getText());
                customer.setAddress(addressText.getText());
                customer.setId(Integer.parseInt(idText.getText()));
                customer.setPoint(Integer.parseInt(pointText.getText()));
                customer.setPhoneNumber(phoneNumberText.getText());
                customer.setIdentyCard(Integer.parseInt(identyCardText.getText()));
                customers.add(customer);
            }

    }
        public void FindCustomer(){
            FilteredList<Customer > filteredData = new FilteredList(customers,b->true);
            search.textProperty().addListener((observableValue, oldValue, newValue) ->{
                filteredData.setPredicate(Customer ->{
                    if(newValue.isEmpty() || newValue.isBlank()|| newValue == null){
                        return true;
                    }
                    String searchKey = newValue.toLowerCase();
                    if(Customer.getName().toLowerCase().indexOf(searchKey) > -1){
                        return true;
                    } else if(Customer.getAddress().toLowerCase().indexOf(searchKey) >-1 ){
                        return true;
                    } else if(Customer.getPhoneNumber().toLowerCase().indexOf(searchKey) >-1) {
                        return true;
                    } else if(String.valueOf(Customer.getId()).toLowerCase().indexOf(searchKey) >-1) {
                        return true;
                    } else if(String.valueOf(Customer.getIdentyCard()).toLowerCase().indexOf(searchKey)>-1){
                        return true;
                    } else if(String.valueOf(Customer.getPoint()).toLowerCase().indexOf(searchKey)>-1){
                        return true;
                    } else{
                        return false;
                    }
                }
                );
                SortedList<Customer> sortedData = new SortedList<>(filteredData);
                sortedData.comparatorProperty().bind(table.comparatorProperty());
                table.setItems(sortedData);
            });
        }




        public void Delete(ActionEvent e) {
        Customer selected = (Customer) table.getSelectionModel().getSelectedItem();
        customers.remove(selected);


    }
}