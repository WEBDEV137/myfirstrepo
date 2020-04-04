package controller;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import model.Question;
import model.Quiz;
import model.User;
import view.Main;

import java.util.*;

public class FillOutQuizController{
    private User user;
    private DBAccess dbAccess;
    private QuestionDAO questionDAO;
    private Quiz quiz;
    private  Question currentQuestion;
    private List<Question> questions;
    private  int questionNumber;
    private int questionIndex;



    @FXML
    private Label titleLabel;
    @FXML
    private TextArea questionArea;

    public void setup(Quiz quiz) {
        this.quiz = quiz;
        user = Main.getCurrentUser();
        dbAccess = Main.getDBaccess();
        questionDAO = new QuestionDAO(dbAccess);
        questions = questionDAO.getSelectedQuestionsByQuizId(quiz.getId());
        questionIndex = 0;
        questionNumber = 1;

        doCurrenQuestion();
    }
    public void doCurrenQuestion(){
        if(questions.get(questionIndex) != null){
            currentQuestion = questions.get(questionIndex);
            questionArea.setText(currentQuestion.getQuestionText());
            titleLabel.setText(String.format("Vraag %d", questionNumber));
        }
    }


    public void doNextQuestion() {
        questionIndex++;

        if (questionIndex < questions.size()) {
            questionNumber++;
            currentQuestion = questions.get(questionIndex);
            questionArea.setText(currentQuestion.getQuestionText());
            titleLabel.setText("Vraag" + questionIndex+1);
            titleLabel.setText(String.format("Vraag %d", questionNumber));
        }
        else{
            questionIndex--;
        }
    }

    public void doPreviousQuestion() {

            if (questionIndex > 0) {
            questionIndex --;
            questionNumber--;
            currentQuestion = questions.get(questionIndex);
            questionArea.setText(currentQuestion.getQuestionText());
            titleLabel.setText(String.format("Vraag %d", questionNumber));
        }
        else{

        }
    }
    public void doRegisterA() {
    }

    public void doRegisterB() {}

    public void doRegisterC() {}

    public void doRegisterD() {}






    public void doMenu() {
       Main.getSceneManager().showWelcomeScene(user);
    }
    /**
     * Confirmation dialogue
     * do you want to Delete quiz?
     */
    public void doGoMenuConfirmation() {
            String ARE_YOU_SURE = "U gaat de quiz verlaten";
            String CLICK_CONTINUE = "Weet u zeker dat u wilt doorgaan?";

            ButtonType jaKnop = new ButtonType("Ja", ButtonBar.ButtonData.YES);
            ButtonType neeKnop = new ButtonType("Nee", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert okCancelDialogue = new Alert(Alert.AlertType.WARNING, CLICK_CONTINUE, jaKnop, neeKnop);
            okCancelDialogue.setTitle(Main.QUISMASTER);
            okCancelDialogue.setHeaderText(ARE_YOU_SURE);
            okCancelDialogue.initModality(Modality.APPLICATION_MODAL); //Achtegrpond scherm wordt onbruikbaar gemaakt.
            okCancelDialogue.initOwner(Main.getPrimaryStage()); //show,
            Optional<ButtonType> result = okCancelDialogue.showAndWait();
            if (result.get() == jaKnop) {
                doMenu();
            }
            else if (!result.isPresent()){}

    }
}
