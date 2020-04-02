package controller;

import database.mysql.*;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import model.Course;
import model.Question;
import model.Quiz;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class CreateUpdateQuizController extends AbstractController{

    private QuestionDAO questionDAO;
    private QuizDAO quizDAO;
    private CourseDAO courseDAO;
    private DBAccess dbAccess;
    private User user;
    private Quiz quiz;
    private List<Question> allQuizQuestions;
   // private List<Question> selectedQuizQuestions;
    private List<Question> availableQuizQuestions = new  ArrayList<>();

    @FXML
    private ListView<Question> selectedQuestions;
    @FXML
    private ListView<Question> availableQuestions;
    @FXML
    private TextField quizNameField;
    @FXML
    private TextField succesDefinitionField;
    @FXML
    private MenuButton coursesMenuButton;

    public void setup(Quiz quiz) {
        this.quiz = quiz;
        user = Main.getCurrentUser();
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        questionDAO = new QuestionDAO(dbAccess);
        courseDAO = new CourseDAO(dbAccess);

        if(quiz != null) {
            setupUpdateQuiz();
        }
        else{
            setupCreateNewQuiz();
        }
    }
    public void setupUpdateQuiz(){
        quizNameField.setText(quiz.getName());
        succesDefinitionField.setText(Integer.toString(quiz.getSuccesDefinition()));
        quiz.setSelectedQuestions(questionDAO.getSelectedQuestionsByQuizId(quiz.getId()));
        allQuizQuestions = null;
        allQuizQuestions = questionDAO.getAllQuestionsByQuizId(quiz.getId());

        populateLeftListView();
        populateAvailableQuizQuestionsList();
        populateRightListView();
        populateCourseMenuButton();
    }
    public void setupCreateNewQuiz(){
       questionDAO.getQuestionList();
    }

    public void populateLeftListView() {
        for (Question question : quiz.getQuestions()) {
            //System.out.println(question);
            selectedQuestions.getItems().add(question);
        }
    }
    public void populateAvailableQuizQuestionsList() {
        // Step 1:
        for (Question question : allQuizQuestions) {
            //System.out.println(question);
            availableQuizQuestions.add(question);
        }
        //step 2:
        for (Question selectedQuestion : quiz.getQuestions()) {
            //System.out.println(selectedQuestion);
            availableQuizQuestions.remove(selectedQuestion);
        }
    }
    public void populateRightListView() {
        for (Question availableQuestion : availableQuizQuestions) {
            //System.out.println(availableQuestion);
            availableQuestions.getItems().add(availableQuestion);
        }
    }
    public void populateCourseMenuButton(){
        ArrayList<Course> coordinatorCourses = courseDAO.getAllByCoordinatorId(user.getUserId());
       for(Course course : coordinatorCourses) {
           MenuItem menuItem = new MenuItem(course.getCoursename());
           menuItem.setOnAction(event -> changeQuizCourse(course));
           coursesMenuButton.setText(course.getCoursename());
           coursesMenuButton.getItems().add(menuItem);
       }
    }
    public void changeQuizCourse(Course course){
        quiz.setCourseName(course.getCoursename());
        quiz.setCourseId(course);
        coursesMenuButton.setText(course.getCoursename());
    }

    @FXML
    public void doAddQuestionToQuiz(){
        Question selectedQuestion = availableQuestions.getSelectionModel().getSelectedItem();
        if(selectedQuestion != null) {
            availableQuestions.getItems().remove(selectedQuestion);
            selectedQuestions.getItems().add(selectedQuestion);
        }
    }
    @FXML
    public void doRemoveQuestionFromQuiz(){
        Question selectedQuestion = selectedQuestions.getSelectionModel().getSelectedItem();
        if(selectedQuestion != null) {
            selectedQuestions.getItems().remove(selectedQuestion);
            availableQuestions.getItems().add(selectedQuestion);
        }
    }
    @FXML
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene(user);
    }
    @FXML
    public void doManageQuizzes() {
        Main.getSceneManager().showManageQuizScene();
    }


    public void doCreateUpdateQuiz() {}

    @FXML
    public void doCreateNewQuestion (){
        if (questionDAO == null) {questionDAO = new QuestionDAO(dbAccess);}
        questionDAO.storeEmptyQuestionByQuizId(quiz);
        Main.getSceneManager().showCreateUpdateQuizScene(quiz);
        }

    @FXML
    public void doChangeQuestion(){
        Question selectedQuestion = selectedQuestions.getSelectionModel().getSelectedItem();
        Main.getSceneManager().showCreateUpdateQuestionScene(selectedQuestion);
    }

    @FXML
    public void doStore () {
        updateQuizAttributes();
        updateQuizQuestionTable();
    }
        /**
     * Checks if an input String contains any of the not allowed characters. returns false if it does
     * @param inputText
     * @param notAllowedCharacters
     * @return true if the String is allowed
     */
    public boolean checkIfNameAllowed(String inputText, String notAllowedCharacters){
            for (int index = 0; index < notAllowedCharacters.length() ; index++) {
                if (inputText.indexOf(notAllowedCharacters.charAt(index)) != -1){
                    return false;
                };
            }return true;
    }
    public void updateQuizQuestionTable() {
        QuizQuestionDAO quizQuestionDAO = new QuizQuestionDAO(dbAccess);
        ArrayList<Integer> selectedQuizQuestionIdsInDb = quizQuestionDAO.getAllbyQuizId(quiz);
        ArrayList<Integer> selectedQuizQuestionIdsInLisView = new ArrayList<>();
        for (Question selectedQuestion : selectedQuestions.getItems()) {
            selectedQuizQuestionIdsInLisView.add(selectedQuestion.getQuestionID());
        }
        // Check if quizvraag tabel Contains question that is notin listview -> removeOne
        for (int index = 0; index < selectedQuizQuestionIdsInDb.size(); index++) {
            Integer questionId = selectedQuizQuestionIdsInDb.get(index);
            if (!doesArraylistContainNumber(selectedQuizQuestionIdsInLisView, selectedQuizQuestionIdsInDb.get(index))) {
                quizQuestionDAO.removeOneByQuestionId(questionId);
            }
        }
        // Check if ListView Contains question that is not in Database -> storeOne()
        for (int index = 0; index < selectedQuizQuestionIdsInLisView.size(); index++) {
            Integer questionId = selectedQuizQuestionIdsInLisView.get(index);
            if (!doesArraylistContainNumber(selectedQuizQuestionIdsInDb, selectedQuizQuestionIdsInLisView.get(index))) {
                quizQuestionDAO.storeOne(quiz.getId(), questionId);
            }
        }
    }
    public boolean doesArraylistContainNumber(ArrayList<Integer> list, Integer number) {
        for (int index = 0; index < list.size(); index++) {
            if (list.get(index) == number) {
                return true;
            }
        }
        return false;
    }


    public void updateQuizAttributes(){
        final String NOT_ALLOWED_CHARACTERS = "%$@~`?;:*^#!+}{[]=\\|";
        final String SUCCESFACTOR_TOO_HIGH = "De succesfactor mag niet hoger zijn dan het aantal vragen";
        final String SUCCESFACTOR_TOO_LOW = "De succesfactor moet minimaal 1 zijn";
        final String STORING_SUCCES = "De gegevens zijn opgeslagen";
        final String SOME_CHARACTERS_NOT_ALLOWED =  "De volgende karakters zijn niet toegestaan: %$@~`?;:*^#!+}{[]=\\|";
        final String GIVE_OTHER_VALUE = "Geef een andere waarde op.";
        final String CHOOSE_OTHER_NAME = "Geef een andere naam op.";

        String newQuizName = quizNameField.getText();
        boolean nameIsAllowed = checkIfNameAllowed(newQuizName, NOT_ALLOWED_CHARACTERS);
        if(nameIsAllowed) {
            quiz.setName(quizNameField.getText());
        } else showAlert(SOME_CHARACTERS_NOT_ALLOWED, CHOOSE_OTHER_NAME, "WARNING");

        int newSuccesDefinition = Integer.valueOf(succesDefinitionField.getText());
        int numberOfQuestions = selectedQuestions.getItems().size();
        if (newSuccesDefinition < 1) {showAlert(SUCCESFACTOR_TOO_LOW, GIVE_OTHER_VALUE, "Warning");}
        else if (newSuccesDefinition > numberOfQuestions){showAlert(SUCCESFACTOR_TOO_HIGH, GIVE_OTHER_VALUE, "Warning");}
        else { quiz.setSuccesDefinition(newSuccesDefinition);
            showAlert(STORING_SUCCES, null, "INFORMATION");
        }

        quizDAO = new QuizDAO(dbAccess);
        quizDAO.updateOne(quiz);
    }


}