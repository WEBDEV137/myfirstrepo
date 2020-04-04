package database.mysql;

import controller.AbstractController;
import model.Group;
import model.Question;
import model.Quiz;
import model.User;
import view.Main;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO implements GenericDAO{

    //CONSTRUCTOR
    public UserDAO(DBAccess dbAccess){
        super(dbAccess);
    }

    /**
     * om all users te brengen in een lijst
     * @return
     */
    @Override
    public ArrayList<User> getAll() {
        String sql = "Select * From Gebruiker";
        ArrayList<User> result = new ArrayList<>();
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

    /**
     * brengt een user met de id.
     * @param id
     * @return
     */
    @Override
    public Object getOneById(int id) {
        String query = "SELECT * FROM gebruiker WHERE id = ?;";
        User user = null;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet rs = executeSelectPreparedStatement(preparedStatement);
            if (rs.next()) {
                String rolName = rs.getString("rol");
                String userName = rs.getString("inlognaam");
                String password = rs.getString("wachtwoord");
                String name = rs.getString("voornaam");
                String prefix = rs.getString("tussenvoegsels");
                String surName = rs.getString("achternaam");
                user = new User(id,rolName,userName,password,name, prefix,surName);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return user;
    }
    /**
     *
     * Sla een een user in database.
     */
    @Override
    public void storeOne(Object type) {
        String sql = "Insert into Gebruiker values(DEFAULT,?, ?, ?, ?, ?, ?) ;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setString(1,((User)type).getRolName().substring(0,1).toUpperCase() + ((User)type).getRolName().substring(1).toLowerCase());
            ps.setString(2, ((User)type).getUserName());
            ps.setString(3, ((User)type).getPassword());
            ps.setString(4, ((User)type).getName().substring(0,1).toUpperCase() + ((User)type).getName().substring(1).toLowerCase());
            ps.setString(5, ((User)type).getPrefix());
            ps.setString(6, ((User)type).getSurname().substring(0,1).toUpperCase() + ((User)type).getSurname().substring(1).toLowerCase());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    /**
     * brengt user met username.
     * @param userName
     * @return
     */
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

    /**
     * bring user met de rol.
     * @param rol
     * @return
     */
    public ArrayList<User> getUsersByRole(String rol) {
        String sql = "SELECT * FROM gebruiker WHERE rol = ?;";
        ArrayList <User> allTeachers = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setString(1, rol);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                int userID= resultSet.getInt("id");
                String userName = resultSet.getString("inlognaam");
                String password = resultSet.getString("wachtwoord");
                String name = resultSet.getString("voornaam");
                String prefix = resultSet.getString("tussenvoegsels");
                String surname = resultSet.getString("achternaam");
                User user = new User(userID, rol, userName, password, name, prefix, surname);
                allTeachers.add(user);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        System.out.println(allTeachers);
        return allTeachers;
    }

    public int getUserIdByLoginName(String userName){
        int userId = 0;
        String sql = "SELECT * FROM gebruiker WHERE inlognaam = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setString(1, userName);
            ResultSet rs = executeSelectPreparedStatement(preparedStatement);
            if (rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
            AbstractController.showAlert("x", "x", "x");
        }
        return userId;
    }



    /**
     * om user te update
     * @param user
     */
    public void updateUser(User user) {
        String sql = "Update Gebruiker Set rol = ?, inlognaam = ?, wachtwoord = ?, voornaam = ?, tussenvoegsels = ?, achternaam = ? where id = ?;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setString(1, user.getRolName().substring(0,1).toUpperCase() + user.getRolName().substring(1).toLowerCase());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getName().substring(0,1).toUpperCase() + user.getName().substring(1).toLowerCase());
            ps.setString(5, user.getPrefix());
            ps.setString(6, user.getSurname());
            ps.setInt(7, user.getUserId());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    /**
     * om user te werwijderen.
     * @param user
     */
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
