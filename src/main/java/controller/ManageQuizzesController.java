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

public class ManageQuizzesController {
    private QuizDAO quizDAO;
    private CourseDAO courseDAO;
    private DBAccess dbAccess;

    @FXML
    private ListView<Quiz> quizList;
    @FXML
    private Button newQuizButton;



    // connectie maken met dbase om courses te laten zien in het scherm listview
    public void setup(User user) {
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
            System.out.println(quiz);
        }


    }


    public void doMenu() {
    }


    public void doCreateQuiz() {
        Main.getSceneManager().showCreateUpdateQuizScene(null);
    }


    public void doUpdateQuiz() {
        Quiz quiz = quizList.getSelectionModel().getSelectedItem();
            Main.getSceneManager().showCreateUpdateQuizScene(quiz);
    }

    public void doDeleteQuiz() {
    }
}