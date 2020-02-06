/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controllers;

import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import attendanceautomation.gui.AppModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author ander
 */
public class AttendanceController implements Initializable {

    AppModel appModel = new AppModel();

    @FXML
    private ComboBox<?> comboSort;
    @FXML
    private TableView<Date> attendanceTable;
    @FXML
    private TableColumn<Date, String> dateColumn;
    @FXML
    private TableColumn<Date, String> statusColumn;
    @FXML
    private TableColumn<Date, String> descColumn;
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private HBox paneArea;
    @FXML
    private Label lectureLabel;
    @FXML
    private Label absentLabel;
    @FXML
    private Label percentageLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dateColumn.setCellValueFactory((data) -> {

            Date date = data.getValue();
            return new SimpleStringProperty(date.getDate().toString());
        });

        statusColumn.setCellValueFactory((data) -> {

            Date date = data.getValue();
            if (date.isAttendance()) {
                return new SimpleStringProperty("Present");
            } else {
                return new SimpleStringProperty("Absent");
            }
        });

        descColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    public void setStudent(Student student) {

        attendanceTable.setItems(student.getDays());
        name.setText(student.getName());
        email.setText(student.getEmail());
        loadStatistics(student);
    }

    public void loadStatistics(Student student) {
        double present = 0;
        double absent = 0;

        for (Date d : student.getDays()) {
            if (d.isAttendance()) {
                present++;
            } else {
                absent++;
            }
        }
        String absentPercentage = String.format("%.2f", (absent / (present + absent) * 100));

        //Creates the piechart
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Present", present),
                        new PieChart.Data("Absent", absent)
                );

        PieChart pieChart = new PieChart(pieChartData);
        //pieChart.setTitle("School Attendance");
        pieChart.setClockwise(true);
        pieChart.setLabelLineLength(20);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);
        paneArea.getChildren().add(pieChart);

        //sets the absent and present amounts
        lectureLabel.setText("Lectures Taken: " + present + "/" + (present + absent));
        absentLabel.setText("Classes Absent: " + absent + "/" + (present + absent));
        percentageLabel.setText("Absent Percentage: " + absentPercentage + "%");

    }

}
