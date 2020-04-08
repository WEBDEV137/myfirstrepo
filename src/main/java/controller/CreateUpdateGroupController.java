package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import database.mysql.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Course;
import model.Group;
import model.User;
import view.Main;

import java.util.List;

public class CreateUpdateGroupController {

    private DBAccess dbAccess;
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

    // setup voor CRUD-scherm voor nieuwe groep en voor groep wijzigen
    public void setup(Group group) {
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        UserDAO userDAO = new UserDAO(dbAccess);
        List<Course> allCourses = courseDAO.getAll();
        List<User> allUsers = userDAO.getUsersByRole("coordinator");
        List<User> teachers = userDAO.getUsersByRole("docent");
        for(User user : teachers){
            allUsers.add(user);
        }
        if (group == null) {
            titleLabel.setText("Nieuwe groep aanmaken");
            setupTeacherMenuButton(allUsers);
            setupCourseMenuButtonNewGroup(allCourses);
        } else {
            titleLabel.setText("Groep wijzigen");
            setupTeacherMenuButton(allUsers);
            for (int i = 0; i < allCourses.size(); i++) {
                String coursename = allCourses.get(i).getCoursename();
                MenuItem menuItem = new MenuItem(coursename);
                courseMenuButton.getItems().add(menuItem);
                menuItem.setOnAction(e -> setCoursename(coursename));
                courseMenuButton.setText(courseDAO.getCourseNameById(group.getCourseId()));
                alertCourseNotUpdatable(menuItem);
            }
            groupNumberTextfield.setText(String.valueOf(group.getGroupId()));
            groupNameTextfield.setText(group.getGroupName());
            teacherMenuButton.setText(userDAO.getUserNameById(group.getUserId()));
        }
    }

// hulpmethode om MenuButton Docent te vullen met lijst
    private void setupTeacherMenuButton(List<User> allUsers) {
        for (int ib = 0; ib < allUsers.size(); ib++) {
            String teachername = allUsers.get(ib).getUserName();
            MenuItem menuItem = new MenuItem(teachername);
            teacherMenuButton.getItems().add(menuItem);
            menuItem.setOnAction(e -> setTeachername(teachername));
        }
    }

    // hulpmethode om MenuButton Course te vullen met lijst
    private void setupCourseMenuButtonNewGroup(List<Course> allCourses){
        for (int i = 0; i < allCourses.size(); i++) {
            String coursename = allCourses.get(i).getCoursename();
            MenuItem menuItem = new MenuItem(coursename);
            courseMenuButton.getItems().add(menuItem);
            menuItem.setOnAction(e -> setCoursename(coursename));
        }
    }

    // toon alert wanneer gebruiker course wil wijzigen
    private void alertCourseNotUpdatable(MenuItem menuItem) {
        menuItem.setOnAction(e -> {
            Alert opgeslagen = new Alert(Alert.AlertType.INFORMATION);
            opgeslagen.setContentText("Je kunt de cursus niet wijzigen. Maak een nieuwe groep.");
            opgeslagen.show();
        });
    }

    public void alertSetupCRUD(String string){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(string);
        alert.show();
    }

// hulpmethodes om gekozen naam in te stellen als tekst van MenuButton

public void setCoursename(String coursename){
    courseMenuButton.setText(coursename);
}

public void setTeachername(String teachername){
    teacherMenuButton.setText(teachername);
    }


    // Save-knop
    @FXML
    public void doCreateUpdateGroup() {
        createGroup();
        DBAccess dbAccess = Main.getDBaccess();
        GroupDAO groupDAO = new GroupDAO(dbAccess);
        if (group != null) { //in geval van groep aanmaken
            if (groupNumberTextfield.getText().equals(("groepnummer"))) {
                groupDAO.storeOne(group);
                groupNumberTextfield.setText(String.valueOf(group.getGroupId()));
                alertSetupCRUD("Groep opgeslagen");
            } else { // in geval van groep wijzigen
                int groupId = Integer.parseInt(groupNumberTextfield.getText());
                group.setGroupId(groupId);
                groupDAO.updateGroup(group);
                alertSetupCRUD("Groep gewijzigd");
            }
        }
    }

    // nieuwe groep aanmaken
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
            Alert foutmelding = new Alert(Alert.AlertType.INFORMATION);
            foutmelding.setContentText(warningText.toString());
            foutmelding.show();
            group = null;
        } else {
            group = new Group(groupName, userId, courseId);
        }
    }


    @FXML
    public void doBackToList() {
        Main.getSceneManager().showManageGroupsScene();
    }

    @FXML
    public void doBackToMenu() {
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }


}