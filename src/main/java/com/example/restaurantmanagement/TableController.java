package com.example.demo1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TableController implements Initializable {
    @FXML
    private TableView tableView;
    @FXML
    private TextField Id;
    @FXML
    private TextField amount;
    @FXML
    private TextField nameCustomer;
    @FXML
    private TextField totalPrice;
    @FXML
    private TextField date;
    @FXML
    private TableColumn<Table,Integer> idCol;
    @FXML
    private TableColumn<Table,String> nameCol;
    @FXML
    private TableColumn<Table,Integer> amountCol;
    @FXML
    private TableColumn<Table,Double> totalPriceCol;
    @FXML
    private TableColumn<Table,String> dateCol;
    @FXML
    private TextField search;
    @FXML
    private ObservableList<Table> tables = FXCollections.observableArrayList(
            new Table(12, "Duy",12,121.505,"12/12/2022"),
            new Table(213, "Tu",12,123.505,"12/12/2022"),
            new Table(125, "Khanh",12,123.505,"12/12/2022"),
            new Table(742, "Dung",12,123.505,"12/12/2022"),
            new Table(675, "Chien",12,123.505,"12/12/2022"),
            new Table(451, "Minh",12,123.505,"12/12/2022")

    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableView.setEditable(true);
    idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    nameCol.setCellValueFactory(new PropertyValueFactory<>("nameCustomer"));
    amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
    totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
    dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    tableView.setItems(tables);
    FindCustomer();
    }
    public void FindCustomer(){
        FilteredList<Table> filteredData = new FilteredList(tables, b->true);
        search.textProperty().addListener((observableValue, oldValue, newValue) ->{
            filteredData.setPredicate(Table ->{
                        if(newValue.isEmpty() || newValue.isBlank()|| newValue == null){
                            return true;
                        }
                        String searchKey = newValue.toLowerCase();
                        if(String.valueOf(Table.getId()).toLowerCase().indexOf(searchKey) > -1){
                            return true;
                        } else if(Table.getDate().toLowerCase().indexOf(searchKey) >-1 ){
                            return true;
                        } else if(String.valueOf(Table.getAmount()).toLowerCase().indexOf(searchKey) >-1) {
                            return true;
                        } else if(String.valueOf(Table.getTotalPrice()).toLowerCase().indexOf(searchKey) >-1) {
                            return true;
                        } else if(Table.getNameCustomer().toLowerCase().indexOf(searchKey)>-1){
                            return true;
                        } else{
                            return false;
                        }
                    }
            );
            SortedList<Table> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());
            tableView.setItems(sortedData);
        });
    }
    public void AddTable(ActionEvent e) throws IOException {
        if(Id.getText().isEmpty() || amount.getText().isEmpty() || nameCustomer.getText().isEmpty() || totalPrice.getText().isEmpty() || date.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Into Text Fields");
            alert.show();
        }else {
            Table customer = new Table();
            customer.setNameCustomer(nameCustomer.getText());
            customer.setAmount(Integer.parseInt(amount.getText()));
            customer.setId(Integer.parseInt(Id.getText()));
            customer.setDate(date.getText());
            customer.setTotalPrice(Double.parseDouble(totalPrice.getText()));
            tables.add(customer);
        }

    }
    public void Delete(ActionEvent e ) throws  IOException{
        Table selected = (Table) tableView.getSelectionModel().getSelectedItem();
        tables.remove(selected);
    }

}