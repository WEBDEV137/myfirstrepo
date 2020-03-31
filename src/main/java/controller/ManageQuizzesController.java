package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.QuizDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import model.Course;
import model.Quiz;
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
    @FXML
    private Slider slider;


    // connectie maken met dbase om courses te laten zien in het scherm listview
    public void setup() {
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        this.quizDAO = new QuizDAO(dbAccess);
        this.courseDAO = new CourseDAO(dbAccess);
        List<Quiz> allQuizzes = quizDAO.getAll();
        for (Quiz quiz : allQuizzes) {
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

    //nieuwe cursus maken: doorgaan naar scherm createupdatecourse
    public void doCreateQuiz() {
        Main.getSceneManager().showCreateUpdateCourseScene(null);
    }


    public void doUpdateQuiz() {
    }

    public void doDeleteQuiz() {
    }
}