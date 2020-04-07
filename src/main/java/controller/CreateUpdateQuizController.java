package controller;

import database.mysql.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import view.Main;

import java.util.ArrayList;

public class CreateUpdateQuizController{

    private QuizDAO quizDAO;
    private CourseDAO courseDAO;
    private DBAccess dbAccess;
    private User user;
    private Quiz quiz;
    private boolean isNewCourse = false;
    @FXML
    private Label createUpdateQuizHeader;
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
        createUpdateQuizHeader.setText("Nieuwe quiz aanmaken");
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
    @FXML
    public void doStore() {
        try {
            setQuizName();
        } catch (Exception foutmelding) {
            System.out.println(foutmelding.toString());
            Coll.showAlert(Const.SUCCESFACTOR_, Const.EMPTY_STRING, Const.INFORMATION);
        }
        try {
            quiz.setSuccesDefinition(Integer.valueOf(succesDefinitionField.getText()));
        } catch (Exception foutmelding) {
            Coll.showAlert(Const.SUCCESFACTOR_TOO_HIGH, Const.EMPTY_STRING, Const.INFORMATION);
            System.out.println(foutmelding.toString());
        }
        if (isNewCourse) {
            try {
                quizDAO.storeOneAndReturnId(quiz);
                Coll.showAlert(Const.STORING_SUCCES, Const.EMPTY_STRING, Const.INFORMATION);
            } catch (Exception foutmelding) {
                System.out.println(foutmelding.toString());
            }
        } else {
            try {
                quizDAO.updateOne(quiz);
                Coll.showAlert(Const.STORING_SUCCES, Const.EMPTY_STRING, Const.INFORMATION);
            } catch (Exception foutmelding) {
                System.out.println(foutmelding.toString());
            }
        }
    }

    public boolean setQuizName() {
        String newQuizName = quizNameField.getText();
        boolean nameIsAllowed = Coll.checkIfNameAllowed(newQuizName, Const.NOT_ALLOWED_CHARACTERS);
        if (!nameIsAllowed) {
            Coll.showAlert(Const.SOME_CHARACTERS_NOT_ALLOWED, Const.CHOOSE_OTHER, "INFORMATION");
            return false;
        } else if (newQuizName.length() < 1) {
            Coll.showAlert(Const.STRING_TOO_SHORT, Const.EMPTY_STRING, "INFORMATION");
            return false;
        }
         else {
            quiz.setName(quizNameField.getText());
            return true;
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
}