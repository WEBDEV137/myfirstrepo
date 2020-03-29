package database.mysql;

import javafx.scene.control.Alert;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends AbstractDAO{

    //CONSTRUCTOR
    public UserDAO(DBAccess dbAccess){
        super(dbAccess);
    }

    public User getUserByInlognaam(String inlognaam) {
        String query = "SELECT * FROM gebruiker WHERE inlognaam = ?";
        User user = null;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1, inlognaam);;
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            if (resultSet.next()) {
                String rol = resultSet.getString("rol");
                String wachtwoord = resultSet.getString("wachtwoord");
                user = new User(inlognaam, wachtwoord, rol);
            } else {
                System.out.println("Geen gebruiker opgehaald uit database");
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return user;
    }
}
