package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Course;
import view.Main;

import java.util.List;

public class CreateUpdateCourseController {
    private CourseDAO courseDAO;
    private DBAccess dbAccess;
    private Course course;
    @FXML
    private Label titleLabel;

    @FXML
    private TextField cursusnummerTextfield;
    @FXML
    private TextField cursusnaamTextfield;
    @FXML
    private MenuButton coordinatorIdTextfield;
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

        if (course == null) {
            CourseDAO courseDAO = new CourseDAO((dbAccess));
            titleLabel.setText("Nieuwe cursus aanmaken");
            List<Course> allCourses = courseDAO.getAllCourses();
            for (int i = 0; i < allCourses.size(); i++) {
                int coordinatorid = allCourses.get(i).getCoordinatorid();
                MenuItem menuItem = new MenuItem(String.valueOf(coordinatorid));
                coordinatorIdTextfield.getItems().add(menuItem);
                menuItem.setOnAction(event -> setCoordinatorid(coordinatorid));

            }
        } else {
            titleLabel.setText("Beheer cursus");
            cursusnummerTextfield.setText(String.valueOf(course.getId()));
            cursusnaamTextfield.setText(course.getCoursename());
            coordinatorIdTextfield.setText(String.valueOf(course.getCoordinatorid()));
        }
    }

    public void setCoordinatorid(int coordinatorid) {
        coordinatorIdTextfield.setText(String.valueOf(coordinatorid));

    }


    //nieuwe cursus aanmaken
    private void createCourse() {
        StringBuilder warningText = new StringBuilder();
        boolean correcteInvoer = true;
        String cursusnaam = cursusnaamTextfield.getText();
        int coordinatorid = Integer.parseInt(coordinatorIdTextfield.getText());

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
            course = new Course(cursusnaam, coordinatorid);
        }
    }


    @FXML
    public void doCreateUpdateCourse(ActionEvent actionEvent) {
        createCourse();
        if (course != null) {
            if (cursusnummerTextfield.getText().equals(("cursusnummer"))) {
                courseDAO.storeCourse(course);
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


    public void doMenu(ActionEvent e) {
        dbAccess.closeConnection();
        System.out.println("Connection closes");
        Main.getSceneManager().setWindowTool();
    }


}
