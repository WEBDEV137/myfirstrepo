package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Course;
import model.Group;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class CreateUpdateUserController {
    private UserDAO udao;
    private DBAccess db;
    private User user;
    private ArrayList<String> rolList = new ArrayList<>();
    @FXML
    private Label gebruikerIsToegevoegd;
    @FXML
    public Label titleLabel;
    @FXML
    public TextField userIdTextfield;
    @FXML
    public TextField userNameTextfield;
    @FXML
    public TextField nameTextfield;
    @FXML
    public TextField prefixTextfield;
    @FXML
    public TextField surnameTextfield;
    @FXML
    public TextField passwordTextfield;
    @FXML
    public MenuButton roleMenuButton;

    public CreateUpdateUserController() {
        super();
        this.db = Main.getDBaccess();
        this.udao = new UserDAO(db);
    }

    public void setup(User user) {
        if (user == null) {
            titleLabel.setText("Nieuwe gebruiker aanmaken");
            rolList.add("Docent");
            rolList.add("Teknisch beherder");
            rolList.add("Student");
            rolList.add("Administrator");
            rolList.add("Coordinator");
            for (int i = 0; i < rolList.size(); i++) {
                String rolName = rolList.get(i);
                MenuItem menuItem = new MenuItem(String.valueOf(rolName));
                roleMenuButton.getItems().add(menuItem);
                menuItem.setOnAction(event -> {
                    roleMenuButton.setText(rolName);
                });
            }
        } else {
            titleLabel.setText("Update gebruiker");
            userIdTextfield.setText(String.valueOf(user.getUserId()));
            roleMenuButton.setText(String.valueOf(user.getRolName()));
            userNameTextfield.setText(user.getUserName());
            passwordTextfield.setText(user.getPassword());
            nameTextfield.setText(user.getName());
            prefixTextfield.setText(user.getPrefix());
            surnameTextfield.setText(user.getSurname());
            ArrayList<String> rolList = new ArrayList<>();
            rolList.add("Docent");
            rolList.add("Teknisch beherder");
            rolList.add("Student");
            rolList.add("Administrator");
            rolList.add("Coordinator");
            for (int i = 0; i < rolList.size(); i++) {
                String rolName = rolList.get(i);
                MenuItem menuItem = new MenuItem(String.valueOf(rolName));
                roleMenuButton.getItems().add(menuItem);
                menuItem.setOnAction(event -> {
                    roleMenuButton.setText(rolName);
                });
            }
        }
    }

    @FXML
    public void doMenu(ActionEvent e) {
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }

    @FXML
    public void doCreateUpdateUser(ActionEvent e) {
        createUser();
        if (user != null) {
            if (userIdTextfield.getText().equals(("Gebruiker id"))) {
                udao.storeOne(user);
                userIdTextfield.setText(String.valueOf(user.getUserId()));
                Main.getSceneManager().showCreateUpdateUserScene(null);
                Alert opgeslagen = new Alert(Alert.AlertType.INFORMATION);
                opgeslagen.setContentText("Gebruker opgeslagen");
                opgeslagen.show();

            } else {
                int id = Integer.valueOf(userIdTextfield.getText());
                user.setUserId(id);
                udao.updateUser(user);
                Main.getSceneManager().showCreateUpdateUserScene(null);
                Alert gewijzigd = new Alert(Alert.AlertType.INFORMATION);
                gewijzigd.setContentText("Gebruiker gewijzigd");
                gewijzigd.show();
            }
        }

    }

    private void createUser() {
        if (checkOfAllesingevuldIs()) {
            String userRolName = String.valueOf(roleMenuButton.getText());
            String userName = userNameTextfield.getText();
            String password = passwordTextfield.getText();
            String name = nameTextfield.getText();
            String prefix = prefixTextfield.getText();
            String surname = surnameTextfield.getText();
            user = new User(userRolName, userName, password, name, prefix, surname);
        }
    }

    private boolean checkOfAllesingevuldIs() {
        if (userNameTextfield.getText().equals("")) {
            gebruikerIsToegevoegd.setText("Je moet een inlognaam invullen!\n");
            return false;
        }
        if (nameTextfield.getText().equals("")) {
            gebruikerIsToegevoegd.setText("Je moet een voornaam invullen!\n");
            return false;
        }
        if (surnameTextfield.getText().equals("")) {
            gebruikerIsToegevoegd.setText("Je moet een achternaam invullen!\n");
            return false;
        }
        if (passwordTextfield.getText().equals("")) {
            gebruikerIsToegevoegd.setText("Je moet een wachtwoord invullen!\n");
            return false;
        }
        if (roleMenuButton.getText().equals("Kies rol")) {
            gebruikerIsToegevoegd.setText("Je moet een rol kiezen!");
            return false;
        }
        return true;
    }


    }
