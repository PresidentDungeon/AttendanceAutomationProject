/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendanceautomation.gui;

import attendanceautomation.dal.dbaccess.DBSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Peter
 */
public class AttendanceAutomation extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("views/LoginView.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image(AttendanceAutomation.class.getResourceAsStream("images/imageedit_1_3887033642.png")));
        stage.setTitle("EASV Attendance");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {

            DBSettings.getInstance().closeAllConnections();
        }));
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
