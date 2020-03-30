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

    public User getUserByInlognaam(String userName) {
        String query = "SELECT * FROM gebruiker WHERE inlognaam = ? ";
        User user = null;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet rs = executeSelectPreparedStatement(preparedStatement);
            if (rs.next()) {
                Integer userId = rs.getInt("id");
                String rolName = rs.getString("rol");
                String password = rs.getString("wachtwoord");
                String name = rs.getString("voornaam");
                String prefix = rs.getString("tussenvoegsels");
                String surName = rs.getString("achternaam");
                user = new User(userId,rolName,userName,password,name, prefix,surName);
                user.setUserName(userName);
            } else {
                System.out.println("Combinatie van inlognaam en wachtwoord komt niet voor in database");
                /*Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.ERROR);
                verkeerdeInlogGegevens.setContentText("Onjuiste inloggevens");
                verkeerdeInlogGegevens.show();*/
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return user;
    }


    public void storeUser(User user) {
        String sql = "Insert into Gebruiker(rol,inlognaam,wachtwoord,voornaam,tussenvoegsels,achternaam) values(?,?,?,?,?,?) ;";
        try {
            PreparedStatement ps = getStatementWithKey(sql);
            ps.setString(1, user.getRolName());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getName());
            ps.setString(5, user.getPrefix());
            ps.setString(6, user.getSurname());
            int key = executeInsertPreparedStatement(ps);
            user.setUserId(key);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }
}
