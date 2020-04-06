package view;

import controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;

public class SceneManager {

    private Stage primaryStage;

    //CONSTRUCTOR
    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * laadt een scene In een FXMLLoader object
     */
    public FXMLLoader getScene(String fxml) {
        Scene scene;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            scene = new Scene(root);
            primaryStage.setScene(scene);
            return loader;
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
    }

    public void showExistingUserScene(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/createUpdateUser.fxml"));
            Parent root = loader.load();
            CreateUpdateUserController controller = loader.getController();
            controller.setup(user);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * laat bestaande courses zien
     **/
    public void showExistingCourseScene(Course course) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/fxml/createUpdateCourse.fxml"));
            Parent root = loader.load();
            CreateUpdateCourseController controller = loader.getController();
            controller.setup(course);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWindowTool() {
        FXMLLoader loader = getScene("/view/fxml/windowTool.fxml");
        if (loader != null) {
            WindowToolController controller = loader.getController();
            controller.populateScreenMenu();
        } else {
            System.out.println("set windowTool: Loader is not initialized");
            System.out.flush();
        }
    }

    public void showLoginScene() {
        FXMLLoader loader = getScene("/view/fxml/login.fxml");
        LoginController controller = loader.getController();
     }

    public void showWelcomeScene(User user) {
        FXMLLoader loader = getScene("/view/fxml/welcomeScene.fxml");
        WelcomeController controller = loader.getController();
        controller.setup(user);
    }

    public void showManageUserScene() {
        FXMLLoader loader = getScene("/view/fxml/manageUsers.fxml");
        ManageUsersController controller = loader.getController();
        controller.setup();
    }

    public void showCreateUpdateUserScene(User user) {
        FXMLLoader loader = getScene("/view/fxml/createUpdateUser.fxml");
        CreateUpdateUserController controller = loader.getController();
        controller.setup(user);
    }

    public void showManageCoursesScene() {
        FXMLLoader loader = getScene("/view/fxml/manageCourses.fxml");
        ManageCoursesController controller = loader.getController();
        controller.setup();
    }

    public void showCreateUpdateCourseScene(Course course) {
        FXMLLoader loader = getScene("/view/fxml/createUpdateCourse.fxml");
        CreateUpdateCourseController controller = loader.getController();
        controller.setup(course);
    }

    public void showManageGroupsScene() {
        FXMLLoader loader = getScene("/view/fxml/manageGroups.fxml");
        ManageGroupsController controller = loader.getController();
        controller.setup();
    }

    public void showCreateUpdateGroupScene(Group group) {
        FXMLLoader loader = getScene("/view/fxml/createUpdateGroup.fxml");
        CreateUpdateGroupController controller = loader.getController();
        controller.setup(group);
    }

    public void showManageQuizScene() {
        FXMLLoader loader = getScene("/view/fxml/manageQuizzes.fxml");
        ManageQuizzesController controller = loader.getController();
        controller.setup();
    }

    public void showCreateUpdateQuizScene(Quiz quiz) {
        FXMLLoader loader = getScene("/view/fxml/createUpdateQuiz.fxml");
        CreateUpdateQuizController controller = loader.getController();
        controller.setup(quiz);
    }

    public void showManageQuestionsScene() {
        FXMLLoader loader = getScene("/view/fxml/manageQuestions.fxml");
        ManageQuestionsController controller = loader.getController();
        controller.setup();
    }

    public void showCreateUpdateQuestionScene(Question question) {
        FXMLLoader loader = getScene("/view/fxml/createUpdateQuestion.fxml");
        CreateUpdateQuestionController controller = loader.getController();
        if (question != null) {
            controller.setup(question);
        }
    }

    public void showStudentSignInOutScene() {
        FXMLLoader loader = getScene("/view/fxml/studentSignInOut.fxml");
        StudentSignInOutController controller = loader.getController();
        controller.setup();
    }

    public void showSelectQuizForStudent() {
        FXMLLoader loader = getScene("/view/fxml/selectQuizForStudent.fxml");
        SelectQuizForStudentController controller = loader.getController();
        controller.setup();
    }

    public void showFillOutQuiz(Quiz quiz) {
        FXMLLoader loader = getScene("/view/fxml/fillOutQuiz.fxml");
        FillOutQuizController controller = loader.getController();
        controller.setup(quiz);
    }

    public void showStudentFeedback(Quiz quiz) {
        FXMLLoader loader = getScene("/view/fxml/studentFeedback.fxml");
        StudentFeedbackController controller = loader.getController();
        controller.setup(quiz);
    }

    public void showCoordinatorDashboard() {
        FXMLLoader loader = getScene("/view/fxml/coordinatorDashboard.fxml");
        CoordinatorDashboardController controller = loader.getController();
        controller.setup();
    }

    public void showWelcomeScene() {
    }
}
