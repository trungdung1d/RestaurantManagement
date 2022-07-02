package com.example.restaurantmanagement;

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

public class DishController implements Initializable {

    @FXML
    private TableView<DishModel> dishTable;

    @FXML
    private TableColumn<DishModel, String> idCol;

    @FXML
    private TableColumn<DishModel, String> nameCol;

    @FXML
    private TableColumn<DishModel, String> amountCol;

    @FXML
    private TableColumn<DishModel, Double> vatCol;

    @FXML
    private TableColumn<DishModel, Double> priceCol;

    @FXML
    private TableColumn<DishModel, Double> totalPriceCol;

    @FXML
    private TableColumn actionCol;

    @FXML
    private TextField search;

    private ObservableList<DishModel> Table = FXCollections.observableArrayList();

    @FXML
    void handleAddAction(ActionEvent event) {
        addDish();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Table.clear();
        idCol.setCellValueFactory(new PropertyValueFactory<DishModel, String>("ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<DishModel, String>("Name"));
        amountCol.setCellValueFactory(new PropertyValueFactory<DishModel, String>("Amount"));
        vatCol.setCellValueFactory(new PropertyValueFactory<DishModel, Double>("VAT"));
        priceCol.setCellValueFactory(new PropertyValueFactory<DishModel, Double>("Price"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<DishModel, Double>("TotalPrice"));
        showEmployeeTable();
        actionButtons();
    }

    public void showEmployeeTable() {
        //Table.clear();
        Connection connection = DBConnection.getConnections();
        try {
            if (!connection.isClosed()) {
                String sql = "SELECT * FROM dish ORDER BY ID";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String ID = resultSet.getString("ID"); //SQL COL NAMES NID
                    String NAME = resultSet.getString("NAME");
                    String AMOUNT = resultSet.getString("AMOUNT");
                    Double VAT = Double.parseDouble(resultSet.getString("VAT"));
                    Double PRICE = Double.parseDouble(resultSet.getString("PRICE"));
                    Double TOTALPRICE = Double.parseDouble(resultSet.getString("TOTALPRICE"));

                    DishModel empTable = new DishModel(ID, NAME, AMOUNT, VAT, PRICE, TOTALPRICE);

                    Table.add(empTable);
                }
                dishTable.getItems().setAll(Table);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            DBConnection.closeConnections();
        }

        FilteredList<DishModel> filteredData = new FilteredList<>(Table, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(search -> { // here search is a object of CustomerRoomTable class
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (search.getID().toLowerCase().contains(searchKeyword)) {
                    return true; // Filter matches ID.
                } else return search.getName().toLowerCase().contains(searchKeyword); // Filter matches Room Email
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<DishModel> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(dishTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        dishTable.setItems(sortedData);
    }


    private void addDish() {
        DishModel dishModel = new DishModel();
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("DishInfo1.fxml"));
            Parent viewContact = loader.load();
            DishInfoController controller = loader.getController();
            Scene scene = new Scene(viewContact);

            Stage window = new Stage();
            window.setScene(scene);
            window.initStyle(StageStyle.UNDECORATED);

            stagePosition(window, viewContact);

            window.showAndWait();

            dishModel = controller.dish();
            if (dishModel != null) {
                Table.add(dishModel);
                dishTable.setItems(Table);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
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

    public void edit(DishModel dishModel) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("DishInfo1.fxml"));
            Parent viewContact = loader.load();
            Scene scene = new Scene(viewContact);
            DishInfoController controller = loader.getController();
            controller.setDish(dishModel);
            Stage window = new Stage();
            window.setScene(scene);
            window.initStyle(StageStyle.UNDECORATED);
            stagePosition(window, viewContact);
            window.showAndWait();

            DishModel newDish = controller.dish();
            if (newDish == null) {
                return;
            }

            int idx = Table.indexOf(dishModel);
            if (idx == -1) {
                Table.add(newDish);
                dishTable.setItems(Table);
            } else {
                Table.remove(dishModel);
                Table.add(idx, newDish);
                dishTable.setItems(Table);
            }
        } catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            DBConnection.closeConnections();
        }
    }

    private void actionButtons() {
        Callback<TableColumn<DishModel, String>, TableCell<DishModel, String>> cellCallback =
                new Callback<TableColumn<DishModel, String>, TableCell<DishModel, String>>() {
                    @Override
                    public TableCell<DishModel, String> call(TableColumn<DishModel, String> param) {

                        TableCell<DishModel, String> cell = new TableCell<DishModel, String>() {

                            Button editbtn = new Button("Edit");
                            Button deletebtn = new Button("Delete");
                            public HBox hBox = new HBox(25, editbtn, deletebtn);

                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty) {
                                    setGraphic(null);
                                    setText(null);
                                } else {

                                    deletebtn.setStyle(
                                            " -fx-cursor: hand ;"
                                                    + "-glyph-size:20px;"
                                                    + "-fx-fill:#ffffff;"
                                    );

                                    deletebtn.setOnMouseEntered((MouseEvent event) -> {
                                        deletebtn.setStyle(
                                                " -fx-cursor: hand ;"
                                                        +
                                                        "-glyph-size:20px;"
                                                        + "-fx-fill:khaki;"
                                        );
                                    });

                                    deletebtn.setOnMouseExited((MouseEvent event2) -> {
                                        deletebtn.setStyle(
                                                " -fx-cursor: hand ;"
                                                        +
                                                        "-glyph-size:20px;"
                                                        + "-fx-fill:white;"
                                        );
                                    });

                                    deletebtn.setOnMouseClicked((MouseEvent event2) -> {
                                        deletebtn.setStyle(
                                                " -fx-cursor: hand ;"
                                                        +
                                                        "-glyph-size:20px;"
                                                        + "-fx-fill:lightgreen;"
                                        );

                                        //delete sql statements
                                        DishModel eplTable = getTableView().getItems().get(getIndex());
                                        tableRowDelete(eplTable);
                                    });

                                    editbtn.setStyle(
                                            " -fx-cursor: hand ;"
                                                    + "-glyph-size:20px;"
                                                    + "-fx-fill:#ffffff;"
                                    );

                                    editbtn.setOnMouseEntered((MouseEvent event) -> {
                                        editbtn.setStyle(
                                                " -fx-cursor: hand ;"
                                                        +
                                                        "-glyph-size:20px;"
                                                        + "-fx-fill:khaki;"
                                        );
                                    });

                                    editbtn.setOnMouseExited((MouseEvent event2) -> {
                                        editbtn.setStyle(
                                                " -fx-cursor: hand ;"
                                                        +
                                                        "-glyph-size:20px;"
                                                        + "-fx-fill:white;"
                                        );
                                    });

                                    editbtn.setOnMouseClicked((MouseEvent event2) -> {
                                        editbtn.setStyle(
                                                " -fx-cursor: hand ;"
                                                        +
                                                        "-glyph-size:20px;"
                                                        + "-fx-fill:lightgreen;"
                                        );
                                        DishModel eplTable = getTableView().getItems().get(getIndex());
                                        edit(eplTable);

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

    public void tableRowDelete(DishModel eplTable) {
        Connection connection = DBConnection.getConnections();
        try {
            if (!connection.isClosed()) {
                String sql = "DELETE FROM dish where ID=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, eplTable.getID());
                statement.execute();
                Main.showAlert(Alert.AlertType.INFORMATION, "Delete Operation Successful", "Dish " + eplTable.getID() + " is deleted from database!");
                dishTable.getItems().remove(eplTable);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBConnection.closeConnections();
        }
    }
}
