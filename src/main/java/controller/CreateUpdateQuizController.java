package controller;

import database.mysql.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import view.Main;

import java.util.ArrayList;

public class CreateUpdateQuizController extends AbstractController {

    private QuestionDAO questionDAO;
    private QuizDAO quizDAO;
    private CourseDAO courseDAO;
    private QuizQuestionDAO quizQuestionDAO;
    private DBAccess dbAccess;
    private User user;
    private Quiz quiz;
    private boolean isNewCourse = false;
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
        courseDAO = new CourseDAO(dbAccess);
        quizDAO = new QuizDAO(dbAccess);
        if (quiz == null) {
            isNewCourse = true;
            setupCreateNewQuiz();
        } else {
            setupUpdateQuiz();
        }
    }

    public void setupUpdateQuiz() {
        quizNameField.setText(quiz.getName());
        succesDefinitionField.setText(Integer.toString(quiz.getSuccesDefinition()));
        populateCourseMenuButton();
        Course currentQuizCourse = courseDAO.getOneById(quiz.getCourseId());
        System.out.println(currentQuizCourse.getCoursename());
        if (currentQuizCourse != null) {
            coursesMenuButton.setText(currentQuizCourse.getCoursename());
        }
    }
    public void setupCreateNewQuiz() {

        quiz = new Quiz();
        populateCourseMenuButton();
        coursesMenuButton.setText("Cursus");
    }
    public void populateCourseMenuButton() {
        ArrayList<Course> coordinatorCourses = courseDAO.getAllByCoordinatorId(user.getUserId());
        for (Course course : coordinatorCourses) {
            MenuItem menuItem = new MenuItem(course.getCoursename());
            menuItem.setOnAction(event -> changeCourse(course));
            coursesMenuButton.setText(course.getCoursename());
            coursesMenuButton.getItems().add(menuItem);
        }
    }
    public void changeCourse(Course course) {
        this.quiz.setCourseName(course.getCoursename());
        this.quiz.setCourseId(course);
        coursesMenuButton.setText(course.getCoursename());
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
    public void doStore() {

        setQuizName();
        quiz.setSuccesDefinition(Integer.valueOf(succesDefinitionField.getText()));
        if(isNewCourse) {
            int quizId = quizDAO.storeOneAndReturnId(quiz);
        }
        else{
            quizDAO.updateOne(quiz);
        }
    }

    public void setQuizName(){
        String newQuizName = quizNameField.getText();
        boolean nameIsAllowed = checkIfNameAllowed(newQuizName, Const.NOT_ALLOWED_CHARACTERS);
        if (nameIsAllowed) {
            quiz.setName(quizNameField.getText());
        } else showAlert(Const.SOME_CHARACTERS_NOT_ALLOWED, Const.CHOOSE_OTHER, "INFORMATION");
    }
    public boolean doesArraylistContainNumber(ArrayList<Integer> list, Integer number) {
        for (int index = 0; index < list.size(); index++) {
            if (list.get(index) == number) {
                return true;
            }
        }
        return false;
    }


    /*    */

    /**
     * werkt deze wel?
     *//*

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
        }*/
/*
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
*/

/*    public void updateQuizAttributes() {
        final String NOT_ALLOWED_CHARACTERS = "%$@~`?;:*^#!+}{[]=\\|";
        final String SUCCESFACTOR_TOO_HIGH = "De succesfactor mag niet hoger zijn dan het aantal vragen";
        final String SUCCESFACTOR_TOO_LOW = "De succesfactor moet minimaal 1 zijn";
        final String STORING_SUCCES = "De gegevens zijn opgeslagen";
        final String SOME_CHARACTERS_NOT_ALLOWED = "De volgende karakters zijn niet toegestaan: %$@~`?;:*^#!+}{[]=\\|";
        final String GIVE_OTHER_VALUE = "Geef een andere waarde op.";
        final String CHOOSE_OTHER_NAME = "Geef een andere naam op.";


    public void
        int newSuccesDefinition = Integer.valueOf(succesDefinitionField.getText());
        int numberOfQuestions = selectedQuestions.getItems().size();
        if (newSuccesDefinition < 1) {
            showAlert(SUCCESFACTOR_TOO_LOW, GIVE_OTHER_VALUE, "INFORMATION");
        } else if (newSuccesDefinition > numberOfQuestions) {
            showAlert(SUCCESFACTOR_TOO_HIGH, GIVE_OTHER_VALUE, "INFORMATION");
        } else {
            quiz.setSuccesDefinition(newSuccesDefinition);
            showAlert(STORING_SUCCES, null, "INFORMATION");
        }

        quizDAO = new QuizDAO(dbAccess);
        quizDAO.updateOne(quiz);
    }*/
}


/*    public List<Question> RemoveQuestionFromArrayList(List<Question> list, Question question) {
        for (int index = 0; index < list.size(); index++) {
            if (list.get(index).getQuestionText().equals(question.getQuestionText())) {
                list.remove(index);
            }
        } return list;
    }*/
/*    *//**
     * Confirmation dialogue
     * do you want to go back to Menu quiz?
     *//*
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
            else if (!result.isPresent()){}*/
/*

    }
    */
/**
     * Confirmation dialogue
     * do you want to go back to ManagaQuizzes?
     *//*

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

}*/
