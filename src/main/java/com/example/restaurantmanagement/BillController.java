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
    private TableColumn actionCol;

    @FXML
    private TextField search;

    private ObservableList<BillModel> Table = FXCollections.observableArrayList();

    @FXML
    void handleAddAction(ActionEvent event) {
        addBillInfo();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Table.clear();
        idCol.setCellValueFactory(new PropertyValueFactory<BillModel, String>("ID"));
        dateCol.setCellValueFactory(new PropertyValueFactory<BillModel, String>("Date"));
        amountCol.setCellValueFactory(new PropertyValueFactory<BillModel, String>("Amount"));
        vatCol.setCellValueFactory(new PropertyValueFactory<BillModel, Double>("VAT"));
        priceCol.setCellValueFactory(new PropertyValueFactory<BillModel, Double>("Price"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<BillModel, Double>("TotalPrice"));
        showTable();
        actionButtons();
    }

    public void showTable(){
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

                    BillModel bllTable = new BillModel(ID, DATE, AMOUNT, VAT, PRICE, TOTALPRICE);

                    Table.add(bllTable);
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
        SortedList<BillModel> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(billTable.comparatorProperty());
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

    private void addBillInfo() {
        BillModel bill = new BillModel();
        Connection connection = DBConnection.getConnections();
        try {
            if (!connection.isClosed()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("Bill1.fxml"));
                Parent viewContact = loader.load();
                BillEditController controller = (BillEditController) loader.getController();
                Scene scene = new Scene(viewContact);

                Stage window = new Stage();
                window.setScene(scene);
                window.initStyle(StageStyle.UNDECORATED);

                stagePosition(window, viewContact);

                window.showAndWait();
                bill = controller.billModel();
                Table.add(bill);
                billTable.setItems(Table);

            }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        } finally {
            DBConnection.closeConnections();
        }
    }

    private void actionButtons() {
        Callback<TableColumn<BillModel, String>, TableCell<BillModel, String>> cellCallback =
                new Callback<TableColumn<BillModel, String>, TableCell<BillModel, String>>() {
                    @Override
                    public TableCell<BillModel, String> call(TableColumn<BillModel, String> param) {

                        TableCell<BillModel, String> cell = new TableCell<BillModel, String>() {

                            Button editbtn = new Button("Edit");
                            Button deletebtn = new Button("Delete");
                            public HBox hBox = new HBox(25, editbtn, deletebtn);

                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty){
                                    setGraphic(null);
                                    setText(null);
                                }else{

                                    deletebtn.setStyle(
                                            " -fx-cursor: hand ;"
                                                    + "-glyph-size:20px;"
                                                    + "-fx-fill:#ffffff;"
                                    );

                                    deletebtn.setOnMouseEntered((MouseEvent event) ->{
                                        deletebtn.setStyle(
                                                " -fx-cursor: hand ;"
                                                        +
                                                        "-glyph-size:20px;"
                                                        +"-fx-fill:khaki;"
                                        );
                                    });

                                    deletebtn.setOnMouseExited((MouseEvent event2) ->{
                                        deletebtn.setStyle(
                                                " -fx-cursor: hand ;"
                                                        +
                                                        "-glyph-size:20px;"
                                                        + "-fx-fill:white;"
                                        );
                                    });

                                    deletebtn.setOnMouseClicked((MouseEvent event2) ->{
                                        deletebtn.setStyle(
                                                " -fx-cursor: hand ;"
                                                        +
                                                        "-glyph-size:20px;"
                                                        +"-fx-fill:lightgreen;"
                                        );

                                        //delete sql statements
                                        BillModel bllTable = getTableView().getItems().get(getIndex());
                                        tableRowDelete(bllTable);
                                    });

                                    editbtn.setStyle(
                                            " -fx-cursor: hand ;"
                                                    + "-glyph-size:20px;"
                                                    + "-fx-fill:#ffffff;"
                                    );

                                    editbtn.setOnMouseEntered((MouseEvent event) ->{
                                        editbtn.setStyle(
                                                " -fx-cursor: hand ;"
                                                        +
                                                        "-glyph-size:20px;"
                                                        +"-fx-fill:khaki;"
                                        );
                                    });

                                    editbtn.setOnMouseExited((MouseEvent event2) ->{
                                        editbtn.setStyle(
                                                " -fx-cursor: hand ;"
                                                        +
                                                        "-glyph-size:20px;"
                                                        + "-fx-fill:white;"
                                        );
                                    });

                                    editbtn.setOnMouseClicked((MouseEvent event2) ->{
                                        editbtn.setStyle(
                                                " -fx-cursor: hand ;"
                                                        +
                                                        "-glyph-size:20px;"
                                                        +"-fx-fill:lightgreen;"
                                        );
                                        BillModel bllTable = getTableView().getItems().get(getIndex());
                                        editInfo(bllTable);

                                    });

                                    hBox.setStyle("-fx-alignment:center");
                                    setGraphic(hBox);
                                }
                            }
                        };

                        return cell;
                    }
                };
        actionCol.setCellFactory(cellCallback);
    }

    public void tableRowDelete(BillModel bllTable) {
        Connection connection = DBConnection.getConnections();
        try {
            if (!connection.isClosed()) {
                String sql = "DELETE FROM bill where ID=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, bllTable.getID());
                statement.execute();
                Main.showAlert(Alert.AlertType.INFORMATION, "Delete Operation Successful", "Bill " + bllTable.getID() + " is deleted from database!");
                billTable.getItems().remove(bllTable);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBConnection.closeConnections();
        }
    }

    public void editInfo(BillModel bll) {
        Connection connection = DBConnection.getConnections();
        try {
            if (!connection.isClosed()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("BillInfoEdit.fxml"));
                Parent viewContact = loader.load();
                Scene scene = new Scene(viewContact);
                BillEditController1 bll1 = loader.getController();
                bll1.setBillInfo(bll);
                Stage window = new Stage();
                window.setScene(scene);
                window.initStyle(StageStyle.UNDECORATED);
                stagePosition(window, viewContact);
                window.showAndWait();
            }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        } finally {
            DBConnection.closeConnections();
        }
    }
}
