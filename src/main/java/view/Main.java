package view;


//Dit programma is gemaakt als opdracht voor MIW cohort 19, Het is gemaakt door, Tom , Joelle, Minke, Umrullah, En Suzanne.
// Met dit programma kunnen gebruikers alles omtrent cursussen beheren.

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Quiz;
import model.User;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class Main extends Application {
    //CONSTANTS
    public static final String QUISMASTER = "Quismaster";

    //Object declaratie
    private static SceneManager sceneManager = null;
    private static Stage primaryStage = null;
    private static DBAccess dbAccess = null;
    private static User currentUser = null;

    public static void setCurrentUser(User currentUser) {
        Main.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void main(String[] args) {
        launch(args); }

    /**
     * Deze methode geeft het DBAccess object terug als die al is aangemaakt. Anders wordt die eerste aangemaakt en dan
     * teruggegeven.
     * */
    public static DBAccess getDBaccess() {
        if (dbAccess == null) {
            dbAccess = new DBAccess("quizmaster", "userQuizmaster", "pwQuizmaster");
        }
        return dbAccess;
    }


    /**
     *Deze methode wordt automatisch gestart
     *
     */
    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        primaryStage.setTitle("Make IT Work - Project 1");
        getSceneManager().setWindowTool();
        primaryStage.show();
    }

    public static SceneManager getSceneManager() {
        if (sceneManager == null) {
            sceneManager = new SceneManager(primaryStage);
        }
        return sceneManager;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

}