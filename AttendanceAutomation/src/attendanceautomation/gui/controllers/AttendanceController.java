/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui.controllers;

import attendanceautomation.be.Date;
import attendanceautomation.be.Student;
import attendanceautomation.gui.AppModel;
import attendanceautomation.utilities.AbsenceCalculator;
import attendanceautomation.utilities.DateIndexer;
import com.jfoenix.controls.JFXButton;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
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
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ander
 */
public class AttendanceController implements Initializable {

    private AppModel appModel = new AppModel();
    private Student loggedStudent;
    private TableView<Student> students;
    private PieChart pieChart;
    private BarChart<String, Number> barChart;
    private Image attend = new Image("attendanceautomation/gui/images/correct.png");
    private Image absence = new Image("attendanceautomation/gui/images/quit.png");
    private DecimalFormat df;

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
    private MenuItem handleLogOut;
    @FXML
    private ImageView imageMarker;
    @FXML
    private JFXButton attendButton;
    @FXML
    private JFXButton editButton;
    @FXML
    private Label lastLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        df = new DecimalFormat("#.00");

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

    /**
     * This methods runs when the AttendanceView FXML is opened. 
     * The information regarding the student is displayed,
     * such as the name and date list of the student.
     * @param student the student to be displayed
     * @param students the list of students contained by the teacher opening the view
     */
    public void setStudent(Student student, TableView<Student> students) {

        attendanceTable.setItems(student.getDays());
        name.setText(student.getName());
        email.setText(student.getEmail());
        lastLogin.setText(student.getLastAccess().toString());
        loggedStudent = student;
        this.students = students;
        loadStatistics(student);
        setCharts();
        decideMarker();

        if (students != null) {
            editButton.setVisible(true);
        }
    }

    /**
     * Calculates the attendance of the student and inserts the data into graphs.
     * @param student the student the calculation is based on.
     */
    public void loadStatistics(Student student) {

        double absencePercentage = AbsenceCalculator.calculateAttendance(student);
        int absenceDays = AbsenceCalculator.getDaysAbsence(student);
        int presenceDays = AbsenceCalculator.getDaysPresent(student);
        int[] absentDay = DateIndexer.indexAbsenceDays(student);

        String absentPercentage = df.format(absencePercentage) + "%";

        //Creates the piechart
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Present", presenceDays),
                        new PieChart.Data("Absent", absenceDays)
                );

        pieChart = new PieChart(pieChartData);
        pieChart.setClockwise(true);
        pieChart.setTitle("Absence Overall");
        pieChart.setLabelLineLength(20);
        pieChart.setLabelsVisible(true);
        pieChart.setStartAngle(180);

        //sets the absent and present amount labels
        String lectureLabelText = "Lectures Taken: " + presenceDays + "/" + (presenceDays + absenceDays);
        String absentLabelText = "Classes Absent: " + absenceDays + "/" + (presenceDays + absenceDays);
        String percentageLabelText = "Absent Percentage: " + absentPercentage;

        //Creates the barchart
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day of week");

        barChart = new BarChart<String, Number>(xAxis, yAxis);
        XYChart.Series series = new XYChart.Series();

        //Populates the data
        series.getData().add(new XYChart.Data("Monday", absentDay[0]));
        series.getData().add(new XYChart.Data("Tuesday", absentDay[1]));
        series.getData().add(new XYChart.Data("Wednesday", absentDay[2]));
        series.getData().add(new XYChart.Data("Thursday", absentDay[3]));
        series.getData().add(new XYChart.Data("Friday", absentDay[4]));

        barChart.getData().add(series);
        barChart.setTitle("Daily Absence");

        Platform.runLater(() -> {
            lectureLabel.setText(lectureLabelText);
            absentLabel.setText(absentLabelText);
            percentageLabel.setText(percentageLabelText);
        });

    }

    /**
     * Updates the paneAreas with the calculated charts.
     */
    public void setCharts() {
        paneArea.getChildren().add(pieChart);
        paneArea1.getChildren().add(barChart);
    }

    /**
    * Event handler for the moodle menuitem. Opens moodle on the browser.
    * @param event 
    */
    @FXML
    private void goToMoodle(ActionEvent event) {
        {
        try
        {
            Desktop.getDesktop().browse(new URL("https://moodle.easv.dk/login/index.php").toURI());
        } catch (IOException | URISyntaxException ignored)
        {
        }
      }
    }
    
    /**
    * Event handler for the outlook menuitem. Opens outlook on the browser.
    * @param event 
    */
    @FXML
    private void goToOutlook(ActionEvent event) {
        {
        try
        {
            Desktop.getDesktop().browse(new URL("https://outlook.office.com/mail/inbox").toURI());
        } catch (IOException | URISyntaxException ignored)
        {
        }
    }
    }

    /**
     * Event handler for the lougout menuitem.
     * Switches the scene of the stage with the login scene.
     * @param event 
     */
    @FXML
    private void handleLogOut(ActionEvent event) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppModel.class.getResource("views/LoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage appStage = (Stage) menubar.getScene().getWindow();
        appStage.setScene(scene);

    }

    /**
     * Event handler for the absence button.
     * Loads the DescriptionView FXML as a new stage.
     * @param event
     * @throws IOException 
     */
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

    /**
     * Event handler for the attend button.
     * Adds/updates the date for the current day and updates
     * the student list of dates with the attendance.
     * Then updates the students statistics by running the thread
     * created with method updateStatisticsThread()
     * @param event 
     */
    @FXML
    private void toBeAttending(ActionEvent event) {

        Date date = new Date(LocalDate.now(), true, "");
        appModel.toBeAttending(loggedStudent.getId(), date);
        loggedStudent.setDays(appModel.getStudentDays(loggedStudent.getId()));
        attendanceTable.setItems(loggedStudent.getDays());
        updateStatisticsThread().start();
    }

    /**
     * Creates a new Thread that updates the statistics of the logged in student
     * @return the update statistics Thread to be executed
     */
    public Thread updateStatisticsThread() {
        Thread updateThread = new Thread(() -> {

            Platform.runLater(() -> {
                paneArea.getChildren().clear();
                paneArea1.getChildren().clear();

                decideMarker();

            });

            loadStatistics(loggedStudent);

            Platform.runLater(() -> {
                setCharts();

                if (students != null) {
                    int placement = students.getItems().indexOf(loggedStudent);
                    students.getItems().remove(loggedStudent);
                    students.getItems().add(placement, loggedStudent);
                    students.refresh();
                }
            });
        });
        return updateThread;
    }

    /**
     * Determines which image should be displayed for the image marker
     * based on the logged in students attendance and time of the week.
     */
    public void decideMarker() {
        if (LocalDate.now().getDayOfWeek() != DayOfWeek.SATURDAY && LocalDate.now().getDayOfWeek() != DayOfWeek.SUNDAY) {
            if (attendanceTable.getItems().size() == 0) {
                imageMarker.setImage(absence);
            } else if (!attendanceTable.getItems().get(attendanceTable.getItems().size() - 1).isAttendance()
                    || !attendanceTable.getItems().get(attendanceTable.getItems().size() - 1).getDate().toString().equalsIgnoreCase(LocalDate.now().toString())) {
                imageMarker.setImage(absence);
            } else {
                imageMarker.setImage(attend);
            }
        } else {
            absentButton.setDisable(true);
            attendButton.setDisable(true);
        }

    }

    /**
     * Event handler for the edit button. Loads the EditView FXML as a new stage.
     * @param event
     * @throws IOException 
     */
    @FXML
    private void openEditView(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AppModel.class.getResource("views/EditView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();

        EditViewController controller = fxmlLoader.getController();
        controller.setStudent(loggedStudent, attendanceTable, updateStatisticsThread());

        stage.setScene(scene);
        stage.show();
    }

}
