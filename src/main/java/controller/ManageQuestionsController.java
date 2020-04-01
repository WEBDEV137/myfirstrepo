package controller;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Question;
import model.QuizResult;

import java.util.List;

public class ManageQuestionsController {
    private QuestionDAO questionDAO;
    private DBAccess dbAccess;

    @FXML
    ListView<Question> questionList;

    @FXML
    TextField warningText;

    public void setup() {
        this.questionDAO = new QuestionDAO(dbAccess);
        List<Question> allQuestions = questionDAO.getQuestionList();
        for (Question question :
                allQuestions) {
            questionList.getItems().add(question);
        }
        
    }

    public void doMenu(){}

    public void doCreateQuestion(){}

    public void doUpdateQuestion(){}

    public void doDeleteQuestion(){}
}
