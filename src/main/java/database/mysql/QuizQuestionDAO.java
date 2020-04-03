package database.mysql;

import model.Quiz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizQuestionDAO extends AbstractDAO{

    public QuizQuestionDAO(DBAccess dbAccess){
        super(dbAccess);
    }

    public void removeOneByQuestionId(int vraagid) {
        String query = " DELETE FROM quizvraag WHERE vraagid = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1,vraagid);
            executeManipulatePreparedStatement(preparedStatement);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }
    public ArrayList<Integer> getAllbyQuizId (Quiz quiz) {
        String query = "SELECT * FROM quizvraag WHERE quizid = ? ; ";
        ArrayList<Integer> vraagIds = null;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1, quiz.getId());
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                if(vraagIds == null){ vraagIds = new ArrayList<>();}
                Integer vraagId = resultSet.getInt("vraagid");
                vraagIds.add(vraagId);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return vraagIds;
    }
    public void storeOne(int quizid, int vraagid){
        String query = "INSERT INTO quizvraag VALUES (?, ?);";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1, quizid);
            preparedStatement.setInt(2, vraagid);
           executeManipulatePreparedStatement(preparedStatement);
        }
        catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }
    public void dropAllFromQuiz(int quizid) {
        String query = " DELETE FROM quizvraag WHERE quizid = ?;";
        try {

            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1, quizid);
            executeManipulatePreparedStatement(preparedStatement);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }


}
