package com.example.restaurantmanagement;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class EmployeeManagementController implements Initializable {

    @FXML
    private JFXButton add;

    @FXML
    private TableColumn<EmployeeManagementModel, String> addressCol;

    @FXML
    private TableColumn<EmployeeManagementModel, String> dateCol;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton edit;

    @FXML
    private TableColumn actionCol;

    @FXML
    private TableView<EmployeeManagementModel> employeeTable;

    @FXML
    private TableColumn<EmployeeManagementModel, String> idCol;

    @FXML
    private TableColumn<EmployeeManagementModel, String> jobCob;

    @FXML
    private TableColumn<EmployeeManagementModel, String> nameCol;

    @FXML
    private TableColumn<EmployeeManagementModel, String> phoneCol;

    @FXML
    private TableColumn<EmployeeManagementModel, Double> salCol;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<EmployeeManagementModel, String> statusCol;

    private ObservableList<EmployeeManagementModel> Table = FXCollections.observableArrayList();

    @FXML
    void handleAddAction(ActionEvent event) {
        addEmployeeInfo();
    }

//    public void refreshTable(){
//        Table.clear();
//        Connection connection = DBConnection.getConnections();
//        try {
//            String sql = "SELECT * FROM staff";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            ResultSet rs = statement.executeQuery();
//            while (rs.next()){
//                Table.add(new EmployeeManagementModel(rs.getString("ID"),
//                          rs.getString("NAME"),
//                          rs.getString("DATE"),
//                          rs.getString("PHONE"),
//                          rs.getString("ADDRESS"),
//                           Double.parseDouble(rs.getString("SALARY")),
//                          rs.getString("JOB"),
//                          rs.getString("STATUS")));
//                employeeTable.setItems(Table);
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Table.clear();
        nameCol.setCellValueFactory(new PropertyValueFactory<EmployeeManagementModel, String>("Name")); //adminCustomerTable variable name
        idCol.setCellValueFactory(new PropertyValueFactory<EmployeeManagementModel, String>("ID"));
        dateCol.setCellValueFactory(new PropertyValueFactory<EmployeeManagementModel, String>("Date"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<EmployeeManagementModel, String>("Phone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<EmployeeManagementModel, String>("Address"));
        salCol.setCellValueFactory(new PropertyValueFactory<EmployeeManagementModel, Double>("Salary"));
        jobCob.setCellValueFactory(new PropertyValueFactory<EmployeeManagementModel, String>("Job"));
        statusCol.setCellValueFactory(new PropertyValueFactory<EmployeeManagementModel, String>("Status"));
        showEmployeeTable();
        actionButtons();
    }

    public void showEmployeeTable(){
        //Table.clear();
        Connection connection = DBConnection.getConnections();
        try {
            if (!connection.isClosed()) {
                String sql = "SELECT * FROM staff ORDER BY ID";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    String ID = resultSet.getString("ID"); //SQL COL NAMES NID
                    String NAME = resultSet.getString("NAME");
                    String DATE = resultSet.getString("DATE");
                    String PHONE = resultSet.getString("PHONE");
                    String ADDRESS = resultSet.getString("ADDRESS");
                    Double SALARY = Double.parseDouble(resultSet.getString("SALARY"));
                    String JOB = resultSet.getString("JOB");
                    String STATUS = resultSet.getString("STATUS");

                    EmployeeManagementModel empTable = new EmployeeManagementModel(ID, NAME, DATE, ADDRESS, PHONE, SALARY, JOB, STATUS);

                    Table.add(empTable);
                }
                employeeTable.getItems().setAll(Table);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBConnection.closeConnections();
        }

        FilteredList<EmployeeManagementModel> filteredData = new FilteredList<>(Table, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(search -> { // here search is a object of CustomerRoomTable class
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (search.getID().toLowerCase().indexOf(searchKeyword) != -1) {
                    return true; // Filter matches ID.
                } else if (search.getName().toLowerCase().indexOf(searchKeyword) != -1) {
                    return true; // Filter matches Name.
                } else if (search.getAddress().toLowerCase().indexOf(searchKeyword) != -1) {
                    return true; // Filter matches Address
                } else if (search.getDate().toLowerCase().indexOf(searchKeyword) != -1) {
                    return true; // Filter matches Room Email
                } else if (search.getPhone().toLowerCase().indexOf(searchKeyword) != -1) {
                    return true; // Matches Phone
                } else {
                    return false;
                }
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<EmployeeManagementModel> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        employeeTable.setItems(sortedData);
    }



    private void addEmployeeInfo() {
        EmployeeManagementModel em = new EmployeeManagementModel();
        Connection connection = DBConnection.getConnections();
        try {
            if (!connection.isClosed()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("AddEmployee.fxml"));
                Parent viewContact = loader.load();
                EmployeeInfoController controller = (EmployeeInfoController) loader.getController();
                Scene scene = new Scene(viewContact);

                Stage window = new Stage();
                window.setScene(scene);
                window.initStyle(StageStyle.UNDECORATED);

                stagePosition(window, viewContact);

                window.showAndWait();
                em = controller.empl();
                Table.add(em);
                employeeTable.setItems(Table);

            }

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        } finally {
            DBConnection.closeConnections();
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

    public void editEmployeeInfo(EmployeeManagementModel epm) {
        Connection connection = DBConnection.getConnections();
        try {
            if (!connection.isClosed()) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("EmployeeInfoEdit.fxml"));
                Parent viewContact = loader.load();
                Scene scene = new Scene(viewContact);
                EmployeeInfoEditController epl = loader.getController();
                epl.setEmployeeInfo(epm);
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

    private void actionButtons() {
        Callback<TableColumn<EmployeeManagementModel, String>, TableCell<EmployeeManagementModel, String>> cellCallback =
                new Callback<TableColumn<EmployeeManagementModel, String>, TableCell<EmployeeManagementModel, String>>() {
                    @Override
                    public TableCell<EmployeeManagementModel, String> call(TableColumn<EmployeeManagementModel, String> param) {

                        TableCell<EmployeeManagementModel, String> cell = new TableCell<EmployeeManagementModel, String>() {

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
                                        EmployeeManagementModel eplTable = getTableView().getItems().get(getIndex());
                                        tableRowDelete(eplTable);
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
                                        EmployeeManagementModel eplTable = getTableView().getItems().get(getIndex());
                                        editEmployeeInfo(eplTable);

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

    public void tableRowDelete(EmployeeManagementModel eplTable) {
        Connection connection = DBConnection.getConnections();
        try {
            if (!connection.isClosed()) {
                String sql = "DELETE FROM staff where ID=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, eplTable.getID());
                statement.execute();
                Main.showAlert(Alert.AlertType.INFORMATION, "Delete Operation Successful", "Customer Named " + eplTable.getName() + " is deleted from database!");
                employeeTable.getItems().remove(eplTable);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            DBConnection.closeConnections();
        }
    }

}
