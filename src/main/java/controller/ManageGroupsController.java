package controller;

import com.mysql.cj.log.Log;
import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Group;
import model.User;
import view.Main;

import java.util.Iterator;
import java.util.List;

public class ManageGroupsController {

    private GroupDAO groupDAO;
    private DBAccess dbAccess;

    @FXML
    private ListView<Group> groupList;

// moet hier nog een warningText?

    public void setup() {
        Main.getDBaccess().openConnection();
        GroupDAO groupDAO = new GroupDAO(Main.getDBaccess());
        try {
            List<Group> allGroups = groupDAO.getAllGroupsByName();
            Iterator i = allGroups.iterator();
            while (i.hasNext()) {
                Group g = (Group) i.next();
                groupList.getItems().add(g);
            }
        }
        catch (Exception fout){
            fout.getMessage();
        }
    }



    /*       wanneer deze: dBaccess.closeConnection(); ??? */

    @FXML
    public void doMenu(ActionEvent event) {
/*        // hoe krijg je hier de ingelogde user in?
        User user = ;
        Main.getSceneManager().showWelcomeScene(User user);*/
        Main.getSceneManager().showLoginScene();
    }


    public void doCreateGroup() {}

    public void doUpdateGroup() {}

    public void doDeleteGroup(ActionEvent event) {
        Group group = (Group)this.groupList.getSelectionModel().getSelectedItems();
        if (group == null) {
            Alert niksGeselecteerdFout = new Alert(Alert.AlertType.ERROR);
            niksGeselecteerdFout.setContentText("Selecteer een groep.");
            niksGeselecteerdFout.show();
        } else {
            GroupDAO groupDAO = new GroupDAO(Main.getDBaccess());
            this.groupDAO.deleteGroup(group);
        }
    }


}
