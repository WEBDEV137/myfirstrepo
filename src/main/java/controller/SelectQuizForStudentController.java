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

import java.util.ArrayList;

public class SelectQuizForStudentController extends AbstractController {
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



       // populateListView(quizList, quizzes);
        for (Quiz quiz : quizzes) {
            System.out.println(quiz);
            quizList.getItems().add(quiz);
        }
    }


    public void doMenu() {}

    public void doQuiz() {}
}
