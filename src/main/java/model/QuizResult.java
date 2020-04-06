package model;

import java.util.ArrayList;
import java.util.List;

public class QuizResult {
    private User user;
    private Course course;
    private Quiz quiz;
    private List<QuestionResult> questionResults;

    public QuizResult(User user, Quiz quiz, Course course) {
        this.user = user;
        this.quiz = quiz;
        this.course = course;
        this.questionResults = new ArrayList<>();
    }
    public void addQuestionResult(QuestionResult questionResult){
        if(questionResult == null){
            questionResults = new ArrayList<>();
        }
        questionResults.add(questionResult);
    }

    public QuestionResult getQuestionResult(int index) {
        return questionResults.get(index);
    }

    public void setQuestionResult (int index, QuestionResult questionResult){
        questionResults.set(index, questionResult);
    }

    @Override
    public String toString() {
        StringBuilder quizResulString = new StringBuilder();
        quizResulString.append("Quizresults \n");
        quizResulString.append("Student: \n");
        quizResulString.append(user.toString()).append("\n");
        quizResulString.append("Course: \n");
        quizResulString.append(course).append("\n");
        quizResulString.append("Quiz \n");
        quizResulString.append(quiz.getName()).append("\n");
        quizResulString.append(quiz.getSuccesDefinition()).append("\n");
        for(QuestionResult questionResult: questionResults){
            quizResulString.append(questionResult).append("\n");
        }return quizResulString.toString();
    }
}
