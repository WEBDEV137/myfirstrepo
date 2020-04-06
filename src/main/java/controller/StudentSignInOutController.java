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
        List<Course> allCourses = cdao.getAllCourses();
        for (Course course : allCourses ) {
            signedOutCourseList.getItems().add(course);
        }
        List<Course> inscrijvingCursus = cdao.getAllInscrijvingCourses(Main.getCurrentUser().getUserId());
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
//       signedInCourseList.getItems().add(course);
        Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.INFORMATION);
        verkeerdeInlogGegevens.setContentText("Je bent ingescrijft voor " + course + " .");
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
//        signedInCourseList.getItems().remove(course);
        Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.INFORMATION);
        verkeerdeInlogGegevens.setContentText("Je bent uitgescrijft voor " + course + " .");
        verkeerdeInlogGegevens.show();
    }
}
