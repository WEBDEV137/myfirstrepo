package view;


//Dit programma is gemaakt als opdracht voor MIW cohort 19, Het is gemaakt door, Tom , Joelle, Minke, Umrullah, En Suzanne.
// Met dit programma kunnen gebruikers alles omtrent cursussen beheren.

import controller.AbstractController;
import controller.LoginController;
import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;

public class Main extends Application {

    //Object declaratie
    private static SceneManager sceneManager = null;
    private static Stage primaryStage = null;
    private static DBAccess dbAccess = null;

    public static void main(String[] args) {
        launch(args);
    }

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

<<<<<<< HEAD
    /**
     *Deze methode wordt automatisch gestart
     *
     */
=======

>>>>>>> suzanne

    @Override
    public void start(Stage primaryStage) {
        System.out.println(primaryStage);
        Main.primaryStage = primaryStage;
<<<<<<< HEAD
        primaryStage.setTitle("Make IT Work - Project 1");
=======
        //primaryStage.setTitle("Make IT Work - Project 1");
>>>>>>> suzanne
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