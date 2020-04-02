package controller;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Question;
import model.Quiz;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class CreateUpdateQuizController extends AbstractController{
    private QuestionDAO questionDAO;
    private DBAccess dbAccess;


    @FXML
    private ListView<Question> selectedQuestions;
    @FXML
    private ListView<Question> availableQuestions;

    private List<Question> allQuizQuestions;
    private List<Question> selectedQuizQuestions;
    private List<Question> availableQuizQuestions = new  ArrayList<>();




    public void setup(User user,  Quiz quiz) {

        super.user = user;
        super.quiz = quiz;
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        questionDAO = new QuestionDAO(dbAccess);

        selectedQuizQuestions = questionDAO.getSelectedQuestionsByQuizId(quiz.getId());
        allQuizQuestions = questionDAO.getAllAvailableQuizQuestions(quiz.getId());

        // LEFT LIST VIEW
        for (Question question : selectedQuizQuestions) {
            System.out.println("1");
            System.out.println(question);
            selectedQuestions.getItems().add(question);
        }
        //set available questions to ALL question
        for (Question question : allQuizQuestions) {
            System.out.println("2");
            System.out.println(question);
                availableQuizQuestions.add(question);
        }
        //REMOVE THE ALREADY SELECTED QUESTIONS
        for (Question selectedQuestion : selectedQuizQuestions) {
            System.out.println("3");
            System.out.println(selectedQuestion);
          availableQuizQuestions.remove(selectedQuestion);
        }
        for (Question availableQuestion : availableQuizQuestions){
            System.out.println("4");
            System.out.println(availableQuestion);
            availableQuestions.getItems().add(availableQuestion);
        }


    }
/*       titleLabel.setText(quiz.getName());
        klantnummerTextfield.setText(String.valueOf(customer.getCustomerId()));
        voorlettersTextfield.setText(customer.getInitials());
        tussenvoegselTextfield.setText(customer.getPrefix());
        achternaamTextfield.setText(customer.getSurName());
        mobielTextfield.setText(customer.getMobilePhone());
    }*/







    public void doAddQuestionToQuiz(){
        Question selectedQuestion = availableQuestions.getSelectionModel().getSelectedItem();
        if(selectedQuestion != null) {
            availableQuestions.getItems().remove(selectedQuestion);
            selectedQuestions.getItems().add(selectedQuestion);
        }
    }
    public void doRemoveQuestionFromQuiz(){
        Question selectedQuestion = selectedQuestions.getSelectionModel().getSelectedItem();
        if(selectedQuestion != null) {
            selectedQuestions.getItems().remove(selectedQuestion);
            availableQuestions.getItems().add(selectedQuestion);
        }
    }


    public void doMenu() {
        Main.getSceneManager().showWelcomeScene(user);
    }
    public void doManageQuizzes() {
        Main.getSceneManager().showManageQuizScene(user);
    }


    public void doCreateUpdateQuiz() {}





    public void doCreateNewQuestion (){}

    public void doStore (){}

}