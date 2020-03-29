package database.mysql;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends AbstractDAO{
    //VARIABELEN


    //CONSTRUCTOR
    public UserDAO(DBAccess dbAccess){
        super(dbAccess);
    }

    public User getUserByInlognaam(String inlognaam) {
        //String query = "SELECT * FROM gebruiker WHERE inlognaam = ?";
        String query = getSelectAllQuery(TABEL_GEBRUIKER, KOLOM_INLOGNAAM);
        User user = null;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1, inlognaam);;
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            if (resultSet.next()) {
                String rol = resultSet.getString(KOLOM_ROL);
                String wachtwoord = resultSet.getString(KOLOM_WACHTWOORD);
                user = new User(inlognaam, wachtwoord, rol);
            } else {
                System.out.println(NIKS_OPGEHAALD);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return user;
    }
}
