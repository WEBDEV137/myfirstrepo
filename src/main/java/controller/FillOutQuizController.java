package controller;

import database.mysql.AnswerDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import model.*;
import view.Main;

import java.util.*;

public class FillOutQuizController{

    private DBAccess dbAccess;
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private QuestionResult questionResult;
    private User user;
    private Quiz quiz;
    private Question currentQuestion;
    private List<Question> questions;
    private List<Answer> answers;
    private int questionNumber;
    private int questionIndex;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;

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

        showFirstQuestion();
        getAnswersByQuestionId();
        setAnswersInQuestionResult();
        questionResult.setQuestionText(currentQuestion.getQuestionText());
    }
    private void showFirstQuestion(){
        if(questions.get(questionIndex) != null){
            currentQuestion = questions.get(questionIndex);
            questionArea.setText(currentQuestion.getQuestionText());
            titleLabel.setText(String.format("Vraag %d", questionNumber));
        }
    }
    private void getAnswersByQuestionId(){
        dbAccess = Main.getDBaccess();
        answerDAO = new AnswerDAO(dbAccess);
        QuestionResult questionResult = new QuestionResult();
        answers = answerDAO.getAllByQuestionId(currentQuestion.getQuestionID());


    }
    private void setAnswersInQuestionResult() {
        questionResult.setRightAnswer(answers.get(0).getText());
        questionResult.setWrongAnswer1(answers.get(1).getText());
        questionResult.setWrongAnswer1(answers.get(2).getText());
        questionResult.setWrongAnswer1(answers.get(3).getText());
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
            questionIndex--;
            questionNumber--;
            currentQuestion = questions.get(questionIndex);
            questionArea.setText(currentQuestion.getQuestionText());
            titleLabel.setText(String.format("Vraag %d", questionNumber));
        }
    }

    public ArrayList<String> randomizeAnswers(){
        int index = answers.size()-1;
        ArrayList<String> randomAnswers = new ArrayList<>();
        while(index >=0 ) {
            int randomNumber = ((int)Math.random() * index);
            String answerToshuffle = answers.get(randomNumber).getText();
            randomAnswers.add(answerToshuffle);
            index--;
        }return randomAnswers;
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
     * do you want to Leave Quiz?
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
