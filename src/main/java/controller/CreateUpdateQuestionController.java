package controller;

import database.mysql.AnswerDAO;
import database.mysql.QuestionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Answer;
import model.Question;
import view.Main;

import java.util.ArrayList;

public class CreateUpdateQuestionController {
    private Question newQuestion;
    private Answer newRightAnswer;
    private Answer newWrongAnswer1;
    private Answer newWrongAnswer2;
    private Answer newWrongAnswer3;
    private QuestionDAO questionDAO = new QuestionDAO(Main.getDBaccess());
    private AnswerDAO answerDAO = new AnswerDAO(Main.getDBaccess());
    private Question selectedQuestion;

    public Label titleLabel;
    public Label answerLabel;
    @FXML
    public TextField createUpdateRightAnswerTextField;
    public TextField createUpdateWrongAnswerTextField1;
    public TextField createUpdateWrongAnswerTextField2;
    public TextField createUpdateWrongAnswerTextField3;
    public TextField createUpdateQuestionTextField;
    public Button createUpdateAnswerButton;

    public void setup(Question question) {
        titleLabel.setText("Wijzig vraag");
        answerLabel.setText("Wijzig antwoorden");
        createUpdateAnswerButton.setText("Wijzig vraag en antwoorden");
        createUpdateQuestionTextField.setText(question.toString());
        ArrayList<Answer> answerList = new ArrayList<>(answerDAO.getAllAnswersByQuestionId(question.getQuestionID()));
        createUpdateRightAnswerTextField.setText(answerList.get(0).getText());
        createUpdateWrongAnswerTextField1.setText(answerList.get(1).getText());
        createUpdateWrongAnswerTextField2.setText(answerList.get(2).getText());
        if (!createUpdateWrongAnswerTextField2.getText().isEmpty()){
            createUpdateWrongAnswerTextField2.setDisable(false);
        }
        createUpdateWrongAnswerTextField3.setText(answerList.get(3).getText());
        if (!createUpdateWrongAnswerTextField3.getText().isEmpty()){
            createUpdateWrongAnswerTextField3.setDisable(false);
        }
        selectedQuestion = question;
    }

    @FXML
    private void createQuestion(){
        String newQuestion = createUpdateQuestionTextField.getText();
        if (newQuestion.isEmpty()) {
            Alert nothingFilledIn = new Alert(Alert.AlertType.ERROR);
            nothingFilledIn.setContentText("Je moet een vraag invullen.");
            nothingFilledIn.show();
            return;
        }
        this.newQuestion = new Question(newQuestion);
    }

    @FXML
    private void createRightAnswer(){
        String rightAnswer = createUpdateRightAnswerTextField.getText();
        this.newRightAnswer = new Answer(rightAnswer, true);
    }

    @FXML
    private void createWrongAnswer1(){
        String newWrongAnswer = createUpdateWrongAnswerTextField1.getText();
        this.newWrongAnswer1 = new Answer(newWrongAnswer, false);
    }

    @FXML
    private void createWrongAnswer2(){
        String newWrongAnswer = createUpdateWrongAnswerTextField2.getText();
        this.newWrongAnswer2 = new Answer(newWrongAnswer, false);
    }

    @FXML
    private void createWrongAnswer3(){
        String newWrongAnswer = createUpdateWrongAnswerTextField3.getText();
        this.newWrongAnswer3 = new Answer(newWrongAnswer, false);
    }

    @FXML
    private void enableWrongAnswerTextfield2(){
        createUpdateWrongAnswerTextField2.setDisable(false);
    }

    @FXML
    private void enableWrongAnswerTextfield3(){
        createUpdateWrongAnswerTextField3.setDisable(false);
    }

    @FXML
    public void doCreateUpdateQuestionAndAnswers() {
        createQuestion();
        createRightAnswer();
        createWrongAnswer1();
        if (newRightAnswer.getText().isEmpty() || newWrongAnswer1.getText().isEmpty()){
            Alert nothingFilledIn = new Alert(Alert.AlertType.ERROR);
            nothingFilledIn.setContentText("Je moet minstens één juist en één fout antwoord invullen.");
            nothingFilledIn.show();
            return;
        }
        if (newWrongAnswer2 != null) {createWrongAnswer2();}
        if (newWrongAnswer3 != null) {createWrongAnswer3();}
        if (selectedQuestion == null) {
            questionDAO.storeOne(newQuestion);
            answerDAO.storeOne(newRightAnswer, 1, questionDAO.getIdByQuestionText(newQuestion.toString()));
            answerDAO.storeOne(newWrongAnswer1, 0, questionDAO.getIdByQuestionText(newQuestion.toString()));
            if (newWrongAnswer2 != null) {answerDAO.storeOne(newWrongAnswer2, 0, questionDAO.getIdByQuestionText(newQuestion.toString()));}
            if (newWrongAnswer3 != null) {answerDAO.storeOne(newWrongAnswer3, 0, questionDAO.getIdByQuestionText(newQuestion.toString()));}
            Alert stored = new Alert(Alert.AlertType.INFORMATION);
            stored.setContentText("Vraag en antwoorden opgeslagen");
            stored.show();
        } else {
            questionDAO.updateQuestionByQuestionText(newQuestion.toString(), selectedQuestion.toString());
            Alert updated = new Alert(Alert.AlertType.INFORMATION);
            updated.setContentText("Vraag en antwoorden gewijzigd");
            updated.show();
        }
    }

    @FXML
    public void doBackToManage() {
        Main.getSceneManager().showManageQuestionsScene();
    }

    public void doMenu() { Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());}
}