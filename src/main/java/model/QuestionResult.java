package model;

public class QuestionResult extends Question{

    private String answer;


    public QuestionResult(){
        this("Question is not viewed", "", "", "", "", "Question is not answered");
    }

    public QuestionResult(String questionText, String rightAnswer, String wrongAnswer1, String wrongAnswer2, String wrongAnswer3, String answer) {
        super(questionText, rightAnswer, wrongAnswer1, wrongAnswer2, wrongAnswer3);
        this.answer = answer;
    }

    //Setters
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    //Getters
    public String getAnswer() {
        return answer;
    }

    @Override
    public String toString() {
        StringBuilder questionResultString = new StringBuilder();
        questionResultString.append("Question: ").append(super.getQuestionText()).append("\n");
        questionResultString.append("Right answer: ").append(super.getRightAnswer()).append("\n");
        questionResultString.append("Wrong answer: ").append(super.getWrongAnswer1()).append("\n");
        questionResultString.append("Wrong answer: ").append(super.getWrongAnswer2()).append("\n");
        questionResultString.append("Wrong answer: ").append(super.getWrongAnswer3()).append("\n");
        questionResultString.append("Given answer: ").append(answer).append("\n");
        return questionResultString.toString();
    }
}
