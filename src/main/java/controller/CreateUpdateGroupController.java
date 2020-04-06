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
        if (group == null) {
            titleLabel.setText("Nieuwe groep aanmaken");
            for (int i = 0; i < allCourses.size(); i++) {
                String coursename = allCourses.get(i).getCoursename();
                MenuItem menuItem = new MenuItem(coursename);
                courseMenuButton.getItems().add(menuItem);
                menuItem.setOnAction(e -> setCoursename(coursename));
            }
            for (int ib = 0; ib < allUsers.size(); ib++) {
                String teachername = allUsers.get(ib).getUserName();
                MenuItem menuItem = new MenuItem(teachername);
                teacherMenuButton.getItems().add(menuItem);
                menuItem.setOnAction(e -> setTeachername(teachername));
            }
        } else {
            titleLabel.setText("Groep wijzigen");
            for (int ib = 0; ib < allUsers.size(); ib++) {
                String teachername = allUsers.get(ib).getUserName();
                MenuItem menuItem = new MenuItem(teachername);
                teacherMenuButton.getItems().add(menuItem);
                menuItem.setOnAction(e -> setTeachername(teachername));
            }
                groupNumberTextfield.setText(String.valueOf(group.getGroupId()));
                groupNameTextfield.setText(group.getGroupName());
                courseMenuButton.setText(courseDAO.getCourseNameById(group.getCourseId()));
                teacherMenuButton.setText(userDAO.getUserNameById(group.getUserId()));
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


    @FXML // wordt geactiveerd met de save-knop
    public void doCreateUpdateGroup(ActionEvent event) {
        createGroup();
        DBAccess dbAccess = Main.getDBaccess();
        GroupDAO groupDAO = new GroupDAO(dbAccess);
        if (group != null) { //in geval van groep aanmaken
            if (groupNumberTextfield.getText().equals(("groepnummer"))) {
                groupDAO.storeGroup(group);
                groupNumberTextfield.setText(String.valueOf(group.getGroupId()));
                Alert opgeslagen = new Alert(Alert.AlertType.INFORMATION);
                opgeslagen.setContentText("Groep opgeslagen");
                opgeslagen.show();
            } else { // in geval van groep wijzigen
                int groupId = Integer.parseInt(groupNumberTextfield.getText());
                group.setGroupId(groupId);
                groupDAO.updateGroup(group);
                Alert gewijzigd = new Alert(Alert.AlertType.INFORMATION);
                gewijzigd.setContentText("Groep gewijzigd");
                gewijzigd.show();
            }
        }
    }

//
    private void createGroup() {
        StringBuilder warningText = new StringBuilder();
        boolean correcteInvoer = true;
        UserDAO userDAO = new UserDAO(dbAccess);
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        int userId;
        int courseId;
        String groupName = groupNameTextfield.getText();
        String courseName = courseMenuButton.getText();
        String userName = teacherMenuButton.getText();
        userId = userDAO.getUserIdByLoginName(userName);
        courseId = courseDAO.getCourseIdByName(courseName);
        if (groupName.isEmpty()) {
            warningText.append("Vul een groepnaam in.\n");
            correcteInvoer = false;
        }
        if ("Kies cursus".equals(courseMenuButton.getText())){
            warningText.append("Kies een cursusnaam.\n");
            correcteInvoer = false;
        } else {
            correcteInvoer = true;
        }
        if ("Kies docent".equals(teacherMenuButton.getText())){
            warningText.append("Kies een docentnaam.\n");
            correcteInvoer = false;
        } else {
            correcteInvoer = true;
        }
        if (!correcteInvoer) {
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText(warningText.toString());
            foutmelding.show();
            group = null;
        } else {
            group = new Group(groupName, userId, courseId);
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