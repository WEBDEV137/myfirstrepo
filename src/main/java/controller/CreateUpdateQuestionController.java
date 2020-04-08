package controller;

import database.mysql.AnswerDAO;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Answer;
import model.Question;
import model.Quiz;
import view.Main;

import java.util.ArrayList;

public class CreateUpdateQuestionController {
    private QuestionDAO questionDAO = new QuestionDAO(Main.getDBaccess());
    private AnswerDAO answerDAO = new AnswerDAO(Main.getDBaccess());
    private QuizDAO quizDAO = new QuizDAO(Main.getDBaccess());
    private Question newQuestion;
    private Answer newRightAnswer;
    private Answer newWrongAnswer1;
    private Answer newWrongAnswer2;
    private Answer newWrongAnswer3;
    private Question selectedQuestion;
    private Quiz selectedQuiz;
    private String selectedQuizName;

    @FXML
    public Label titleLabel;
    public Label answerLabel;
    public TextField createUpdateRightAnswerTextField;
    public TextField createUpdateWrongAnswerTextField1;
    public TextField createUpdateWrongAnswerTextField2;
    public TextField createUpdateWrongAnswerTextField3;
    public TextField createUpdateQuestionTextField;
    public Button createUpdateAnswerButton;
    public MenuButton quizMenuButton;

    public void setup(Question question) {
        if (question != null){
            titleLabel.setText("Wijzig vraag");
            answerLabel.setText("Wijzig antwoorden");
            createUpdateAnswerButton.setText("Wijzig vraag en antwoorden");
            createUpdateQuestionTextField.setText(question.toString());
            ArrayList<Answer> answerList = getAnswerListBySelectedQuestion(question);
            if (!createUpdateWrongAnswerTextField2.getText().isEmpty()){
                createUpdateWrongAnswerTextField2.setDisable(false);
            }
            createUpdateWrongAnswerTextField3.setText(answerList.get(3).getText());
            if (!createUpdateWrongAnswerTextField3.getText().isEmpty()){
                createUpdateWrongAnswerTextField3.setDisable(false);
            }
            quizMenuButton.setText(quizDAO.getOneById(question.getQuizID()).getName());
            selectedQuizName = quizDAO.getOneById(question.getQuizID()).getName();
            selectedQuestion = question;
        } fillQuizDropdownMenu();
    }

    private ArrayList<Answer> getAnswerListBySelectedQuestion(Question question) {
        ArrayList<Answer> answerList = new ArrayList<>(answerDAO.getAllAnswersByQuestionId(question.getQuestionID()));
        createUpdateRightAnswerTextField.setText(answerList.get(0).getText());
        createUpdateWrongAnswerTextField1.setText(answerList.get(1).getText());
        createUpdateWrongAnswerTextField2.setText(answerList.get(2).getText());
        return answerList;
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
        if (selectedQuiz == null || selectedQuizName.isEmpty()){
            Alert nothingFilledIn = new Alert(Alert.AlertType.ERROR);
            nothingFilledIn.setContentText("Je moet een quiz selecteren.");
            nothingFilledIn.show();
            return;
        }
        this.newQuestion.setQuizID(selectedQuiz.getId());
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
    public void doCreateUpdateQuestionAndAnswers() {
        createQuestion();
        if (newRightAnswer.getText().isEmpty() || newWrongAnswer1.getText().isEmpty()){
            Alert nothingFilledIn = new Alert(Alert.AlertType.ERROR);
            nothingFilledIn.setContentText("Je moet minstens één juist en één fout antwoord invullen.");
            nothingFilledIn.show();
            return;
        }
        createRightAnswer();
        createWrongAnswer1();
        if (newWrongAnswer2 != null) {createWrongAnswer2();}
        if (newWrongAnswer3 != null) {createWrongAnswer3();}
        if (selectedQuestion == null) {
            storeNewQuestionAndAnswers();
        } else {
            updateQuestionAndAnswers();
        }
    }

    private void storeNewQuestionAndAnswers() {
        newQuestion.setQuizID(selectedQuiz.getId());
        questionDAO.storeOne(newQuestion);
        newRightAnswer.setQuestionId(questionDAO.getIdByQuestionText(newQuestion.toString()));
        answerDAO.storeOne(newRightAnswer);
        newWrongAnswer1.setQuestionId(questionDAO.getIdByQuestionText(newQuestion.toString()));
        answerDAO.storeOne(newWrongAnswer1);
        if (newWrongAnswer2 != null) {
            newWrongAnswer2.setQuestionId(questionDAO.getIdByQuestionText(newQuestion.toString()));
            answerDAO.storeOne(newWrongAnswer2);}
        if (newWrongAnswer3 != null) {
            newWrongAnswer3.setQuestionId(questionDAO.getIdByQuestionText(newQuestion.toString()));
            answerDAO.storeOne(newWrongAnswer3);}
        Alert stored = new Alert(Alert.AlertType.INFORMATION);
        stored.setContentText("Vraag en antwoorden opgeslagen");
        stored.show();
    }

    private void updateQuestionAndAnswers() {
        questionDAO.updateQuestionByQuestionText(newQuestion.toString(), selectedQuestion.toString());
        answerDAO.updateAnswerByAnswerText(newRightAnswer.toString(), createUpdateRightAnswerTextField.getText());
        answerDAO.updateAnswerByAnswerText(newWrongAnswer1.toString(), createUpdateWrongAnswerTextField1.getText());
        answerDAO.updateAnswerByAnswerText(newWrongAnswer2.toString(), createUpdateWrongAnswerTextField2.getText());
        answerDAO.updateAnswerByAnswerText(newWrongAnswer3.toString(), createUpdateWrongAnswerTextField3.getText());
        Alert updated = new Alert(Alert.AlertType.INFORMATION);
        updated.setContentText("Vraag en antwoorden gewijzigd");
        updated.show();
    }

    @FXML
    private void fillQuizDropdownMenu (){
        ArrayList<Quiz> quizList = quizDAO.getAll();
        for (Quiz quiz :
                quizList) {
            MenuItem menuItem = new MenuItem(quiz.getName());
            menuItem.setOnAction(actionEvent -> selectedQuiz = quiz);
            quizMenuButton.getItems().add(menuItem);
        }
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
    public void doBackToManage() {
        Main.getSceneManager().showManageQuestionsScene();
    }

    public void doMenu() { Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());}
}