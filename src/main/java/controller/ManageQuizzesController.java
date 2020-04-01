package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.Course;
import model.Quiz;
import model.User;
import view.Main;

import java.util.List;

public class ManageQuizzesController extends AbstractController {
    private QuizDAO quizDAO;
    private CourseDAO courseDAO;
    private DBAccess dbAccess;

    @FXML
    private ListView<Quiz> quizList;
    @FXML
    private Button newQuizButton;



    // connectie maken met dbase om courses te laten zien in het scherm listview
    public void setup(User user) {
        super.user = user;
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
        Main.getSceneManager().showCreateUpdateQuizScene(user, null);
    }


    public void doUpdateQuiz() {
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
            Main.getSceneManager().showCreateUpdateQuizScene(user, quiz);
    }

    public void doDeleteQuiz() {
       Quiz quiz =  quizList.getSelectionModel().getSelectedItem();
       QuizDAO quizDAO = new QuizDAO(dbAccess);
       quizDAO.removeOneById(quiz.getId());

    }
    public void doLogOut() {
        Main.getSceneManager().showLoginScene();
    }
}