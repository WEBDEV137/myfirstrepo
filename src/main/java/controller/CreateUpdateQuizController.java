package controller;

import database.mysql.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import model.Course;
import model.Question;
import model.Quiz;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CreateUpdateQuizController extends AbstractController {

    private QuestionDAO questionDAO;
    private QuizDAO quizDAO;
    private CourseDAO courseDAO;
    private QuizQuestionDAO quizQuestionDAO;
    private DBAccess dbAccess;
    private User user;
    private Quiz quiz;
    private List<Question> allQuizQuestions;
    private List<Question> selectedQuizQuestions;
    private List<Question> availableQuizQuestions;
    @FXML
    private Label createUpdateQuizHeader;
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
        quizQuestionDAO = new QuizQuestionDAO(dbAccess);

        if (quiz != null) {
            setupUpdateQuiz();
        } else {
            setupCreateNewQuiz();
        }
    }

    public void setupUpdateQuiz() {
        quizNameField.setText(quiz.getName());
        succesDefinitionField.setText(Integer.toString(quiz.getSuccesDefinition()));
        selectedQuizQuestions = (questionDAO.getSelectedQuestionsByQuizId(quiz.getId()));
        allQuizQuestions = null;
        allQuizQuestions = questionDAO.getAllQuestionsByQuizId(quiz.getId());


      populateLeftListView();
        populateListView(selectedQuestions, selectedQuizQuestions);
        //populateAvailableQuizQuestionsList();
        populateListView(availableQuestions, availableQuizQuestions);
        //populateRightListView();
        populateCourseMenuButton();

    }

    public void setupCreateNewQuiz() {
//        populateLeftListView();
//        populateAvailableQuizQuestionsList();
//        populateRightListView();
        //createUpdateQuizHeader.setText("Nieuwe quiz");
        //changeQuizCourse(coursesMenuButton.getItems().;
        quizDAO = new QuizDAO(dbAccess);
        ArrayList<Course> coordinatorCourses = courseDAO.getAllByCoordinatorId(user.getUserId());

        Course course = coordinatorCourses.get(0);

        System.out.println(course);
        quiz = new Quiz(0,"onbekend", 10 , course.getId());

        int quizId = quizDAO.storeOneAndReturnId(quiz);
        System.out.println(quizId);
        quiz.setId(quizId);
        populateCourseMenuButton();


    }

    public void populateLeftListView() {
        for (Question question : selectedQuizQuestions) {
            System.out.println(question);
            selectedQuestions.getItems().add(question);
        }
    }

    public void populateAvailableQuizQuestionsList() {
        // Step 1:
        availableQuizQuestions = new ArrayList<>();
        for (Question question : allQuizQuestions) {
            //System.out.println(question);
            availableQuizQuestions.add(question);
        }
        //step 2:
        for (int index = 0; index < selectedQuizQuestions.size(); index++) {
        RemoveQuestionFromArrayList(availableQuizQuestions, selectedQuizQuestions.get(index));
            System.out.println("selected question to remove" + selectedQuizQuestions.get(index));

        }
        for (Question available : availableQuizQuestions) {
            System.out.println("available qquestion: " + available);
        }
    }

    public void populateRightListView() {
        for (Question availableQuestion : availableQuizQuestions) {
            System.out.println("selected question to populate right screen" + availableQuestion);
            availableQuestions.getItems().add(availableQuestion);
        }
    }

    public ArrayList<Course> populateCourseMenuButton() {

        ArrayList<Course> coordinatorCourses = courseDAO.getAllByCoordinatorId(user.getUserId());
        for (Course course : coordinatorCourses) {
            MenuItem menuItem = new MenuItem(course.getCoursename());
            menuItem.setOnAction(event -> changeQuizCourse(course));
            coursesMenuButton.setText(course.getCoursename());
            coursesMenuButton.getItems().add(menuItem);
        }

        Course currentQuizCourse = courseDAO.getOneById(quiz.getCourseId());
        if (currentQuizCourse != null) {
            coursesMenuButton.setText(currentQuizCourse.getCoursename());
        }
        return coordinatorCourses;

    }


    public void changeQuizCourse(Course course) {
        quiz.setCourseName(course.getCoursename());
        quiz.setCourseId(course);
        coursesMenuButton.setText(course.getCoursename());
    }

    @FXML
    public void doAddQuestionToQuiz() {
        Question selectedQuestion = availableQuestions.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            selectedQuestions.getItems().add(selectedQuestion); //Listview
            selectedQuizQuestions.add(selectedQuestion); //chosen questions arraylist
            availableQuizQuestions.remove(selectedQuestion); //Listview
            availableQuestions.getItems().remove(selectedQuestion); // available questions arraylist

        }
    }

    @FXML
    public void doRemoveQuestionFromQuiz() {
        Question selectedQuestion = selectedQuestions.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            selectedQuestions.getItems().remove(selectedQuestion);
            selectedQuizQuestions.remove(selectedQuestion);
            availableQuestions.getItems().add(selectedQuestion);
            availableQuizQuestions.remove(selectedQuestion);
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


    public void doCreateUpdateQuiz() {
    }

    @FXML
    public void doCreateNewQuestion() {
        //Eerste versie werkt wel maar, slaat direct op in database
       if (questionDAO == null) {
            questionDAO = new QuestionDAO(dbAccess);
        }
        int vraagId = questionDAO.storeEmptyQuestionByQuizId(quiz);
        quizQuestionDAO.storeOne(quiz.getId(), vraagId);
        Main.getSceneManager().showCreateUpdateQuizScene(quiz);

        //Tweede versie : Beter, eerst aanmaken in geheugen en pas toevoegen aan database als Opslaan wordt gedrukt.
        /*Question question = new Question(0, "Vul vraag in ", quiz.getId());
        selectedQuestions.getItems().add(question);
        selectedQuizQuestions.add(question);*/
    }

    @FXML
    public void doChangeQuestion() {
        Question selectedQuestion = selectedQuestions.getSelectionModel().getSelectedItem();
        Main.getSceneManager().showCreateUpdateQuestionScene(selectedQuestion);
    }

    @FXML
    public void doStore() {
        updateQuizAttributes();
        updateQuizQuestionTable();
        //dropAndCreateNewQuizquestionTable();
    }

    /**
     * Checks if an input String contains any of the not allowed characters. returns false if it does
     *
     * @param inputText
     * @param notAllowedCharacters
     * @return true if the String is allowed
     */
    public boolean checkIfNameAllowed(String inputText, String notAllowedCharacters) {
        for (int index = 0; index < notAllowedCharacters.length(); index++) {
            if (inputText.indexOf(notAllowedCharacters.charAt(index)) != -1) {
                return false;
            }
            ;
        }
        return true;
    }

    public boolean doesArraylistContainNumber(ArrayList<Integer> list, Integer number) {
        for (int index = 0; index < list.size(); index++) {
            if (list.get(index) == number) {
                return true;
            }
        }
        return false;
    }


    /**
     * werkt deze wel?
     */

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
    public void dropAndCreateNewQuizquestionTable() {
        QuizQuestionDAO quizQuestionDAO = new QuizQuestionDAO(dbAccess);
        quizQuestionDAO.dropAllFromQuiz(quiz.getId());
        for (int index = 0; index < selectedQuestions.getItems().size() ; index++) {
            int questionId = selectedQuestions.getItems().get(index).getQuestionID();
            int quizId = selectedQuestions.getItems().get(index).getQuizID();
            quizQuestionDAO.storeOne(quizId, questionId);
        }

        }




    public void updateQuizAttributes() {
        final String NOT_ALLOWED_CHARACTERS = "%$@~`?;:*^#!+}{[]=\\|";
        final String SUCCESFACTOR_TOO_HIGH = "De succesfactor mag niet hoger zijn dan het aantal vragen";
        final String SUCCESFACTOR_TOO_LOW = "De succesfactor moet minimaal 1 zijn";
        final String STORING_SUCCES = "De gegevens zijn opgeslagen";
        final String SOME_CHARACTERS_NOT_ALLOWED = "De volgende karakters zijn niet toegestaan: %$@~`?;:*^#!+}{[]=\\|";
        final String GIVE_OTHER_VALUE = "Geef een andere waarde op.";
        final String CHOOSE_OTHER_NAME = "Geef een andere naam op.";

        String newQuizName = quizNameField.getText();
        boolean nameIsAllowed = checkIfNameAllowed(newQuizName, NOT_ALLOWED_CHARACTERS);
        if (nameIsAllowed) {
            quiz.setName(quizNameField.getText());
        } else showAlert(SOME_CHARACTERS_NOT_ALLOWED, CHOOSE_OTHER_NAME, "WARNING");

        int newSuccesDefinition = Integer.valueOf(succesDefinitionField.getText());
        int numberOfQuestions = selectedQuestions.getItems().size();
        if (newSuccesDefinition < 1) {
            showAlert(SUCCESFACTOR_TOO_LOW, GIVE_OTHER_VALUE, "Warning");
        } else if (newSuccesDefinition > numberOfQuestions) {
            showAlert(SUCCESFACTOR_TOO_HIGH, GIVE_OTHER_VALUE, "Warning");
        } else {
            quiz.setSuccesDefinition(newSuccesDefinition);
            showAlert(STORING_SUCCES, null, "INFORMATION");
        }

        quizDAO = new QuizDAO(dbAccess);
        quizDAO.updateOne(quiz);
    }


    public List<Question> RemoveQuestionFromArrayList(List<Question> list, Question question) {
        for (int index = 0; index < list.size(); index++) {
            if (list.get(index).getQuestionText().equals(question.getQuestionText())) {
                list.remove(index);
            }
        } return list;
    }
    /**
     * Confirmation dialogue
     * do you want to go back to Menu quiz?
     */
    public void goBackToMenuConfirmation() {
            String ARE_YOU_SURE = "Als u terugggaat worden alle wijzigingen die niet zijn opgeslagen  verwijderd.";
            String CLICK_CONTINUE = "Weet u zeker dat u wilt doorgaan?";

            ButtonType jaKnop = new ButtonType("Ja", ButtonBar.ButtonData.YES);
            ButtonType neeKnop = new ButtonType("Nee", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert okCancelDialogue = new Alert(Alert.AlertType.WARNING, CLICK_CONTINUE, jaKnop, neeKnop);
            okCancelDialogue.setTitle(Main.QUIZMASTER);
            okCancelDialogue.setHeaderText(ARE_YOU_SURE);
            okCancelDialogue.initModality(Modality.APPLICATION_MODAL); //Achtegrpond scherm wordt onbruikbaar gemaakt.
            okCancelDialogue.initOwner(Main.getPrimaryStage()); //show,
            Optional<ButtonType> result = okCancelDialogue.showAndWait();
            if (result.get() == jaKnop) {
                doMenu();
            }
            else if (!result.isPresent()){}

    }
    /**
     * Confirmation dialogue
     * do you want to go back to ManagaQuizzes?
     */
    public void goBackToManagQuizzesConfirmation() {
        String ARE_YOU_SURE = "Als u terugggaat worden alle wijzigingen die niet zijn opgeslagen  verwijderd.";
        String CLICK_CONTINUE = "Weet u zeker dat u wilt doorgaan?";

        ButtonType jaKnop = new ButtonType("Ja", ButtonBar.ButtonData.YES);
        ButtonType neeKnop = new ButtonType("Nee", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert okCancelDialogue = new Alert(Alert.AlertType.WARNING, CLICK_CONTINUE, jaKnop, neeKnop);
        okCancelDialogue.setTitle(Main.QUIZMASTER);
        okCancelDialogue.setHeaderText(ARE_YOU_SURE);
        okCancelDialogue.initModality(Modality.APPLICATION_MODAL); //Achtegrpond scherm wordt onbruikbaar gemaakt.
        okCancelDialogue.initOwner(Main.getPrimaryStage()); //show,
        Optional<ButtonType> result = okCancelDialogue.showAndWait();
        if (result.get() == jaKnop) {
            doManageQuizzes();
        }
        else if (!result.isPresent()){}

    }

}