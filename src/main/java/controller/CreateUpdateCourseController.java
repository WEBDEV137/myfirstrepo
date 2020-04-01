package controller;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Course;
import view.Main;

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
    private TextField coordinatorIdTextfield;

    public CreateUpdateCourseController() {
        super();
        this.courseDAO = new CourseDAO(dbAccess);
        this.dbAccess = Main.getDBaccess();
    }

    private void createCourse() {
        StringBuilder warningText = new StringBuilder();
        boolean correcteInvoer = true;
        String cursusnaam = cursusnaamTextfield.getText();
        int coordinatoridnr = Integer.parseInt(coordinatorIdTextfield.getText());

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
            course = new Course(cursusnaam, coordinatoridnr);
        }
    }

    @FXML
    public void doStore(ActionEvent actionEvent) {
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







    public void setup(Course course) {
    }

    public void doMenu() {
    }

    public void doCreateUpdateCourse() {
    }
}
