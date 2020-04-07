package controller;

import database.mysql.AnswerDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class CreateUpdateQuestionController{
    private DBAccess dbAccess;
    private User user;
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private QuizDAO quizDAO;
    private Quiz quiz;
    private Question question;
    private boolean isNewCourse = false;
    private boolean filledIn = true;

    private Question selectedQuestion;
    @FXML
    public Label titleLabel;
    @FXML
    public Label answerLabel;
    @FXML
    public TextField createUpdateRightAnswerTextField;
    @FXML
    public TextField createUpdateWrongAnswerTextField1;
    @FXML
    public TextField createUpdateWrongAnswerTextField2;
    @FXML
    public TextField createUpdateWrongAnswerTextField3;
    @FXML
    public TextField createUpdateQuestionTextField;
    @FXML
    public MenuButton quizButton;

    public void setup(Question question) {
        user = Main.getCurrentUser();
        dbAccess = Main.getDBaccess();
        questionDAO = new QuestionDAO(dbAccess);
        answerDAO = new AnswerDAO(dbAccess);
        quizDAO = new QuizDAO(dbAccess);
        quiz = new Quiz();

        if (question == null) {
            this.question = new Question();
            isNewCourse = true;
            setupCreateNewQuestion();
        } else {
            this.question = question;
            setupUpdateQuestion();
        }
    }
    
    public void setupUpdateQuestion() {
        titleLabel.setText("Wijzig vraag");
        answerLabel.setText("Wijzig antwoorden");
        createUpdateQuestionTextField.setText(question.toString());
        quiz = quizDAO.getOneById(question.getQuizID());
        populateQuizMenuButton();
        quizButton.setText(quiz.getName());
        ArrayList<Answer> answerList = answerDAO.getAllByQuestionId(question.getQuestionID());
        if (answerList.size() > 3) {
            createUpdateWrongAnswerTextField3.setText(answerList.get(3).getText());
        }
        if (answerList.size() > 2) {
            createUpdateWrongAnswerTextField2.setText(answerList.get(2).getText());
            createUpdateWrongAnswerTextField3.setDisable(false);
        }
        if (answerList.size() > 1) {
            createUpdateWrongAnswerTextField1.setText(answerList.get(1).getText());
            createUpdateWrongAnswerTextField2.setDisable(false);
        }
        if (answerList.size() > 0) {
            createUpdateRightAnswerTextField.setText(answerList.get(0).getText());
        }
    }
    public void setupCreateNewQuestion() {
        titleLabel.setText("Vraag");
        answerLabel.setText("Geef de antwoorden");
        populateQuizMenuButton();
    }
    public void populateQuizMenuButton() {
        ArrayList<Quiz> quizzes = quizDAO.getAllByCoordinatorId(user.getUserId());
        for (Quiz quiz : quizzes) {
            System.out.println(quiz.getName());
            MenuItem menuItem = new MenuItem(quiz.getName());
            menuItem.setOnAction(event -> changeQuiz(quiz));
            quizButton.getItems().add(menuItem);
        }
    }
    public void changeQuiz(Quiz quiz) {
        this.question.setQuizID(quiz.getId());
        this.quiz = quiz;
        quizButton.setText(quiz.getName());
    }

    private void setQuestion(){ String questionTekst = createUpdateQuestionTextField.getText();
        System.out.println(questionTekst);
        if (questionTekst.isEmpty()){Coll.showAlert(Const.QUESTION_ALERT_HEADER, Const.QUESTION_REQUIRED, "INFORMATION"); filledIn = false;}
        else filledIn = true;
        boolean nameIsAllowed = Coll.checkIfNameAllowed(questionTekst, Const.NOT_ALLOWED_CHARACTERS_QUESTION);
        if (nameIsAllowed) {
            question.setQuestionText(questionTekst);
        } else Coll.showAlert(Const.SOME_CHARACTERS_NOT_ALLOWED, Const.CHOOSE_OTHER, "INFORMATION");
    }
    private void setNewRightAnswer(){ String answerTekst = createUpdateRightAnswerTextField.getText();
        if (answerTekst.isEmpty()){Coll.showAlert(Const.QUESTION_ALERT_HEADER, Const.ANSWER_REQUIRED, "INFORMATION"); filledIn = false;}
        else filledIn = true;
        boolean nameIsAllowed = Coll.checkIfNameAllowed(answerTekst, Const.NOT_ALLOWED_CHARACTERS);
        if (nameIsAllowed) {
            question.setRightAnswer(answerTekst);
        } else Coll.showAlert(Const.SOME_CHARACTERS_NOT_ALLOWED, Const.CHOOSE_OTHER, "INFORMATION");
    }
    private void setNewWrongAnswer1Answer(){ String answerTekst = createUpdateWrongAnswerTextField1.getText();
        if (answerTekst.isEmpty()){Coll.showAlert(Const.QUESTION_ALERT_HEADER, Const.ANSWER_REQUIRED, "INFORMATION"); filledIn = false;}
        else filledIn = true;
        boolean nameIsAllowed = Coll.checkIfNameAllowed(answerTekst, Const.NOT_ALLOWED_CHARACTERS);
        if (nameIsAllowed) {
            question.setWrongAnswer1(answerTekst);
        } else Coll.showAlert(Const.SOME_CHARACTERS_NOT_ALLOWED, Const.CHOOSE_OTHER, "INFORMATION");
    }
    private void setNewWrongAnswer2Answer(){ String answerTekst = createUpdateWrongAnswerTextField2.getText();
        boolean nameIsAllowed = Coll.checkIfNameAllowed(answerTekst, Const.NOT_ALLOWED_CHARACTERS);
        if (nameIsAllowed) {
            question.setWrongAnswer2(answerTekst);
        } else Coll.showAlert(Const.SOME_CHARACTERS_NOT_ALLOWED, Const.CHOOSE_OTHER, "INFORMATION");
    }
    private void setNewWrongAnswer3Answer(){ String answerTekst = createUpdateWrongAnswerTextField3.getText();
        boolean nameIsAllowed = Coll.checkIfNameAllowed(answerTekst, Const.NOT_ALLOWED_CHARACTERS);
        if (nameIsAllowed) {
            question.setWrongAnswer3(answerTekst);
        } else Coll.showAlert(Const.SOME_CHARACTERS_NOT_ALLOWED, Const.CHOOSE_OTHER, "INFORMATION");
    }

    public void storeInQuestionObject(){
        setQuestion();
        if (filledIn){setNewRightAnswer();}
        if (filledIn){setNewWrongAnswer1Answer();}
        setNewWrongAnswer2Answer();
        setNewWrongAnswer3Answer();
    }

    @FXML
    public void doBackToManage() {
        Main.getSceneManager().showManageQuestionsScene();
    }

    public void doMenu() { Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());}

    public void doStore(){
        storeInQuestionObject();
        if (this.quiz.getName().equals("onbekend")) { Coll.showAlert(Const.QUESTION_ALERT_HEADER, Const.QUIZ_REQUIRED, "INFORMATION"); filledIn = false;}
        if (filledIn){
            if(isNewCourse){
                int questionId =  questionDAO.storeOneReturnQuizId(question.getQuestionText(), question.getQuizID());
                answerDAO.storeAnswer(question.getRightAnswer(), questionId, true);
                answerDAO.storeAnswer(question.getWrongAnswer1(), questionId, false);
                answerDAO.storeAnswer(question.getWrongAnswer2(), questionId, false);
                answerDAO.storeAnswer(question.getWrongAnswer3(), questionId, false);
                Coll.showAlert(Const.QUESTION_ALERT_HEADER, Const.QUESTION_STORED, "INFORMATION");
            }
            else{
                int id = question.getQuestionID();
                questionDAO.updateQuestion(question);
                answerDAO.dropAllFromQuestion(id);
                answerDAO.storeAnswer(question.getRightAnswer(), id, true);
                answerDAO.storeAnswer(question.getWrongAnswer1(), id, false);
                answerDAO.storeAnswer(question.getWrongAnswer2(), id, false);
                answerDAO.storeAnswer(question.getWrongAnswer3(), id, false);
                Coll.showAlert(Const.QUESTION_ALERT_HEADER, Const.QUESTION_UPDATED, "INFORMATION");
            }
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
}
