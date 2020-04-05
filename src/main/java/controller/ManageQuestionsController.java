package controller;

import database.mysql.DBAccess;
import database.mysql.QuestionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.*;
import model.Question;
import view.Main;

import java.util.List;
import java.util.Optional;

public class ManageQuestionsController {
    public TextField warningText;
    private QuestionDAO questionDAO;
    private DBAccess dbAccess;

    @FXML
    ListView<Question> questionList;

    @FXML
    ListView<Question> answerList;

    public void setup() {
        this.questionDAO = new QuestionDAO(Main.getDBaccess());
        List<Question> questionList = questionDAO.getAll();
        for (Question question :
                questionList) {
            this.questionList.getItems().add(question);
        }
    }

    public void doMenu(){
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }

    public void doCreateQuestion(){
        Main.getSceneManager().showCreateUpdateQuestionScene(null);
    }

    @FXML
    public void doUpdateQuestion(){
        Question question = questionList.getSelectionModel().getSelectedItem();
        if (question == null){
            Alert nothingChosen = new Alert(Alert.AlertType.ERROR);
            nothingChosen.setContentText("Je moet een vraag kiezen.");
            nothingChosen.show();
            return;
        }
        Main.getSceneManager().showCreateUpdateQuestionScene(question);
    }

    public void doDeleteQuestion() {
        Question question = questionList.getSelectionModel().getSelectedItem();
        if (question == null) {
            Alert nietGekozenFout = new Alert(Alert.AlertType.ERROR);
            nietGekozenFout.setContentText("Je moet een vraag kiezen.");
            nietGekozenFout.show();
            return;
        }
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setContentText("Weet u zeker dat u deze vraag wilt verwijderen?");
        ButtonType deleteQuestion = new ButtonType("Verwijder vraag");
        ButtonType buttonTypeCancel = new ButtonType("Annuleer", ButtonBar.ButtonData.CANCEL_CLOSE);
        confirmation.getButtonTypes().setAll(deleteQuestion, buttonTypeCancel);
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get() == deleteQuestion) {
            questionDAO.removeOneByQuestionText(question.getQuestionText());
            Alert done = new Alert(Alert.AlertType.INFORMATION);
            done.setContentText("Vraag verwijderd");
            done.show();
            Main.getSceneManager().showManageQuestionsScene();
        }
    }

}
