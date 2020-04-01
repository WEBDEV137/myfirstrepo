package database.mysql;

import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Question;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO extends AbstractDAO{
    // Constructor
    public QuestionDAO(DBAccess dbAccess) {
        super(dbAccess);
    }

    public List<Question> getQuestionList() {
        String query = "SELECT * FROM Vraag";
        List<Question> result = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getStatement(query);
            ResultSet resultSet = super.executeSelectPreparedStatement(preparedStatement);
            Question question;
            while (resultSet.next()) {
                String quizQuestion = resultSet.getString("tekst");
                question = new Question(quizQuestion);
                result.add(question);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return result;
    }
}
