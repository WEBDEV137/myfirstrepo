package model;

public class Answer {
    private String text;
    private boolean isRightAnswer;

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
}
