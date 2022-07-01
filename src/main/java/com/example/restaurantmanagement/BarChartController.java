package com.example.restaurantmanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.util.Pair;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BarChartController implements Initializable {

    @FXML
    private BarChart<String, Double> barChart;

    @FXML
    private BarChart<String, Double> barChart1;

    @FXML
    private Button barChartBtn;


    @FXML
    void drawBarChart(ActionEvent event) {
        barChart.setVisible(true);
        barChart1.setVisible(false);
        Double[] a = new Double[13];
        for (int i=0;i<=12;i++) a[i] = 0.0;
        Connection connection = DBConnection.getConnections();
        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM staff");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){

                a[rs.getDate(3).getMonth() + 1] += rs.getDouble(6);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        XYChart.Series<String,Double> series = new XYChart.Series<String , Double>();
        for(int i=1;i<=12;i++){
            series.getData().add(new XYChart.Data<String, Double>(i+"", a[i]));
        }
        barChart.getData().add(series);
        barChart.getData().remove(series1);
    }

    @FXML
    void drawBarChart1(ActionEvent event) {
        barChart.setVisible(false);
        barChart1.setVisible(true);
        double[] a = new double[100];
        for(int i=0;i<100;i++) a[i] = 0.0;
        Connection connection = DBConnection.getConnections();

        try{
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM staff");
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                a[rs.getDate(3).getYear()-100] += rs.getDouble(6);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        XYChart.Series<String,Double> series = new XYChart.Series<String , Double>();
        for(int i=17;i<=27;i++){
            series.getData().add(new XYChart.Data<String, Double>((i+2000)+"", a[i]));
        }
        barChart1.getData().add(series);
        barChart1.getData().remove(series2);
    }

    XYChart.Series<String,Double> series1 = new XYChart.Series<String , Double>();
    XYChart.Series<String,Double> series2 = new XYChart.Series<String , Double>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Double[] a = new Double[13];
        for (int i = 0; i <= 12; i++) a[i] = 0.0;
        for (int i = 1; i <= 12; i++) {
            series1.getData().add(new XYChart.Data<String, Double>(i + "", a[i]));
        }
        Double b[] = new Double[100];
        for (int i = 0;i<100;i++) b[i]=0.0;
        for(int i=17;i<=27;i++){
            series2.getData().add(new XYChart.Data<String, Double>((i+2000)+"", b[i]));
        }


        barChart.getData().add(series1);
        barChart1.getData().add(series2);
        barChart.setVisible(false);
        barChart1.setVisible(false);
    }
}
