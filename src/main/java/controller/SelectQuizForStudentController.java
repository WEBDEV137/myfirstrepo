package controller;

import database.mysql.AbstractDAO;
import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Question;
import model.Quiz;
import model.User;
import view.Main;
import view.SceneManager;

import java.util.ArrayList;

public class SelectQuizForStudentController{
    private User user;
    private DBAccess dbAccess;
    private QuizDAO quizDAO;
    ArrayList<Quiz> quizzes;

    @FXML
    ListView<Quiz> quizList;

    public void setup() {
        User user = Main.getCurrentUser();
        dbAccess = Main.getDBaccess();
        quizDAO = new QuizDAO(dbAccess);
        quizzes = quizDAO.getAllByUserId(user.getUserId());

        Coll.populateListView(quizList, quizzes);
     }

    public void doMenu() {
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }

    public void doQuiz() {
       Quiz selectedQuiz = quizList.getSelectionModel().getSelectedItem();
        Main.getSceneManager().showFillOutQuiz(selectedQuiz);
    }
}
