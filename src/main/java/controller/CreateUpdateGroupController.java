package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Group;
import view.Main;

public class CreateUpdateGroupController {

    private GroupDAO groupDAO;
    private DBAccess dbAccess;


    @FXML
    private TextField groupNameTextfield;

    @FXML
    private MenuButton courseMenuButton;

    @FXML
    private MenuButton teacherMenuButton;

    @FXML
    private TextField groupNumber;

    @FXML
    private Label titleLabel;



    public void setup(Group group) {
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();


        if (group == null) {
            titleLabel.setText("Nieuwe groep aanmaken");
            // hier inzetten dat dropdown menu's gevuld moeten worden met lijst, aparte methode voor maken
        } else {
            titleLabel.setText("Groep wijzigen");
            groupNameTextfield.setText(group.getGroupName());
            courseMenuButton.getItems();
            teacherMenuButton.getItems();
        }

        }



public void populateCourseMenuButton() {
    CourseDAO courseDAO = new CourseDAO(dbAccess);

    allCourses = courseDAO.getAllCourses();

    MenuItem courseName = new MenuItem();
    courseMenuButton.getItems().add(courseName);
}

    public void populateUserMenuButton() {
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        UserDAO userDAO= new UserDAO(dbAccess);

        allUsers = userDAO.getAllUsers();
        getSelectedQuestionsByQuizId(quiz.getId());
        allQuizQuestions = questionDAO.getAllAvailableQuizQuestions(quiz.getId());

        MenuItem UserName = new MenuItem();
        teacherMenuButton.getItems().add(UserName);
    }

    public void doCreateUpdateGroup(ActionEvent event) {
    }




    @FXML
    public void doStore(ActionEvent actionEvent) {
    }

    @FXML
    public void doBackToList(ActionEvent actionEvent) {
/*        this.db.closeConnection();
        System.out.println("Connection closed");*/
        Main.getSceneManager().showManageGroupsScene();
    }

    @FXML
    public void doBackToMenu() {
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }



}