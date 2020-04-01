package model;

public class Question {
    private String rightAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;

    public Question(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public Question(String rightAnswer, String wrongAnswer1, String wrongAnswer2, String wrongAnswer3) {
        this.rightAnswer = rightAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
    }

}
