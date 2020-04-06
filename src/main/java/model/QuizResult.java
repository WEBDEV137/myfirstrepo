package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Deze klasse wordt gebruikt op de resultaten van een door een student ingevulde quiz op te slaan.
 * omdat cursussen en quizzen kunnen worden verwijderdt, worden de bwlangrijkste gegevens daarvan voor ook hierin opgeslagen.
 */

public class QuizResult {
    private int userid;
    private String username;
    private int courseId;
    private String courseName;
    private int quizId;
    private String quizName;
    private int succesDefinition;
    private Date date;
    private List<QuestionResult> questionResults;

    public QuizResult(User user, Quiz quiz, Course course) {
        this.userid = user.getUserId();
        this.username = user.getUserName();
        this.courseId = course.getId();
        this.courseName = course.getCoursename();
        this.quizId = quiz.getId();
        this.quizName = quiz.getName();
        this.succesDefinition = quiz.getSuccesDefinition();
        this.questionResults = new ArrayList<>();
    }

    //Setters
    public void setQuestionResult (int index, QuestionResult questionResult){
        questionResults.set(index, questionResult);
    }
    public void setDate(Date date) {
        this.date = date;
    }

    //Getters
    public int getUserid() {
        return userid;
    }
    public int getQuizId() {
        return quizId;
    }
    public QuestionResult getQuestionResult(int index) {
        return questionResults.get(index);
    }
    public String getQuizName() {
        return quizName;
    }
    public int getSuccesDefinition() {
        return succesDefinition;
    }
    public List<QuestionResult> getQuestionResults() {
        return questionResults;
    }
    public int getCountCorrectAnswers(){
        int correctAnswerCount = 0;
        for (QuestionResult questionResult : questionResults)
            if (questionResult.getRightAnswer().equals(questionResult.getAnswer())){
                correctAnswerCount++;
            }return correctAnswerCount;
    }
    public int getCountQuestions(){
        return questionResults.size();
    }
    public void addQuestionResult(QuestionResult questionResult){
        if(questionResult == null){
            questionResults = new ArrayList<>();
        }
        questionResults.add(questionResult);
    }
    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        StringBuilder quizResulString = new StringBuilder();
        quizResulString.append("Quizresults \n");
        quizResulString.append("Student: \n");
        quizResulString.append(username).append("\n");
        quizResulString.append("Course: \n");
        quizResulString.append(courseName).append("\n");
        quizResulString.append("Quiz \n");
        quizResulString.append(quizName).append("\n");
        quizResulString.append(succesDefinition).append("\n");
        for(QuestionResult questionResult: questionResults){
            quizResulString.append(questionResult).append("\n");
        }return quizResulString.toString();
    }

}
