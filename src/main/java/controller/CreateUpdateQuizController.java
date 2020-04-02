package controller;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    @FXML
    private TextField quizNameField;
    @FXML
    private TextField succesDefinitionField;


    private List<Question> allQuizQuestions;
    private List<Question> selectedQuizQuestions;
    private List<Question> availableQuizQuestions = new  ArrayList<>();




    public void setup(User user,  Quiz quiz) {

        super.user = user;
        super.quiz = quiz;
        quizNameField.setText(quiz.getName());
        succesDefinitionField.setText(Integer.toString(quiz.getSuccesDefinition()));

        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        questionDAO = new QuestionDAO(dbAccess);

        selectedQuizQuestions = questionDAO.getSelectedQuestionsByQuizId(quiz.getId());
        allQuizQuestions = questionDAO.getAllAvailableQuizQuestions(quiz.getId());

        // Populate Left ListView With
        for (Question question : selectedQuizQuestions) {
            System.out.println("1");
            System.out.println(question);
            selectedQuestions.getItems().add(question);
        }
        //Create the available question list
        // Step 1:
        for (Question question : allQuizQuestions) {
            System.out.println("2");
            System.out.println(question);
             availableQuizQuestions.add(question);
        }
        //step 2:
        for (Question selectedQuestion : selectedQuizQuestions) {
            System.out.println("3");
            System.out.println(selectedQuestion);
          availableQuizQuestions.remove(selectedQuestion);
        }
        //Populate Right ListView with available questions
        for (Question availableQuestion : availableQuizQuestions){
            System.out.println("4");
            System.out.println(availableQuestion);
            availableQuestions.getItems().add(availableQuestion);
        }


    }
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

    public void doStore () {
        final String NOT_ALLOWED_CHARACTERS = "%$@~`?;:*^#!+}{[]=\\|";
        final String SUCCESFACTOR_TO_LOW = "De succesfactor mag niet hoger zijn dan het aantal vragen";
        final String SOME_CHARACTERS_NOT_ALLOWED =  "De volgende karakters zijn niet toegestaan: %$@~`?;:*^#!+}{[]=\\|";
        final String GIVE_OTHER_VALUE = "Geef een andere waarde op.";
        final String CHOOSE_OTHER_NAME = "Geef een andere naam op.";
        String quizNameInput = quizNameField.getText();
        int succesDefinition = Integer.valueOf(succesDefinitionField.getText());
        int numberOfQuestions = selectedQuestions.getItems().size();


        boolean nameIsAllowed = checkIsNameAllowed(quizNameField.getText(), NOT_ALLOWED_CHARACTERS);
        if(nameIsAllowed) {
            super.quiz.setName(quizNameField.getText());
        }
        else showAlert(SOME_CHARACTERS_NOT_ALLOWED, CHOOSE_OTHER_NAME, "WARNING");

       if (succesDefinition <= numberOfQuestions) {
            super.quiz.setSuccesDefinition(Integer.valueOf(succesDefinitionField.getText()));
        } else showAlert(SUCCESFACTOR_TO_LOW, GIVE_OTHER_VALUE,  "WARNING");
        }

    /**
     * Checks if an input String contains eny of the not allowed characters. returns false if it does
     * @param inputText
     * @param notAllowedCharacters
     * @return true if the String is allowed
     */
    public boolean checkIsNameAllowed(String inputText, String notAllowedCharacters){
            for (int index = 0; index < notAllowedCharacters.length() ; index++) {
                if (inputText.indexOf(notAllowedCharacters.charAt(index)) != -1){
                    return false;
                };
            }return true;
        }


}