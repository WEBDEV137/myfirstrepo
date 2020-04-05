package database.mysql;

import model.Answer;
import model.Quiz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AnswerDAO extends AbstractDAO {

    public AnswerDAO(DBAccess dBaccess) {
        super(dBaccess);
    }


    /**
     * Haal alle alle drie de antwoorden op die bij een vraag horen
     *
     * @param questionId De id van de vraag
     * @return ArrayList<Answer>
     * Een lijst met de antwoorden van de vraag. Het juiste antwoord staat op plek met index 0
     */
    public ArrayList<Answer> getAllByQuestionId(int questionId) {
        String query = "SELECT * FROM antwoord WHERE vraagid = ?;";
        ArrayList<Answer> answers = new ArrayList<>();
        Answer answer;
        Answer rightAnswer = new Answer();
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1, questionId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                String text = resultSet.getString("tekst");
                boolean isRightAnswer = resultSet.getBoolean("isjuistantwoord");
                answer = new Answer(text, isRightAnswer);
                if (answer.isRightAnswer()) {
                    rightAnswer = answer;
                }
                else { answers.add(answer);
                }
            }
            if (!rightAnswer.isRightAnswer()) {
                System.out.println("No right answer for this question! id: " + questionId);
            }
            else{
                answers.add(0, rightAnswer);
                }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        } return answers;
    }
}
