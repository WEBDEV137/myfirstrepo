package controller;

import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.Quiz;
import view.Main;

import java.util.List;

public class ManageQuizzesController {
    private QuizDAO quizDAO;
    private DBAccess dbAccess;

    @FXML
    private ListView<Quiz> quizList;
    @FXML
    private Button newCourseButton;


    // connectie maken met dbase om courses te laten zien in het scherm listview
    public void setup() {
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        this.quizDAO = new QuizDAO(dbAccess);
        List<Quiz> allQuizzes = quizDAO.getAll();
        for (Quiz quiz : allQuizzes) {
            quizList.getItems().add(quiz);
        }

    }


    public void doMenu() {
    }

    //nieuwe cursus maken: doorgaan naar scherm createupdatecourse
    public void doCreateCourse() {
        Main.getSceneManager().showCreateUpdateCourseScene(null);
    }


    public void doUpdateCourse() {
    }

    public void doDeleteCourse() {
    }
}