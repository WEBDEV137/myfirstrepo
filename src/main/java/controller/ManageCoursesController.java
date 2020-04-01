package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.Course;
import model.User;
import view.Main;

import java.util.List;

public class ManageCoursesController {
    private CourseDAO courseDAO;
    private DBAccess dbAccess;

    @FXML
    private ListView<Course> courseList;


    // connectie maken met dbase om courses te laten zien in het scherm listview
    public void setup() {
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        this.courseDAO = new CourseDAO(dbAccess);
        List<Course> allCourses = courseDAO.getAllCourses();
        for (Course course : allCourses) {
            courseList.getItems().add(course);
            System.out.println(course.getCoursename());
        }

    }


    public void doMenu() {
    }

    //nieuwe cursus maken: doorgaan naar scherm createupdatecourse
    @FXML
    public void doCreateCourse(ActionEvent e) {
        Main.getSceneManager().showCreateUpdateCourseScene(null);
    }


    @FXML
    public void doUpdateCourse(ActionEvent e) {
        Course course = courseList.getSelectionModel().getSelectedItem();
        Main.getSceneManager().showExistingCourseScene(course);
    }

    public void doDeleteCourse() {
    }

}
