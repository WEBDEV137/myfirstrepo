package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Course;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class CreateUpdateCourseController {
    private CourseDAO courseDAO;
    private UserDAO userDAO;
    private DBAccess dbAccess;
    private Course course;
    private User user;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField cursusnummerTextfield;
    @FXML
    private TextField cursusnaamTextfield;
    @FXML
    private MenuButton coordinatornaamTextfield;
    @FXML
    private ListView<Course> courseList;
    private String coursename;

    public CreateUpdateCourseController() {
        super();
        this.courseDAO = new CourseDAO(dbAccess);
        this.dbAccess = Main.getDBaccess();
    }

    /**
     * ophalen van de coordinatorids bij aanmaken van nieuwe course. TO DO: Bij het wijzigen van de cursus kan je nog niet het coordinatornummer wijzigen.
     *
     * @param course
     */
    public void setup(Course course) {
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        userDAO = new UserDAO(dbAccess);
//        List<Course> allCourses = courseDAO.getAllCourses();
       List<User> allUsers = userDAO.getUsersByRole("Coordinator");

        if (course == null) {
            titleLabel.setText("Nieuwe cursus aanmaken");

            for (int i = 0; i < allUsers.size(); i++) {
                String coordinatorname = allUsers.get(i).getUserName();
                MenuItem menuItem = new MenuItem(coordinatorname);
                coordinatornaamTextfield.getItems().add(menuItem);
                menuItem.setOnAction(event -> setCoordinatorName(coordinatorname));
            }
        } else {
            titleLabel.setText("Cursus wijzigen");
            for (int i = 0; i< allUsers.size(); i++) {
                String coordinatorname = allUsers.get(i).getUserName();
                MenuItem menuItem = new MenuItem(coordinatorname);
                coordinatornaamTextfield.getItems().add(menuItem);
                menuItem.setOnAction(event -> setCoordinatorName(coordinatorname));
            }
            cursusnummerTextfield.setText(String.valueOf(course.getId()));
            cursusnaamTextfield.setText(course.getCoursename());
            coordinatornaamTextfield.setText(userDAO.getUserNameById(course.getCoordinatorid()));

        }
    }

    public void setCoordinatorName(String coordinatorName) {
        coordinatornaamTextfield.setText(coordinatorName);

    }


    //nieuwe cursus aanmaken
    private void createCourse() {
        StringBuilder warningText = new StringBuilder();
        boolean correcteInvoer = true;
        UserDAO userDAO = new UserDAO(dbAccess);
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        int userId;
        int courseId;
        String cursusnaam = cursusnaamTextfield.getText();
        //        int coordinatorid = Integer.parseInt(coordinatorIdTextfield.getText());
        String userName = coordinatornaamTextfield.getText();
        userId = userDAO.getUserIdByLoginName(userName);
        courseId = courseDAO.getCourseIdByName(cursusnaam);

        if (cursusnaam.isEmpty()) {
            warningText.append("Je moet de cursusnaam invullen\n");
            correcteInvoer = false;
        }
        if (!correcteInvoer) {
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText(warningText.toString());
            foutmelding.show();
            course = null;
        } else {
            course = new Course(cursusnaam, userId);
        }
    }


    @FXML
    public void doCreateUpdateCourse(ActionEvent actionEvent) {
        createCourse();
        DBAccess dbAccess = Main.getDBaccess();
        CourseDAO courseDAO = new CourseDAO(dbAccess);

        if (course != null) {
            if (cursusnummerTextfield.getText().equals(("cursusnummer"))) {
                System.out.println(course.getCoursename());
                courseDAO.storeCourse(course);
                System.out.println(course.getCoursename() + "2");
                cursusnummerTextfield.setText(String.valueOf(course.getId()));
                Alert opgeslagen = new Alert(Alert.AlertType.INFORMATION);
                opgeslagen.setContentText("Cursus opgeslagen");
                opgeslagen.show();
            } else {
                int id = Integer.valueOf(cursusnummerTextfield.getText());
                course.setId(id);
                courseDAO.updateCourse(course);
                Alert gewijzigd = new Alert(Alert.AlertType.INFORMATION);
                gewijzigd.setContentText("Cursus gewijzigd");
                gewijzigd.show();
            }
        }
    }

    @FXML
    public void doBackToList(ActionEvent actionEvent) {
        Main.getSceneManager().showManageCoursesScene();;
    }


    public void doMenu(ActionEvent e) {
        dbAccess.closeConnection();
        System.out.println("Connection closes");
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }


}
