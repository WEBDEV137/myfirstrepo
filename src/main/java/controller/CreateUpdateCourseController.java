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

import java.util.List;

public class CreateUpdateCourseController {
    private CourseDAO courseDAO;
    private UserDAO userDAO;
    private DBAccess dbAccess;
    private Course course;


    @FXML
    private Label titleLabel;
    @FXML
    private TextField cursusnummerTextfield;
    @FXML
    private TextField cursusnaamTextfield;
    @FXML
    private MenuButton coordinatornaamTextfield;


    public CreateUpdateCourseController() {
        super();
        this.courseDAO = new CourseDAO(this.dbAccess);
        this.dbAccess = Main.getDBaccess();
    }

    /**
     * opstarten CRUD scherm.
     *
     * @param course
     */
    public void setup(Course course) {
        this.dbAccess = Main.getDBaccess();
        this.dbAccess.openConnection();
        this.userDAO = new UserDAO(this.dbAccess);
        List<User> allUsers = this.userDAO.getUsersByRole("Coordinator");
        if (course == null) {
            this.titleLabel.setText("Nieuwe cursus aanmaken");
            showScreen(allUsers);
        } else {
            this.titleLabel.setText("Cursus wijzigen");
            showScreen(allUsers);
            showSelectedCourse(course);

        }
    }


    private void showScreen(List<User> allUsers) {
        for (int i = 0; i < allUsers.size(); i++) {
            String coordinatorname = allUsers.get(i).getUserName();
            MenuItem menuItem = new MenuItem(coordinatorname);
            this.coordinatornaamTextfield.getItems().add(menuItem);
            menuItem.setOnAction(event -> this.setCoordinatorName(coordinatorname));
        }
    }

    /**
     * Laat gegevens zien van de gekozen cursus     *
     *
     * @param course
     */
    private void showSelectedCourse(Course course) {
        this.cursusnummerTextfield.setText(String.valueOf(course.getId()));
        this.cursusnaamTextfield.setText(course.getCoursename());
        this.coordinatornaamTextfield.setText(this.userDAO.getUserNameById(course.getCoordinatorid()));
    }

    public void setCoordinatorName(String coordinatorName) {
        this.coordinatornaamTextfield.setText(coordinatorName);

    }


    /**
     * Nieuwe cursus maken
     */
    private void createCourse() {
        StringBuilder warningText = new StringBuilder();
        boolean correcteInvoer = true;
        UserDAO userDAO = new UserDAO(this.dbAccess);
        CourseDAO courseDAO = new CourseDAO(this.dbAccess);
        int userId;
        int courseId;
        String cursusnaam = this.cursusnaamTextfield.getText();
        String userName = this.coordinatornaamTextfield.getText();
        userId = userDAO.getUserIdByLoginName(userName);

        if (cursusnaam.isEmpty()) {
            warningText.append("Je moet de cursusnaam invullen\n");
            correcteInvoer = false;
        }
        if (!correcteInvoer) {
            showConfirmation(Alert.AlertType.ERROR, warningText.toString());
            course = null;
        } else {
            course = new Course(cursusnaam, userId);
        }
    }


    @FXML
    public void doCreateUpdateCourse(ActionEvent actionEvent) {
        this.createCourse();
        DBAccess dbAccess = Main.getDBaccess();
        CourseDAO courseDAO = new CourseDAO(dbAccess);

        if (course != null) {
            if (this.cursusnummerTextfield.getText().equals(("cursusnummer"))) {
                System.out.println(course.getCoursename());
                courseDAO.storeCourse(this.course);
                this.cursusnummerTextfield.setText(String.valueOf(course.getId()));
                showConfirmation(Alert.AlertType.INFORMATION, "Cursus opgeslagen");
            } else {
                int id = Integer.parseInt(this.cursusnummerTextfield.getText());
                this.course.setId(id);
                courseDAO.updateCourse(this.course);
                showConfirmation(Alert.AlertType.INFORMATION, "Cursus gewijzigd");
            }
        }
    }

    private void showConfirmation(Alert.AlertType information, String s) {
        Alert opgeslagen = new Alert(information);
        opgeslagen.setContentText(s);
        opgeslagen.show();
    }

    @FXML
    public void doBackToList(ActionEvent actionEvent) {
        Main.getSceneManager().showManageCoursesScene();

    }

    @FXML
    public void doMenu(ActionEvent e) {
        dbAccess.closeConnection();
        System.out.println("Connection closes");
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }


}
