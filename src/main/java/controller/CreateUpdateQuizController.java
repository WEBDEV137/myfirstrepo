package controller;

import model.Quiz;
import model.User;
import view.Main;

public class CreateUpdateQuizController extends AbstractController{


    public void setup(User user,  Quiz quiz) {
        super.user = user;


    }
/*       titleLabel.setText(quiz.getName());
        klantnummerTextfield.setText(String.valueOf(customer.getCustomerId()));
        voorlettersTextfield.setText(customer.getInitials());
        tussenvoegselTextfield.setText(customer.getPrefix());
        achternaamTextfield.setText(customer.getSurName());
        mobielTextfield.setText(customer.getMobilePhone());
    }*/

    public void doMenu() {
        Main.getSceneManager().showWelcomeScene(user);
    }
    public void doManageQuizzes() {
        Main.getSceneManager().showManageQuizScene(user);
    }


    public void doCreateUpdateQuiz() {}

    public void doAddToQuiz(){}

    public void doStore (){}

    public void doRemoveFromQuiz (){}

    public void doCreateNewQuestion (){}



}