package model;

public class QuestionResult extends Question{

    private String Answer;


    public QuestionResult(){
        this("", "", "", "", "", "");
    }

    public QuestionResult(String questionText, String rightAnswer, String wrongAnswer1, String wrongAnswer2, String wrongAnswer3, String answer) {
        super(questionText, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3);
        Answer = answer;
    }

    public void setAnswer(String answer) {
        Answer = answer;
    }
}
