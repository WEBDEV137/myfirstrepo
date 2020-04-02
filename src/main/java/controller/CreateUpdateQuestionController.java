package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Question;
import view.Main;

public class CreateUpdateQuestionController {
    private Question question;

    @FXML
    public TextField aanTePassenVraagTextfield;

    @FXML
    public Label titleLabel;

    @FXML
    public TextField nieuweVraagTextField;

    @FXML
    public Button createUpdateQuestionButton;

    @FXML
    ListView<Question> selectedQuestion;

    @FXML
    public void storeQuestion(ActionEvent actionEvent){

    }

    private void createQuestion(){
        StringBuilder warningText = new StringBuilder();

        String newQuestion = nieuweVraagTextField.getText();

        if (newQuestion.isEmpty()) {
            Alert nothingFilledIn = new Alert(Alert.AlertType.ERROR);
            nothingFilledIn.setContentText("Je moet een vraag invullen.");
            nothingFilledIn.show();
            return;
        }
        question = new Question(newQuestion);

    }

    public void setup(Question question) {
        titleLabel.setText("Wijzig vraag");
        createUpdateQuestionButton.setText("Wijzig vraag");
        this.selectedQuestion.getItems().add(question);
        aanTePassenVraagTextfield.setText(question.toString());
    }

    public void doMenu() { Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());}

    @FXML
    public void doBackToManage(ActionEvent actionEvent) {
        Main.getSceneManager().showManageQuestionsScene();
    }

    public void doCreateUpdateQuestion() {

    }
}