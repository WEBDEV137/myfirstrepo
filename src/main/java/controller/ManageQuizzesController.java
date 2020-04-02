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

public class ManageQuizzesController extends AbstractController {

    private QuizDAO quizDAO;
    private CourseDAO courseDAO;
    private DBAccess dbAccess;
    private User user;

    @FXML
    private ListView<Quiz> quizList;
    @FXML
    private Button newQuizButton;



    // connectie maken met dbase om courses te laten zien in het scherm listview
    public void setup() {
        user = Main.getCurrentUser();
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        this.quizDAO = new QuizDAO(dbAccess);
        this.courseDAO = new CourseDAO(dbAccess);
        List<Quiz> allQuizzesForUser = quizDAO.getAllByCoordinatorId(user.getUserId());
        for (Quiz quiz : allQuizzesForUser) {
            //Haal bij iedere quiz de cursus op uit database en setCourseName()
            Course bijbehorendeCourse = courseDAO.getOneById(quiz.getCourseId());
            String courseName = bijbehorendeCourse.getCoursename();
            quiz.setCourseName(courseName);
            //Voeg toe aan quizlist
            quizList.getItems().add(quiz);
        }


    }


    public void doMenu() {
        Main.getSceneManager().showWelcomeScene(user);
    }


    public void doCreateQuiz() {
        Main.getSceneManager().showCreateUpdateQuizScene( null);
    }


        public void doUpdateQuiz() {
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
            Main.getSceneManager().showCreateUpdateQuizScene( quiz);
    }

    public void doDeleteQuiz() {
       Quiz quiz =  quizList.getSelectionModel().getSelectedItem();
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

        String ARE_YOU_SURE = "U staat op het punt een quiz te wissen!\nAlle bijbehorende vragen zullen ook worden verwijderd.";
        String CLICK_CONTINUE = "Weet u zeker dat u wilt doorgaan?";

        ButtonType jaKnop = new ButtonType("Ja", ButtonBar.ButtonData.YES);
        ButtonType neeKnop = new ButtonType("Nee", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert okCancelDialogue = new Alert(Alert.AlertType.WARNING, CLICK_CONTINUE, jaKnop, neeKnop);
        okCancelDialogue.setTitle(Main.QUISMASTER);
        okCancelDialogue.setHeaderText(ARE_YOU_SURE);
        okCancelDialogue.initModality(Modality.APPLICATION_MODAL); //Achtegrpond scherm wordt onbruikbaar gemaakt.
        okCancelDialogue.initOwner(Main.getPrimaryStage()); //show,
        Optional<ButtonType> result = okCancelDialogue.showAndWait();
        if (result.get() == jaKnop) {
            doDeleteQuiz();
        }
        else if (!result.isPresent()){}
        }
    }

}