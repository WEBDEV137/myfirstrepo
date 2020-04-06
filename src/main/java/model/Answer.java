package model;

public class Answer {
    private String text;
    private boolean isRightAnswer;
    private int questionId;

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Answer(){
        this("", false);
    }
    public Answer(String text, boolean isRightAnswer) {
        this.text = text;
        this.isRightAnswer = isRightAnswer;
    }
    public String getText() {
        return text;
    }
    public boolean isRightAnswer() {
        return isRightAnswer;
    }

    @Override
    public String toString() {
        return text;
    }
}
