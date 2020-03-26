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

    public User getUserByInlognaamEnWachtwoord(String inlognaam, String wachtwoord) {
        String query = "SELECT * FROM gebruiker WHERE inlognaam = ? AND wachtwoord = ?";
        User user = null;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1, inlognaam);
            preparedStatement.setString(2, wachtwoord);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            if (resultSet.next()) {
                String rol = resultSet.getString("rol");
                user = new User(inlognaam, wachtwoord, rol);
            } else {
                System.out.println("Combinatie van inlognaam en wachtwoord komt niet voor in database");
                Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.ERROR);
                verkeerdeInlogGegevens.setContentText("Onjuiste inloggevens");
                verkeerdeInlogGegevens.show();
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return user;
    }
}
