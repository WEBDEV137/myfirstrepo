package model;

public class Question {
    private String questionText;
    private String rightAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;
    private int questionID;
    private int quizID;
    private String quizName;


//    public Question(String rightAnswer) {
//        this.rightAnswer = rightAnswer;
//    }

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public Question(String questionText, String rightAnswer, String wrongAnswer1, String wrongAnswer2, String wrongAnswer3) {
        this.questionText = questionText;
        this.rightAnswer = rightAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
    }

    //Toms Constructor
    public Question(int id, String text, int quizId) {
        this.questionID = id;
        this.questionText = text;
        this.quizID = quizId;
    }

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }

    public String getWrongAnswer3() {
        return wrongAnswer3;
    }

    public void setWrongAnswer3(String wrongAnswer3) {
        this.wrongAnswer3 = wrongAnswer3;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public int getQuestionID() {
        return questionID;
    }

    @Override
    public String toString() {
        return questionText;
    }
}