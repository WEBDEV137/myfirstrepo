package controller;

import database.mysql.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import view.Main;

import java.util.ArrayList;

/**
 * Deze controller klasse zorgt ervoor dat quizzen gemaakt, verwijderd en gewijzigd kunnen worden.
 */

public class CreateUpdateQuizController {

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

    /**
     * Deze Methode geeft de scherm setup voor een quiz Update;
     */
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
    /**
     * Deze Methode geeft de scherm setup voor voor het aanmaken van een nieuwe Quiz;
     */
    public void setupCreateNewQuiz() {
        quiz = new Quiz();
        createUpdateQuizHeader.setText("Nieuwe quiz aanmaken");
        populateCourseMenuButton();
        coursesMenuButton.setText("Cursus");
    }

    /**
     * Deze Methode voegt objecten uit een ArrayList toe aan een ListView
     * Hij geeft ook eventhandlers aan de items.
     */
    public void populateCourseMenuButton() {
        ArrayList<Course> coordinatorCourses = courseDAO.getAllByCoordinatorId(user.getUserId());
        for (Course course : coordinatorCourses) {
            MenuItem menuItem = new MenuItem(course.getCoursename());
            menuItem.setOnAction(event -> changeCourse(course));
            coursesMenuButton.setText(course.getCoursename());
            coursesMenuButton.getItems().add(menuItem);
        }
    }

    /**
     * Deze Methode wordt als eventhandler meegegeven aan de Menubutton waarmee een cursus geselecteerd kan worden
     * @param course
     *             De course die geselecteerd is
     */
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
    private void doDashboard() {
        Main.getSceneManager().showCoordinatorDashboard(quiz);
    }
    @FXML
    public void doManageQuizzes() {
        Main.getSceneManager().showManageQuizScene();
    }

    /**
     * Methode die de quiz opslaat in de database maar alleen als alle gegevens een juiste waarde bevatten.
     */
    @FXML
    public void doStore() {
        boolean nameIsOk = checkIsNewNameOk(quizNameField);
        boolean succesDefinitionIsOk = checkIsSuccesDefinitionOk(succesDefinitionField);
        boolean courseIsOk = checkIsCourseOk(quiz.getCourseId());

        if (nameIsOk && succesDefinitionIsOk && courseIsOk) {
            quiz.setSuccesDefinition(Integer.valueOf(succesDefinitionField.getText()));
            quiz.setName(quizNameField.getText());
            storeQuizInDataBase();
            checkIfStoringWasSuccesful();
        }
    }

    /**
     * Deze Methode checkt of het textveld voor de nieuwe quiznaam niet leeg is en of
     * het geen niet-toegestane karakters bevat.
     * @param newQuizName
     * @return
     */
    public boolean checkIsNewNameOk(TextField newQuizName) {
        boolean okForStoring = true;
        if (newQuizName.getText().isEmpty() || newQuizName.getText().length() < 1) {
            Coll.showAlert(Const.STRING_TOO_SHORT, Const.EMPTY_STRING, "INFORMATION");
            okForStoring = false;
        } else if (!Coll.checkIfNameAllowed(newQuizName.getText(), Const.NOT_ALLOWED_CHARACTERS)) {
            Coll.showAlert(Const.SOME_CHARACTERS_NOT_ALLOWED, Const.CHOOSE_OTHER, "INFORMATION");
            okForStoring = false;
        }
        return okForStoring;
    }

    /**
     * Deze Methode controleert of de opgegeven succesdefinitie goed is om opgeslagen te te worden
     * Hij moet van het type integer zijn en geen andere characters bevatten. hij mag ook niet leeg zijn.
     * @param succesDefinition
     * @return true /false
     *              geeft true als succesDefinition is goedgekeurd
     */
    public boolean checkIsSuccesDefinitionOk(TextField succesDefinition) {
        boolean okForStoring = true;
        int succesdefinitionParsed;
        if (succesDefinitionField.getText().isEmpty()) {
            Coll.showAlert(Const.NO_SUCCESFACTOR, Const.EMPTY_STRING, Const.INFORMATION);
            okForStoring = false;
        }
        else
            try{
                succesdefinitionParsed = Integer.parseInt(succesDefinitionField.getText());
                if ( succesdefinitionParsed < 0) {
                    Coll.showAlert(Const.SUCCESFACTOR_TOO_LOW, Const.EMPTY_STRING, Const.INFORMATION);
                    okForStoring = false;
                }
            }
            catch(NumberFormatException foutmelding){
                Coll.showAlert(Const.SUCCESFACTOR_NOT_NUMBER, Const.EMPTY_STRING, Const.INFORMATION);
                System.out.println(foutmelding.toString() + Const.SUCCESFACTOR_NOT_NUMBER);
                okForStoring = false;
            }
        return okForStoring;
    }

    public boolean checkIsCourseOk(int courseId) {
        boolean okForStoring = true;
        if (courseId == 0) {
            Coll.showAlert(Const.SLECT_COURSE, Const.EMPTY_STRING, "INFORMATION");
            okForStoring = false;
        }
        return okForStoring;
    }

    /**
     * Deze Methode slaat een quiz op in de database als het een nieuwe quiz betreft en
     * maakt een update in de database als het een wijziging betreft
     */
    public void storeQuizInDataBase() {
        if (isNewCourse) {
            quizDAO.storeOneAndReturnId(quiz);
        } else {
            System.out.println(quiz);
            quizDAO.updateOne(quiz);
        }
    }


    /**
     * Deze Methode controleerd of de in MySQL geupdate of nieuw opgeslagen quiz correct is opgeslagen.
     * als dat het geval is krijg je daar een melding van
     */
    public void checkIfStoringWasSuccesful(){
        if (isNewCourse) {
            Quiz controleQuiz = quizDAO.getOneById(quiz.getId());
            if (controleQuiz.getCourseId() == quiz.getCourseId()) {
                Coll.showAlert(Const.STORING_SUCCES, Const.EMPTY_STRING, Const.INFORMATION);
            }
        } else {
            Quiz controleQuiz = quizDAO.getOneById(quiz.getId());
            boolean nameIsStored = (quiz.getName().equals(controleQuiz.getName()));
            boolean courseIsStored = (quiz.getCourseId() == (controleQuiz.getCourseId()));
            boolean succesDefinitionIsStored = (quiz.getSuccesDefinition() == controleQuiz.getSuccesDefinition());
            if (nameIsStored && courseIsStored && succesDefinitionIsStored) {
                Coll.showAlert(Const.STORING_SUCCES, Const.EMPTY_STRING, Const.INFORMATION);
            }
        }
    }
}




