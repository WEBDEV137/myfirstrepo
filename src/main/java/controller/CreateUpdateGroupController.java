package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Course;
import model.Group;
import model.Question;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreateUpdateGroupController extends AbstractController {

    private GroupDAO groupDAO;
    private DBAccess dbAccess;
    private String coursename;
    private String teachername;
    private Group group;


    @FXML
    private TextField groupNameTextfield;

    @FXML
    private MenuButton courseMenuButton;

    @FXML
    private MenuButton teacherMenuButton;

    @FXML
    private TextField groupNumberTextfield;

    @FXML
    private Label titleLabel;


    public void setup(Group group) {
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        UserDAO userDAO = new UserDAO(dbAccess);
            List<Course> allCourses = courseDAO.getAllCourses();
            List<User> allUsers = userDAO.getUsersByRole("docent");
            for (int i = 0; i < allCourses.size(); i++) {
                String coursename = allCourses.get(i).getCoursename();
                MenuItem menuItem = new MenuItem(coursename);
                courseMenuButton.getItems().add(menuItem);
                menuItem.setOnAction(e -> setCoursename(coursename));
            }
            for (int i2 = 0; i2 < allUsers.size(); i2++) {
                String teachername = allUsers.get(i2).getUserName();
                MenuItem menuItem = new MenuItem(teachername);
                teacherMenuButton.getItems().add(menuItem);
                menuItem.setOnAction(e -> setTeachername(teachername));
            }
        if (group == null) {
            titleLabel.setText("Nieuwe groep aanmaken");
        } else {
            titleLabel.setText("Groep wijzigen");
            groupNameTextfield.setText(group.getGroupName());
/*            courseMenuButton.getItems();
            teacherMenuButton.getItems();*/
        }
    }


public void setCoursename(String coursename){
        this.coursename = coursename;
        courseMenuButton.setText(coursename);
}

public void setTeachername(String teachername){
        this.teachername = teachername;
        teacherMenuButton.setText(teachername);
    }


    @FXML
    public void doCreateUpdateGroup(ActionEvent event) {
        createGroup();
        if (group != null) {
            if (groupNumberTextfield.getText().equals(("groepnummer"))) {
                groupDAO.storeGroup(group);
                groupNumberTextfield.setText(String.valueOf(group.getGroupId()));
                Alert opgeslagen = new Alert(Alert.AlertType.INFORMATION);
                opgeslagen.setContentText("Groep opgeslagen");
                opgeslagen.show();
            } else {
                int id = Integer.parseInt(groupNumberTextfield.getText());
                group.setGroupId(id);
                groupDAO.updateGroup(group);
                Alert gewijzigd = new Alert(Alert.AlertType.INFORMATION);
                gewijzigd.setContentText("Groep gewijzigd");
                gewijzigd.show();
            }
        }
    }


    private void createGroup() {
        StringBuilder warningText = new StringBuilder();
        boolean correcteInvoer = true;
        String groupName = groupNameTextfield.getText();
        String cursusId = courseMenuButton.getText();
        String userId = teacherMenuButton.getText();

        if (groupName.isEmpty()) {
            warningText.append("Vul een groepnaam in.\n");
            correcteInvoer = false;
        }
        if (cursusId.isEmpty()) {
            warningText.append("Kies een cursusnummer.\n");
            correcteInvoer = false;
        }
        if (!correcteInvoer) {
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText(warningText.toString());
            foutmelding.show();
            group = null;
        } else {
            group = new Group(groupName, Integer.parseInt(cursusId), Integer.parseInt(userId));
        }
    }


    @FXML
    public void doBackToList(ActionEvent actionEvent) {
        Main.getSceneManager().showManageGroupsScene();
    }

    @FXML
    public void doBackToMenu() {
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }



}