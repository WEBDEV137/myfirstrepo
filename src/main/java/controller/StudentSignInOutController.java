package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Course;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentSignInOutController {
    private CourseDAO cdao;
    private DBAccess db;

    public StudentSignInOutController() {
        super();
        this.cdao = new CourseDAO(db);
        this.db = Main.getDBaccess();
    }

    @FXML
    private ListView<Course> signedOutCourseList;
    @FXML
    private ListView <Course> signedInCourseList;


    public void setup() {
        this.cdao = new CourseDAO(Main.getDBaccess());
        List<Course> allCourses = cdao.getAll();
        Collections.sort(allCourses);
        for (Course course : allCourses ) {
            signedOutCourseList.getItems().add(course);
        }
        List<Course> inscrijvingCursus = cdao.getAllInscrijvingCourses(Main.getCurrentUser().getUserId());
        Collections.sort(inscrijvingCursus);
        for (Course course:inscrijvingCursus
             ) {
            signedInCourseList.getItems().add(course);
        }
        System.out.println(inscrijvingCursus);
    }

    public void doMenu() {
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }

    public void doSignIn(ActionEvent e) {
       Course course = signedOutCourseList.getSelectionModel().getSelectedItem();
       if (course == null) {
           Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.INFORMATION);
           verkeerdeInlogGegevens.setContentText("Je moet een course kiezen!!!");
           verkeerdeInlogGegevens.show();
           return;
       }
       cdao.storeInscrijving(course);
        Main.getSceneManager().showStudentSignInOutScene();
        Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.INFORMATION);
        verkeerdeInlogGegevens.setContentText("Je heeft ingescreven voor " + course + " .");
        verkeerdeInlogGegevens.show();

    }

    public void doSignOut(ActionEvent e) {
        Course course = signedInCourseList.getSelectionModel().getSelectedItem();
        if (course == null) {
            Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.INFORMATION);
            verkeerdeInlogGegevens.setContentText("Je moet een course kiezen!!!");
            verkeerdeInlogGegevens.show();
            return;
        }
        cdao.deleteInshrijvingCourse(course);
        Main.getSceneManager().showStudentSignInOutScene();
        Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.INFORMATION);
        verkeerdeInlogGegevens.setContentText("Je heeft uitgescreven voor " + course + " .");
        verkeerdeInlogGegevens.show();
    }
}
