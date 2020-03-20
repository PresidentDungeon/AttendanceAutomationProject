/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controllers;

import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import attendanceautomation.gui.AppModel;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ander
 */
public class AttendanceController implements Initializable {

    AppModel appModel = new AppModel();
    Student loggedStudent;
    TableView<Student> students;
    private PieChart pieChart; 
    private BarChart <String,Number> barChart;

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
    private MenuBar menubar;
    @FXML
    private MenuItem MoodleMenu;
    @FXML
    private Menu logOutMenu;
    @FXML
    private JFXButton absentButton;
    @FXML
    private HBox paneArea1;
    @FXML
    private Label percentageLabel1;
    @FXML
    private MenuItem handleLogOut;


    
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

    public void setStudent(Student student, TableView<Student> students) {

        attendanceTable.setItems(student.getDays());
        name.setText(student.getName());
        email.setText(student.getEmail());
        loggedStudent = student;
        this.students = students;
        loadStatistics(student);
        setCharts();
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

        pieChart = new PieChart(pieChartData);
        pieChart.setClockwise(true);
        pieChart.setTitle("Absence Overall");
        pieChart.setLabelLineLength(20);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);

        //sets the absent and present amount labels
        
       String lectureLabelText = "Lectures Taken: " + present + "/" + (present + absent);
       String absentLabelText = "Classes Absent: " + absent + "/" + (present + absent);
       String percentageLabelText = "Absent Percentage: " + absentPercentage + "%";

        
        //Creates the barchart
        
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day of week");
        
        barChart = new BarChart<String,Number>(xAxis,yAxis);
        XYChart.Series series = new XYChart.Series();
        
        //Populates the data
        
        series.getData().add(new XYChart.Data("Monday",absentDay[0]));
        series.getData().add(new XYChart.Data("Tuesday",absentDay[1]));
        series.getData().add(new XYChart.Data("Wednesday",absentDay[2]));
        series.getData().add(new XYChart.Data("Thursday",absentDay[3]));
        series.getData().add(new XYChart.Data("Friday",absentDay[4]));
        
        barChart.getData().add(series);
        barChart.setTitle("Daily Absence");
        
        Platform.runLater(()->{
        lectureLabel.setText(lectureLabelText);
        absentLabel.setText(absentLabelText);
        percentageLabel.setText(percentageLabelText);
        });
    }
   
    public void setCharts()
    {
        paneArea.getChildren().add(pieChart);
        paneArea1.getChildren().add(barChart);
    }

    @FXML
    private void goToMoodle(ActionEvent event) {

    }


        @FXML
    private void handleLogOut(ActionEvent event) throws Exception {

        
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppModel.class.getResource("views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage appStage = (Stage) menubar.getScene().getWindow();
        appStage.setScene(scene);   
        

    }
    

    @FXML
    private void openDescriptionView(MouseEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(AppModel.class.getResource("views/DescriptionView.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            
            DescriptionViewController controller = fxmlLoader.getController();
            controller.setStudent(loggedStudent, attendanceTable.getItems(), updateStatisticsThread());
            
            stage.setScene(scene);
            stage.show();
    }

    @FXML
    private void toBeAttending(ActionEvent event) {
        
        Date date = new Date(LocalDate.now(), true, "");
        appModel.toBeAttending(loggedStudent, date);
        loggedStudent.setDays(appModel.getStudentDays(loggedStudent));
        attendanceTable.setItems(loggedStudent.getDays());
        updateStatisticsThread().start();
    }

    //This runs when updating or creating new dates
    public Thread updateStatisticsThread()
    {
        Thread testThread = new Thread(()->{
        
            Platform.runLater(() -> {
            paneArea.getChildren().clear();
            paneArea1.getChildren().clear();
        });

        loadStatistics(loggedStudent);
        
        Platform.runLater(() -> {
            setCharts();
            
            if(students != null)
        {   
            int placement = students.getItems().indexOf(loggedStudent);
            students.getItems().remove(loggedStudent);
            students.getItems().add(placement, loggedStudent);
            students.refresh();
        }
        });
        });
        return testThread;
    }
    
    
}
