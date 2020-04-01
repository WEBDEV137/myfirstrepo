package database.mysql;

import javafx.scene.control.Alert;
import model.Quiz;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuizDAO extends AbstractDAO {

    //CONSTRUCTOR
    public QuizDAO(DBAccess dbAccess){
        super(dbAccess);
     }


    public Quiz getOneById (int id) {
        String query = "SELECT * FROM quiz WHERE id = ?;";
    Quiz quiz = null;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            if (resultSet.next()) {
                String name = resultSet.getString("naam");
                int succesDefinition = resultSet.getInt("succesdefinitie");
                int courseId = resultSet.getInt("cursusid");
                quiz = new Quiz(id, name, succesDefinition, courseId);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return quiz;
    }

    public ArrayList<Quiz> getAll () {
        String query = "SELECT * FROM quiz ; ";
        ArrayList<Quiz> quizzes = null;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                if(quizzes == null){ quizzes = new ArrayList<>();}
                int id = resultSet.getInt("id");
                String name = resultSet.getString("naam");
                int succesDefinition = resultSet.getInt("succesdefinitie");
                int courseId = resultSet.getInt("cursusid");
                quizzes.add(new Quiz(id, name, succesDefinition, courseId));
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return quizzes;
    }
    public void storeOne(Quiz quiz){
        String query = "INSERT INTO quiz VALUES (DEFAULT, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1, quiz.getName());
            preparedStatement.setInt(2, quiz.getSuccesDefinition());
            preparedStatement.setInt(3, quiz.getCourseId());
            preparedStatement.execute();
        }
        catch (SQLException e) {
        System.out.println("SQL error " + e.getMessage());
        }
    }
    public ArrayList<Quiz> getAllByCoordinatorId(int coordinatoId) {
        ArrayList<Quiz> quizzes = null;
        String query = "SELECT * FROM quiz WHERE cursusid IN (SELECT id from cursus WHERE coordinatorid = ?)";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1, coordinatoId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                if(quizzes == null){ quizzes = new ArrayList<>();}
                int quizId = resultSet.getInt("id");
                String name = resultSet.getString("naam");
                int succesDefinition = resultSet.getInt("succesdefinitie");
                int courseId = resultSet.getInt("cursusid");
                Quiz quiz = new Quiz(quizId, name, succesDefinition, courseId);
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return quizzes;
    }
    public void removeOneById (int id) {
        String query = " DELETE FROM quiz WHERE id = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1,id);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }
}
