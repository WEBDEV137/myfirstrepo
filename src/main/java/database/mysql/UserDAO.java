package database.mysql;

import controller.AbstractController;
import model.Group;
import model.User;
import view.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO{
    //VARIABELEN


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
            } else {
                System.out.println("Combinatie van inlognaam en wachtwoord komt niet voor in database");

            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
            AbstractController.showAlert("x", "x", "x");
        }
        return user;
    }


    public void storeUser(User user) {
        String sql = "Insert into Gebruiker values(DEFAULT,?, ?, ?, ?, ?, ?) ;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setString(1, user.getRolName());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getName());
            ps.setString(5, user.getPrefix());
            ps.setString(6, user.getSurname());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    public void updateUser(User user) {
        String sql = "Update Gebruiker Set rol = ?, inlognaam = ?, wachtwoord = ?, voornaam = ?, tussenvoegsels = ?, achternaam = ? where id = ?;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setString(1, user.getRolName());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getName());
            ps.setString(5, user.getPrefix());
            ps.setString(6, user.getSurname());
            ps.setInt(7, user.getUserId());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {

        String sql = "Select * From Gebruiker";
        List<User> result = new ArrayList<>();
        try {
            PreparedStatement ps = getStatement(sql);
            ResultSet rs = super.executeSelectPreparedStatement(ps);
            User user;
            while (rs.next()) {
                String rolName = rs.getString("rol");
                String userName = rs.getString("inlognaam");
                String password = rs.getString("wachtwoord");
                String name = rs.getString("voornaam");
                String prefix = rs.getString("tussenvoegsels");
                String surName = rs.getString("achternaam");
                user = new User(rolName,userName,password,name, prefix,surName);
                user.setUserId(rs.getInt("id"));
                result.add(user);
            }
        } catch (SQLException e){
            System.out.println("SQL error " + e.getMessage());
        }
        return  result;
    }

    public void deleteUser(User user){
        String sql = "DELETE FROM gebruiker WHERE id = ?;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, user.getUserId());
            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("SQL Error "+e.getMessage());
        }

    }

}
