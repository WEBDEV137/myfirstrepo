package controller;

import database.mysql.AnswerDAO;
import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import database.nosql.QuizResultCouchDBDAO;
import database.nosql.QuizResultCouchDBaccess;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import model.*;
import view.Main;

import java.text.SimpleDateFormat;
import java.util.*;

public class FillOutQuizController{

    private DBAccess dbAccess;
    private QuizResultCouchDBaccess quizResultCouchDBaccess;
    private QuizResultCouchDBDAO quizResultCouchDBDAO;
    private QuestionDAO questionDAO;
    private AnswerDAO answerDAO;
    private CourseDAO courseDAO;
    private QuizResult quizResult;
    private QuestionResult currentQuestionResult;
    private User user;
    private Quiz quiz;
    private Course course;
    private Question currentQuestion;
    private List<Question> questions;
    private List<Answer> answers;
    private List<Answer> randomizedAnswers;
    private int questionNumber;
    private int questionIndex;
    private Answer answerA;
    private Answer answerB;
    private Answer answerC;
    private Answer answerD;
    final static int FIRST_QUESTION = 1;
    final static int INDEX_OF_FIRST_ITEM = 0;
    final static int INDEX_OF_SECOND_ITEM = 1;
    final static int INDEX_OF_THIRD_ITEM = 2;
    final static int INDEX_OF_FOURTH_ITEM = 3;
    final static  int NUMBER_OF_ANSWERS_AB= 2;
    final static int NUMBER_OF_ANSWERS_ABC = 3;


    @FXML
    private Label titleLabel;
    @FXML
    private TextArea questionArea;
    @FXML
    private Button buttonC;
    @FXML
    private Button buttonD;

    public void setup(Quiz quiz) {
        this.quiz = quiz;
        questionIndex = INDEX_OF_FIRST_ITEM;
        questionNumber = FIRST_QUESTION;
        dbAccess = Main.getDBaccess();
        user = Main.getCurrentUser();
        questionDAO = new QuestionDAO(dbAccess);
        courseDAO = new CourseDAO(dbAccess);
        course = courseDAO.getOneById(quiz.getCourseId());
        quizResult = new QuizResult(user, quiz, course);
        questions = questionDAO.getSelectedQuestionsByQuizId(quiz.getId());
        for (int index = INDEX_OF_FIRST_ITEM; index < questions.size(); index++) {
            quizResult.addQuestionResult(new QuestionResult());
        }
        currentQuestion = questions.get(questionIndex);
        answers = getAnswersByQuestionId(currentQuestion.getQuestionID());
        randomizedAnswers = randomizeAnswers(answers);
        assignAnswersToLetters();
        fillTextArea();
        currentQuestionResult = quizResult.getQuestionResult(INDEX_OF_FIRST_ITEM);
    }



    private List<Answer> getAnswersByQuestionId(int questionId) {
        answerDAO = new AnswerDAO(dbAccess);
        answers = answerDAO.getAllByQuestionId(questionId);
        return answers;
    }

    public List<Answer> randomizeAnswers (List<Answer> answers) {
        List<Answer> answersCopy = new ArrayList<>();
        for (Answer answerToCopy : answers) {
            answersCopy.add(answerToCopy);
        }
        int index = answersCopy.size()-1;
        int MAX = answersCopy.size();
        List<Answer> randomizedAnswers = new ArrayList<>();
        while (index >= INDEX_OF_FIRST_ITEM) {
            int randomNumber = ((int)(Math.random() * MAX));
            Answer answerToshuffle = answersCopy.get(randomNumber);
            answersCopy.remove(randomNumber);
            randomizedAnswers.add(answerToshuffle);
            index--;
            MAX --;
        } return randomizedAnswers;
    }
        public void assignAnswersToLetters() {
            answerA = null;
            answerB = null;
            answerC = null;
            answerD = null;
            answerA = randomizedAnswers.get(INDEX_OF_FIRST_ITEM);
            answerB = randomizedAnswers.get(INDEX_OF_SECOND_ITEM);
            if (randomizedAnswers.size() > NUMBER_OF_ANSWERS_AB) {
                answerC = randomizedAnswers.get(INDEX_OF_THIRD_ITEM);
                if (randomizedAnswers.size() > NUMBER_OF_ANSWERS_ABC) {
                    answerD = randomizedAnswers.get(INDEX_OF_FOURTH_ITEM);
                }
            }
        }
    private void fillTextArea(){
        if(questions.get(questionIndex) != null){
            currentQuestion = questions.get(questionIndex);
            StringBuilder vraagEnAntwoorden = new StringBuilder();
            vraagEnAntwoorden.append(currentQuestion.getQuestionText());
            vraagEnAntwoorden.append("\nA. ").append(answerA);
            vraagEnAntwoorden.append("\nB. ").append(answerB);
            if (answerC != null){
                vraagEnAntwoorden.append("\nC. ").append(answerC);
                if (answerD!= null) {
                    vraagEnAntwoorden.append("\nD. ").append(answerD);
                }
            }
            questionArea.setText(vraagEnAntwoorden.toString());
            int questionNumber = questionIndex +1;
            titleLabel.setText(String.format("Vraag %d", questionNumber));
        }
    }
    private void storeAnswersInQuestionResult() {
        if (answers.get(INDEX_OF_FIRST_ITEM).isRightAnswer()) {
            currentQuestionResult.setRightAnswer(answers.get(INDEX_OF_FIRST_ITEM).getText());
        }
        else {
            System.out.println("First answer in answers ArrayList should be the correct answer!, but its not");
        }
        currentQuestionResult.setWrongAnswer1(answers.get(INDEX_OF_SECOND_ITEM).getText());
        if (answers.size() > NUMBER_OF_ANSWERS_ABC) {
            currentQuestionResult.setWrongAnswer2(answers.get(INDEX_OF_THIRD_ITEM).getText());
            currentQuestionResult.setWrongAnswer3(answers.get(INDEX_OF_FOURTH_ITEM).getText());
        }
        else if (answers.size() > NUMBER_OF_ANSWERS_AB) {
            currentQuestionResult.setWrongAnswer2(answers.get(INDEX_OF_THIRD_ITEM).getText());
        }
    }
    public void storeQuestionInQuestionResult(){
        currentQuestionResult.setQuestionText(currentQuestion.getQuestionText());
    }
    public void storeCurrentQuestionResult(){
        storeQuestionInQuestionResult();
        storeAnswersInQuestionResult();
        quizResult.setQuestionResult(questionIndex, currentQuestionResult);
    }
    public void doNextQuestion() {
        storeCurrentQuestionResult();
        questionIndex++;
        if (questionIndex < questions.size()) {
            currentQuestion = questions.get(questionIndex);
            System.out.println(currentQuestion);
            answers = getAnswersByQuestionId(currentQuestion.getQuestionID());
            hideButtons(answers.size());
            for (Answer answer : answers){
                System.out.println(answer);
            }
            randomizedAnswers = randomizeAnswers(answers);
            assignAnswersToLetters();
            fillTextArea();
           currentQuestionResult = quizResult.getQuestionResult(questionIndex);
            System.out.println(quizResult);
        }
        else{ ;
            questionIndex--;
            doFinishConfirmation();
        }
    }
    public void doPreviousQuestion() {
        storeCurrentQuestionResult();
        if (questionIndex > INDEX_OF_FIRST_ITEM) {
            questionIndex--;
            currentQuestion = questions.get(questionIndex);
            answers = getAnswersByQuestionId(currentQuestion.getQuestionID());
            hideButtons(answers.size());
            randomizedAnswers = randomizeAnswers(answers);
            assignAnswersToLetters();
            fillTextArea();
            currentQuestionResult = quizResult.getQuestionResult(questionIndex);
            System.out.println(quizResult);
        }
    }
    public void doRegisterA() {

        currentQuestionResult.setAnswer(answerA.getText());
        doNextQuestion();
    }
    public void doRegisterB() {
        currentQuestionResult.setAnswer(answerB.getText());
        doNextQuestion();
    }
    public void doRegisterC() {
        currentQuestionResult.setAnswer(answerC.getText());
        doNextQuestion();
    }
    public void doRegisterD() {
        currentQuestionResult.setAnswer(answerD.getText());
        doNextQuestion();
    }
    public void hideButtons(int numberOfAnswers){
        buttonC.setVisible(true);
        buttonD.setVisible(true);
        if(numberOfAnswers == NUMBER_OF_ANSWERS_ABC ){
            buttonD.setVisible(false);
        }
        else if(numberOfAnswers == NUMBER_OF_ANSWERS_AB ){
            buttonC.setVisible(false);
            buttonD.setVisible(false);
        }
    }
    public String storeQuizResultReturnDocId(QuizResult quizResult){
        String doc_id;
        quizResultCouchDBaccess = new QuizResultCouchDBaccess();

        try {
            quizResultCouchDBaccess.setupConnection();
            System.out.println("Connection open");
            quizResultCouchDBDAO = new QuizResultCouchDBDAO(quizResultCouchDBaccess);
        }
        catch (Exception e) {
            System.out.println("\nEr is iets fout gegaan\n");
            e.printStackTrace();
        }
        doc_id = quizResultCouchDBDAO.saveSingelQuizResult(quizResult);
        return doc_id;
    }
    public void doMenu() {
        if(quizResult.getCountQuestions()>0){
       storeCurrentQuestionResult(); }
       Main.getSceneManager().showWelcomeScene(user);}

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
            okCancelDialogue.setTitle(Main.QUIZMASTER);
            okCancelDialogue.setHeaderText(ARE_YOU_SURE);
            okCancelDialogue.initModality(Modality.APPLICATION_MODAL);
            okCancelDialogue.initOwner(Main.getPrimaryStage()); //show,
            Optional<ButtonType> result = okCancelDialogue.showAndWait();
            if (result.get() == jaKnop) {
                doMenu();
            }
    }
    /**
     * Confirmation dialogue
     * do you want to Finish the Quiz?
     */
    public void doFinishConfirmation() {
        String ARE_YOU_SURE = "Er zijn geen vragen meer. Klik \"Bekijk resultaat\" om uw resulaten te bekijken";
        String CLICK_CONTINUE = "Klik \"Hier blijven\" als u nog aanpassingen wil doen";

        ButtonType jaKnop = new ButtonType("Bekijk resultaat", ButtonBar.ButtonData.YES);
        ButtonType neeKnop = new ButtonType("Hier Blijven", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert okCancelDialogue = new Alert(Alert.AlertType.WARNING, CLICK_CONTINUE, jaKnop, neeKnop);
        okCancelDialogue.setTitle(Main.QUIZMASTER);
        okCancelDialogue.setHeaderText(ARE_YOU_SURE);
        okCancelDialogue.initModality(Modality.APPLICATION_MODAL);
        okCancelDialogue.initOwner(Main.getPrimaryStage()); //show,
        Optional<ButtonType> result = okCancelDialogue.showAndWait();
        if (result.get() == jaKnop) {
            doFinish();
        }
    }
    public void doFinish(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        formatter.format(date);
        quizResult.setDate(date);

        String doc_id = storeQuizResultReturnDocId(quizResult);
        Main.getSceneManager().showStudentFeedback(doc_id);
    }
}
