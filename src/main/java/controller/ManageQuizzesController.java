package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import model.Course;
import model.Quiz;
import model.User;
import view.Main;

import java.util.List;
import java.util.Optional;
/**
 * Deze controller klasse zorgt ervoor dat quizzen gemaakt, verwijderd en gewijzigd kunnen worden.
 */


public class ManageQuizzesController{

    private QuizDAO quizDAO;
    private CourseDAO courseDAO;
    private DBAccess dbAccess;
    private User user;
    private Quiz quiz;

    @FXML
    private ListView<Quiz> quizList;

    public void setup() {
        user = Main.getCurrentUser();
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        this.quizDAO = new QuizDAO(dbAccess);
        this.courseDAO = new CourseDAO(dbAccess);
        List<Quiz> allQuizzesForUser = quizDAO.getAllByCoordinatorId(user.getUserId());
        for (Quiz quiz : allQuizzesForUser) {
            quizList.getItems().add(quiz);
        }
    }

    public void doMenu() {
        Main.getSceneManager().showWelcomeScene(user);
    }

    public void doCreateQuiz() {
        Main.getSceneManager().showCreateUpdateQuizScene(null);
    }

    public void doUpdateQuiz() {
        quiz = quizList.getSelectionModel().getSelectedItem();
        Main.getSceneManager().showCreateUpdateQuizScene(quiz);
    }
    public void doDeleteQuiz() {
       quiz =  quizList.getSelectionModel().getSelectedItem();
       if (quiz != null) {
           QuizDAO quizDAO = new QuizDAO(dbAccess);
           quizDAO.removeOneById(quiz.getId());
           Main.getSceneManager().showManageQuizScene();
       }
    }
    public void doLogOut() {
        Main.getSceneManager().showLoginScene();
    }

    /**
     * Confirmation dialogue
     * do you want to Delete quiz?
     */
    public void doDeleteConfirmation() {
        if(quizList.getSelectionModel().getSelectedItem() != null){
        ButtonType jaKnop = new ButtonType("Ja", ButtonBar.ButtonData.YES);
        ButtonType neeKnop = new ButtonType("Nee", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert okCancelDialogue = new Alert(Alert.AlertType.INFORMATION, Const.CLICK_CONTINUE, jaKnop, neeKnop);
        okCancelDialogue.setTitle(Main.QUIZMASTER);
        okCancelDialogue.setHeaderText(Const.ARE_YOU_SURE);
        okCancelDialogue.initModality(Modality.APPLICATION_MODAL);
        okCancelDialogue.initOwner(Main.getPrimaryStage());
        Optional<ButtonType> result = okCancelDialogue.showAndWait();
        if (result.get() == jaKnop) {
            doDeleteQuiz();
        }
        else if (!result.isPresent()){}
        }
    }
}