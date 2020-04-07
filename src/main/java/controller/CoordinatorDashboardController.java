package controller;

import database.mysql.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.Course;
import model.Question;
import model.Quiz;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.List;

/**
 * Deze controller klasse  zorgt  voor een overzicht van courses, bijbehorende quizzen en bijbehorende vragen en de mogelijkheid quizzen
 * en vragen te wijzigen en toe te voegen
 */

public class CoordinatorDashboardController{

    private QuizDAO quizDAO;
    private QuestionDAO questionDAO;
    private CourseDAO courseDAO;
    private DBAccess dbAccess;
    private User user;
    private Quiz quiz;
    private Question question;
    private ArrayList<Quiz> quizzes;
    private List<Question> questions;


    @FXML
    private ListView<Course> courseList;
    @FXML
    private ListView<Quiz> quizList;
    @FXML
    private ListView<Question> questionList;


    public void setup(Quiz quiz) {
        setupMainObjects();
        Coll.populateListView(courseList, courseDAO.getAllByCoordinatorId(user.getUserId()));
        if(quiz!=null){
            quizList.getItems().add(quiz);
            questions = questionDAO.getAllQuestionsByQuizId(quiz.getId());
            Coll.populateListView(questionList, questions);
        ; }
        courseList.getSelectionModel().selectedItemProperty().addListener(

                new ChangeListener<Course>() {
                    @Override
                    public void changed(ObservableValue<? extends Course> observableValue, Course oldCourse, Course newCourse) {
                        quizzes = quizDAO.getAllByCourseId(newCourse.getId());
                        Coll.populateListView(quizList, quizzes);
                        questionList.getItems().clear();

                    }
                });
        quizList.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Quiz>() {
                    @Override
                    public void changed(ObservableValue<? extends Quiz> observableValue, Quiz oldQuiz, Quiz newQuiz) {
                        questions = questionDAO.getAllQuestionsByQuizId(newQuiz.getId());
                        Coll.populateListView(questionList, questions);
                    }
                });
    }

    public void setupMainObjects(){
        user = Main.getCurrentUser();
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        this.quizDAO = new QuizDAO(dbAccess);
        this.courseDAO = new CourseDAO(dbAccess);
        this.questionDAO = new QuestionDAO(dbAccess);
    }
    public void doNewQuiz() {
        Main.getSceneManager().showCreateUpdateQuizScene(null);
    }

    public void doEditQuiz() {
        quiz = quizList.getSelectionModel().getSelectedItem();
        Main.getSceneManager().showCreateUpdateQuizScene(quiz);
    }

    public void doNewQuestion() {
        Main.getSceneManager().showCreateUpdateQuestionScene(null);
    }

    public void doEditQuestion() {
        question = questionList.getSelectionModel().getSelectedItem();
        Main.getSceneManager().showCreateUpdateQuestionScene(question);
    }

    public void doMenu() {
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }
}
