package view;


import controller.LoginController;
import database.mysql.DBAccess;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/WindowTool.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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