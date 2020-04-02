package controller;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Question;
import model.QuizResult;
import model.User;
import view.Main;

import java.util.List;

public class ManageQuestionsController {
    private QuestionDAO questionDAO;
    private DBAccess dbAccess;

    @FXML
    ListView<Question> questionList;

    public void setup() {
        this.questionDAO = new QuestionDAO(Main.getDBaccess());
        List<Question> questionList = questionDAO.getQuestionList();
        for (Question question :
                questionList) {
            this.questionList.getItems().add(question);
        }
    }

    public void doMenu(){
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }

    public void doCreateQuestion(){}

    public void doUpdateQuestion(){}

    public void doDeleteQuestion(){}
}
