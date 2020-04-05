package database.mysql;

        import javafx.scene.control.Alert;
        import model.Quiz;
        import model.User;

        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.util.ArrayList;

/**
 * QuizDAO om quiz gegevens op te slaan en op te halen uit een database,
 * @author Tom Janssen
 *
 */

public class QuizDAO extends AbstractDAO implements GenericDAO{

    //CONSTRUCTOR
    public QuizDAO(DBAccess dbAccess){
        super(dbAccess);
    }

    /**
     * Haal 1 quiz op uit de database
     *
     * @param  id
     *          de id van de quiz
     */
    @Override
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
    /**
     * Sla 1 quiz op in de database.
     *
     * @param type
     *          een quiz (die opgeslagen moet worden_)
     */
    @Override
    public void storeOne(Object type) {
        String query = "INSERT INTO quiz VALUES (DEFAULT, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1, ((Quiz)type).getName());
            preparedStatement.setInt(2, ((Quiz)type).getSuccesDefinition());
            preparedStatement.setInt(3, ((Quiz)type).getCourseId());
            executeManipulatePreparedStatement(preparedStatement);
        }
        catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }
    /**
     * Haal alle quizen op uit de database
     *
     *
     */
        @Override
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
    /**
     * Sla een quiz op in de database en geeft de automatisch gegenereerde quiz-id terug als int.
     *
     * @param quiz
     *            De quiz die opgeslagen moet worden
     */
    public int storeOneAndReturnId(Quiz quiz){
        String query = "INSERT INTO quiz VALUES (DEFAULT, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = getStatementWithKey(query);
            preparedStatement.setString(1, quiz.getName());
            preparedStatement.setInt(2, quiz.getSuccesDefinition());
            preparedStatement.setInt(3, quiz.getCourseId());
            int quizId = executeInsertPreparedStatement(preparedStatement);
            return quizId;
        }
        catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return 0;
    }
    /**
     * Update een quiz in de database door een quiz mee te geven aan deze methode.
     *
     * @param quiz
     *              de quiz die aangepast is
     */
    public void updateOne(Quiz quiz){
        String query = "UPDATE quiz SET naam = ?, succesdefinitie = ? , cursusid = ?  WHERE id = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1, quiz.getName());
            preparedStatement.setInt(2, quiz.getSuccesDefinition());
            preparedStatement.setInt(3, quiz.getCourseId());
            preparedStatement.setInt(4, quiz.getId());
            preparedStatement.execute();
        }
        catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    /**
     * Deze methode geeft een ArrayList terug met alle quizen van een coordinator.
     *
     * @param   coordinatorId
     *      de id van de coordinator
     */
    public ArrayList<Quiz> getAllByCoordinatorId(int coordinatorId) {
            ArrayList<Quiz> quizzes = null;
            String query = "SELECT * FROM quiz WHERE cursusid IN (SELECT id from cursus WHERE coordinatorid = ?)";
            try {
                PreparedStatement preparedStatement = getStatement(query);
                preparedStatement.setInt(1, coordinatorId);
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
    /**
     * Verwijder een quiz uit de database door de quizId mee te geven
     *
     * @param quizId
     *          de id van de quiz
     */
    public void removeOneById (int quizId) {
        String query = " DELETE FROM quiz WHERE id = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1,quizId);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }
    /**
     * Deze methode geeft een ArrayList terug met alle quizen van een coordinator.
     *
     * @param   userId
     *      de id van de gebruiker
     */
    public ArrayList<Quiz> getAllByUserId(int userId) {
        ArrayList<Quiz> quizzes = null;
        String query = "SELECT q.id quizid, q.naam quiznaam, q.succesdefinitie, q.cursusid , c.naam cursusnaam FROM quiz q JOIN cursus c ON q.cursusid = c.id JOIN groep gr ON gr.cursusid = c.id JOIN groepsindeling gi ON gr.id = groepid JOIN gebruiker gebr ON gebr.id = gi.gebruikerid WHERE gebr.id = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                if(quizzes == null){ quizzes = new ArrayList<>();}
                int quizId = resultSet.getInt("quizid");
                String name = resultSet.getString("quiznaam");
                int succesDefinition = resultSet.getInt("succesdefinitie");
                int courseId = resultSet.getInt("cursusid");
                String courseName = resultSet.getString("cursusnaam");
                Quiz quiz = new Quiz(quizId, name, succesDefinition, courseId);
                quiz.setCourseName(courseName);
                quizzes.add(quiz);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return quizzes;
    }
}
