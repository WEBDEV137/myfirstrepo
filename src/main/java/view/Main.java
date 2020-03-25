package view;


import controller.LoginController;
import database.mysql.DBAccess;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    //VARIABELEN
    private static SceneManager sceneManager = null;
    private static Stage primaryStage = null;

    //DbAccess
    private static DBAccess dbAccess = null;


    //METHODEN

    public static void main(String[] args) {
        launch(args);
    }

    //create DBAccess Object voor verbinding met database
    public static DBAccess getDBaccess() {
        if (dbAccess == null) {
            dbAccess = new DBAccess("quizmaster", "userQuizmaster", "pwQuizmaster");
        }
        return dbAccess;
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        //primaryStage.setTitle("Make IT Work - Project 1");
        getSceneManager().showLoginScene();
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