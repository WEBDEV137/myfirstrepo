package database.mysql;

import model.Quiz;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Methodes voor het ophalen en ontvangen van quizvraag gegeven ui de database.
 * quizvraag is een gegeven welke vraag gebruikt wordt in een quiz.
 *
 *
 */
public class QuizQuestionDAO extends AbstractDAO{

    public QuizQuestionDAO(DBAccess dbAccess){
        super(dbAccess);
    }

    /**
     * Verwijdert een quizvraag record uit de database
     *
     * @param vraagid
     *      id van de vraag
     */
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

    /**
     * Geeft alle vraagId's terug van de vragen die bij een quiz zijn gekozen zijn.
     *
     * @param quiz
     *          quiz waarvan je de gekozen vragen wil weten
     */
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

    /**
     * Sla 1 quizvraag op in de database.
     *
     * @param quizid
     * @param vraagid
     *          een quizid van de gekozen quiz en de quizid van de quiz waarbij hij gekozen is
     */
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
    /**
     * Verwijder alle quizvragen die bij een quiz horen.
     *
     * @param quizid
     *          quizid
     */
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
