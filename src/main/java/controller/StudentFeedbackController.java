package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.QuestionResult;
import model.QuizResult;
import view.Main;

import java.util.List;

public class StudentFeedbackController {

    private String quizName;
    private int numberOfQuestions;
    private int countCorrectAnswers;
    private int succesDefinition;
    private boolean quizIsPassed;
    private List<QuestionResult> questionResults;

    @FXML
    private Label feedbackLabel;
    @FXML
    private ListView<String> feedbackList;

    public void setup(QuizResult quizResult) {
        this.quizName = quizResult.getQuizName();
        this.numberOfQuestions = quizResult.getCountQuestions();
        this.countCorrectAnswers = quizResult.getCountCorrectAnswers();
        this.succesDefinition = quizResult.getSuccesDefinition();
        this.quizIsPassed = checkQuizPassed(quizResult);
        this.questionResults = quizResult.getQuestionResults();

        feedbackLabel.setText(quizName);
        feedbackList.getItems().add(createFeedbackText());
    }

    public void doMenu() {
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }

    public boolean checkQuizPassed(QuizResult quizResult){
        if (quizResult.getCountCorrectAnswers() >= quizResult.getSuccesDefinition()){
           return true;
        }
        else{
            return false;
        }
    }
    public String createFeedbackText(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Quiz: ").append(quizName).append("\n");
        stringBuilder.append("U heeft ").append(countCorrectAnswers);
        stringBuilder.append(" van de ").append(numberOfQuestions);
        stringBuilder.append(" vragen correct beantwoord. \n");
        stringBuilder.append("Benodigd aantal correcte antwoorden voor slagen: ").append(succesDefinition).append("\n");
        if(quizIsPassed){
            stringBuilder.append("U bent geslaagd voor de quiz.\n"
            );}
        else{
            stringBuilder.append("U bent niet geslaagd voor de quiz.\n");
        }
        for (int index = 0; index < questionResults.size(); index++) {
            stringBuilder.append("Vraag ").append(index+1).append(": ");
            stringBuilder.append(questionResults.get(index).getQuestionText()).append("\n");
            stringBuilder.append("Correct antwoord: ").append(questionResults.get(index).getRightAnswer()).append("\n");
            stringBuilder.append("Opgegeven antwoord: ").append(questionResults.get(index).getAnswer()).append("\n");
        }
        return stringBuilder.toString();
    }
}

