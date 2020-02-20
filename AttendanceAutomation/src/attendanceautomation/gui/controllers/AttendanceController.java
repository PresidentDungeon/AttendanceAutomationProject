/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controllers;

import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import attendanceautomation.gui.AppModel;
import attendanceautomation.gui.AttendanceAutomation;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    @FXML
    private ComboBox<?> comboSort1;
    @FXML
    private MenuItem teacherViewMenu;
    @FXML
    private MenuBar menubar;
    @FXML
    private MenuItem MoodleMenu;
    @FXML
    private MenuItem statisticsViewMenu;
    @FXML
    private Menu logOutMenu;
    @FXML
    private JFXButton absentButton;
    @FXML
    private HBox paneArea1;
    @FXML
    private Label percentageLabel11;
    @FXML
    private Label percentageLabel1;

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
        
        int[] absentDay = {0, 0, 0, 0, 0};

        for (Date d : student.getDays()) {
            if (d.isAttendance()) {
                present++;
            } else {
                absent++;
                absentDay[d.getDate().getDayOfWeek().getValue() - 1] = absentDay[d.getDate().getDayOfWeek().getValue() - 1] += 1;

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
        pieChart.setTitle("Absence Overall");
        pieChart.setLabelLineLength(20);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);
        paneArea.getChildren().add(pieChart);

        //sets the absent and present amounts
        lectureLabel.setText("Lectures Taken: " + present + "/" + (present + absent));
        absentLabel.setText("Classes Absent: " + absent + "/" + (present + absent));
        percentageLabel.setText("Absent Percentage: " + absentPercentage + "%");
        
        //Creates the barchart
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day of week");
        
        final BarChart <String,Number> barChart = new BarChart<String,Number>(xAxis,yAxis);
        XYChart.Series series = new XYChart.Series();
        
        //Populates the data
        
        series.getData().add(new XYChart.Data("Monday",absentDay[0]));
        series.getData().add(new XYChart.Data("Tuesday",absentDay[1]));
        series.getData().add(new XYChart.Data("Wednesday",absentDay[2]));
        series.getData().add(new XYChart.Data("Thursday",absentDay[3]));
        series.getData().add(new XYChart.Data("Friday",absentDay[4]));
        
        barChart.getData().add(series);
        barChart.setTitle("Daily Absence");
        paneArea1.getChildren().add(barChart);

    }

    @FXML
    private void goToMoodle(ActionEvent event) {

    }

    @FXML
    private void goToTeacherView(ActionEvent event) throws IOException {

        //Loads the teacher view from the menubar. This should not be in the final project
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppModel.class.getResource("views/AttendanceViewTeacher.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        Stage appStage = (Stage) menubar.getScene().getWindow();
        appStage.setScene(scene);
        appStage.show();

    }

    @FXML
    private void goToStatisticsView(ActionEvent event) throws IOException {

    }

    @FXML
    private void logOut(ActionEvent event) {
    }

    @FXML
    private void openDescriptionView(MouseEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AppModel.class.getResource("views/DescriptionView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
    }


}
