package controller;

import model.Quiz;

public class CreateUpdateQuizController {

    public void setup(Quiz quiz) {
        titleLabel.setText(quiz.getName());
        klantnummerTextfield.setText(String.valueOf(customer.getCustomerId()));
        voorlettersTextfield.setText(customer.getInitials());
        tussenvoegselTextfield.setText(customer.getPrefix());
        achternaamTextfield.setText(customer.getSurName());
        mobielTextfield.setText(customer.getMobilePhone());
    }

    public void doMenu() {}

    public void doCreateUpdateQuiz() {}
}