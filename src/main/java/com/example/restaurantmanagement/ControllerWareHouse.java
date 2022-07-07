package com.example.restaurantmanagement;

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

public class ControllerWareHouse implements Initializable {
    @FXML
    private TableView roomTable;
    @FXML
    private TextField Number;
    @FXML
    private TextField Search;
    @FXML
    private TextField Ingredient;
    @FXML
    private TextField Id;
    @FXML
    private TextField Price;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private TableColumn<WareHouseModel, Integer> id;
    @FXML
    private TableColumn<WareHouseModel, String> number;
    @FXML
    private TableColumn<WareHouseModel, Integer> price;
    @FXML
    private TableColumn<WareHouseModel, Integer> ingredient;
    @FXML

    public ObservableList<WareHouseModel> tables = FXCollections.observableArrayList(
            new WareHouseModel("Vegetable", 142, 100, 35.000),
            new WareHouseModel("Chicken", 32, 10, 136.6042),
            new WareHouseModel("Fish", 65, 16, 1000.2311),
            new WareHouseModel("Potato", 76, 190, 3231.451),
            new WareHouseModel("Radish", 300, 120, 300.120)
    );

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        ingredient.setCellValueFactory(new PropertyValueFactory<>("ingredient"));
        roomTable.setItems(tables);
        Find();

    }

    public void Add(ActionEvent e) throws IOException {
        if(Id.getText().isEmpty() || Ingredient.getText().isEmpty() || Price.getText().isEmpty() || Number.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Please Enter Into Text Fields");
            alert.show();
        }else {
            WareHouseModel houseModel = new WareHouseModel();
            houseModel.setIngredient(Ingredient.getText());
            houseModel.setId(Integer.parseInt(Id.getText()));
            houseModel.setNumber(Integer.parseInt(Number.getText()));
            houseModel.setPrice(Double.parseDouble(Price.getText()));
            tables.add(houseModel);
        }
    }
    public void Find(){
        FilteredList<WareHouseModel> filteredData = new FilteredList(tables, b->true);
        Search.textProperty().addListener((observableValue, oldValue, newValue) ->{
            filteredData.setPredicate(WareHouseModel ->{
                        if(newValue.isEmpty() || newValue.isBlank()|| newValue == null){
                            return true;
                        }
                        String searchKey = newValue.toLowerCase();
                        if(String.valueOf(WareHouseModel.getId()).toLowerCase().indexOf(searchKey) > -1){
                            return true;
                        } else if(WareHouseModel.getIngredient().toLowerCase().indexOf(searchKey) >-1 ){
                            return true;
                        } else if(String.valueOf(WareHouseModel.getPrice()).toLowerCase().indexOf(searchKey) >-1) {
                            return true;
                        } else if(String.valueOf(WareHouseModel.getNumber()).toLowerCase().indexOf(searchKey) >-1) {
                            return true;
                        } else{
                            return false;
                        }
                    }
            );
            SortedList<WareHouseModel> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(roomTable.comparatorProperty());
            roomTable.setItems(sortedData);
        });
    }

    public void Delete(ActionEvent e) {
        WareHouseModel selected = (WareHouseModel) roomTable.getSelectionModel().getSelectedItem();
        tables.remove(selected);
    }

}
