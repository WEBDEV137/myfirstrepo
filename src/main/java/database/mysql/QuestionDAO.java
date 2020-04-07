package database.mysql;

import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Question;
import model.Quiz;
import model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO extends AbstractDAO implements GenericDAO<Question>{
    // Constructor
    public QuestionDAO(DBAccess dbAccess) {
        super(dbAccess);
    }


    public Question getOneById(int id) {
        String query = "SELECT * FROM Vraag WHERE id = ?;";
        Question question = null;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = super.executeSelectPreparedStatement(preparedStatement);
            int questionId = resultSet.getInt("id");
            String quizQuestion = resultSet.getString("tekst");
            int quizId = resultSet.getInt("quizid");
            question = new Question(questionId, quizQuestion, quizId);
            System.out.println(question);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return question;
    }

    public ArrayList<Question> getAll() {
        String query = "SELECT * FROM Vraag;";
        ArrayList<Question> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getStatement(query);
            ResultSet resultSet = super.executeSelectPreparedStatement(preparedStatement);
            Question question;
            while (resultSet.next()) {
                int questionId = resultSet.getInt("id");
                String quizQuestion = resultSet.getString("tekst");
                int quizId = resultSet.getInt("quizid");
                question = new Question(questionId, quizQuestion, quizId);
                result.add(question);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return result;
    }

    /**
     * Get all questions that belong to a quiz (insert QuizId)
     *
     */
    public List<Question> getSelectedQuestionsByQuizId(int quizId) {
        String query = "SELECT * FROM Vraag WHERE id IN (SELECT vraagid FROM quizvraag WHERE quizid = ?);";
        List<Question> questions = new ArrayList<>();
        Question question;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1, quizId);
            ResultSet resultSet = super.executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                String questionText = resultSet.getString("tekst");
                int questionId = resultSet.getInt("id");
                question = new Question(questionId, questionText, quizId);
                questions.add(question);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return questions;
    }

        public List<Question> getAllQuestionsByQuizId(int quizId) {
            String query = "SELECT * FROM Vraag WHERE quizid = ?; ";
            List<Question> questions = new ArrayList<>();
            Question question;
            try {
                PreparedStatement preparedStatement = getStatement(query);
                preparedStatement.setInt(1, quizId);
                ResultSet resultSet = super.executeSelectPreparedStatement(preparedStatement);
                while (resultSet.next()) {
                    String questionText = resultSet.getString("tekst");
                    int questionId = resultSet.getInt("id");
                    question = new Question(questionId, questionText, quizId);
                    questions.add(question);
                }
            } catch (SQLException e) {
                System.out.println("SQL error " + e.getMessage());
            }
            return questions;


    }


    public void removeOneByQuestionText (String questionText) {
        String query = " DELETE FROM vraag WHERE tekst = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1,questionText);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    public void storeOne(Question question) {
        String query = "INSERT INTO vraag VALUES (DEFAULT, ?, ?);";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1,question.getQuestionText());
            preparedStatement.setInt(2,question.getQuizID());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    public void updateQuestionByQuestionText (String questionText, String updatableText){
        String query = "UPDATE vraag SET tekst = ? WHERE tekst = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1,questionText);
            preparedStatement.setString(2,updatableText);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    public int storeEmptyQuestionByQuizId(Quiz quiz) {
        String query = "INSERT INTO vraag VALUES (DEFAULT,\'Vul vraag in\',?);";
        try {
            PreparedStatement preparedStatement = getStatementWithKey(query);
            preparedStatement.setInt(1, quiz.getId());
            int vraagId = executeInsertPreparedStatement(preparedStatement);
            return vraagId;
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }return 0;
    }

    public int getIdByQuestionText(String questionText) {
        String query = "SELECT id FROM Vraag WHERE tekst = ?;";
        int questionId = 0;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1, questionText);
            ResultSet resultSet = super.executeSelectPreparedStatement(preparedStatement);
            questionId = resultSet.getInt("id");
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return questionId;
    }
}
