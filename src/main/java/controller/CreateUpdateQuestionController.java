package controller;

import database.mysql.QuestionDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Question;
import view.Main;

public class CreateUpdateQuestionController {
    private Question newQuestion;
    private QuestionDAO questionDAO = new QuestionDAO(Main.getDBaccess());

    @FXML
    public TextField aanTePassenVraagTextfield;

    @FXML
    public Label titleLabel;

    @FXML
    public TextField nieuweVraagTextField;

    @FXML
    public Button createUpdateQuestionButton;

    @FXML
    Question selectedQuestion;

    public void setup(Question question) {
        titleLabel.setText("Wijzig vraag");
        createUpdateQuestionButton.setText("Wijzig vraag");
        aanTePassenVraagTextfield.setText(question.toString());
        selectedQuestion = question;
    }

    private void createQuestion(){
        String newQuestion = nieuweVraagTextField.getText();
        if (newQuestion.isEmpty()) {
            Alert nothingFilledIn = new Alert(Alert.AlertType.ERROR);
            nothingFilledIn.setContentText("Je moet een vraag invullen.");
            nothingFilledIn.show();
            return;
        }
        this.newQuestion = new Question(newQuestion);
    }

    public void doCreateUpdateQuestion() {
        createQuestion();
        if (selectedQuestion == null) {
                questionDAO.storeOne(newQuestion);
                Alert stored = new Alert(Alert.AlertType.INFORMATION);
                stored.setContentText("Vraag opgeslagen");
                stored.show();
        } else {
            questionDAO.updateQuestionByQuestionText(newQuestion.toString(), selectedQuestion.toString());
            Alert updated = new Alert(Alert.AlertType.INFORMATION);
            updated.setContentText("Vraag gewijzigd");
            updated.show();
        }
    }

    @FXML
    public void doBackToManage() {
        Main.getSceneManager().showManageQuestionsScene();
    }

    public void doMenu() { Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());}
}