package com.example.restaurantmanagement;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class BillController implements Initializable {

    @FXML
    private TableView<BillModel> billTable;

    @FXML
    private TableColumn<BillModel, String> idCol;

    @FXML
    private TableColumn<BillModel, String> dateCol;

    @FXML
    private TableColumn<BillModel, String> amountCol;

    @FXML
    private TableColumn<BillModel, Double> vatCol;

    @FXML
    private TableColumn<BillModel, Double> priceCol;

    @FXML
    private TableColumn<BillModel, Double> totalPriceCol;

    @FXML
    private TextField search;

    private ObservableList<BillModel> Table = FXCollections.observableArrayList();

    @FXML
    void handleAddAction(ActionEvent event) {}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Table.clear();
        idCol.setCellValueFactory(new PropertyValueFactory<BillModel, String>("ID"));
        dateCol.setCellValueFactory(new PropertyValueFactory<BillModel, String>("Date"));
        amountCol.setCellValueFactory(new PropertyValueFactory<BillModel, String>("Amount"));
        vatCol.setCellValueFactory(new PropertyValueFactory<BillModel, Double>("VAT"));
        priceCol.setCellValueFactory(new PropertyValueFactory<BillModel, Double>("Price"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<BillModel, Double>("TotalPrice"));
        showEmployeeTable();
    }

    public void showEmployeeTable(){
        //Table.clear();
        Connection connection = DBConnection.getConnections();
        try {
            if (!connection.isClosed()) {
                String sql = "SELECT * FROM bill ORDER BY ID";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String ID = resultSet.getString("ID"); //SQL COL NAMES NID
                    String DATE = resultSet.getString("DATE");
                    String AMOUNT = resultSet.getString("AMOUNT");
                    Double VAT = Double.parseDouble(resultSet.getString("VAT"));
                    Double PRICE = Double.parseDouble(resultSet.getString("PRICE"));
                    Double TOTALPRICE = Double.parseDouble(resultSet.getString("TOTALPRICE"));

                    BillModel empTable = new BillModel(ID, DATE, AMOUNT, VAT, PRICE, TOTALPRICE);

                    Table.add(empTable);
                }
                billTable.getItems().setAll(Table);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            DBConnection.closeConnections();
        }

        FilteredList<BillModel> filteredData = new FilteredList<>(Table, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(search -> { // here search is a object of CustomerRoomTable class
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (search.getID().toLowerCase().contains(searchKeyword)) {
                    return true; // Filter matches ID.
                } else return search.getDate().toLowerCase().contains(searchKeyword); // Filter matches Room Email
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<BillModel> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(billTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        billTable.setItems(sortedData);
    }

    public static double xxx, yyy;
    public void stagePosition(Stage primaryStage, Parent root) {
        AtomicReference<Double> x = new AtomicReference<>(primaryStage.getX());
        AtomicReference<Double> y = new AtomicReference<>(primaryStage.getY());
        root.setOnMousePressed(event -> {
            xxx = event.getSceneX();
            yyy = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xxx);
            primaryStage.setY(event.getScreenY() - yyy);
            x.set(primaryStage.getX());
            y.set(primaryStage.getY());
        });
    }
}
