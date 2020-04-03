package database.mysql;

import javafx.scene.control.Alert;
import model.Course;
import model.Group;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO extends AbstractDAO {

    //constructor
    public GroupDAO(DBAccess dbAccess){
        super(dbAccess);
    }

    public ArrayList<Group> getAll() {
        String sql = "SELECT * FROM groep;";
        ArrayList<Group> result = new ArrayList();
        try {
            PreparedStatement ps = getStatement(sql);
            ResultSet rs = super.executeSelectPreparedStatement(ps);
            while(rs.next()) {
                String groupName = rs.getString("naam");
                int groupId = rs.getInt("id");
                int teacherId = rs.getInt("docentid");
                int courseId = rs.getInt("cursusid");
                Group group = new Group(groupId, groupName, courseId, teacherId);
                result.add(group);
            }
        } catch (SQLException errormessage) {
            System.out.println("SQL error " + errormessage.getMessage());
        }
        return result;
    }


public void deleteGroupByName (String groupName) {
    String sql = "DELETE FROM groep WHERE naam = ?;";
    try {
        PreparedStatement preparedStatement = getStatement(sql);
        preparedStatement.setString(1, groupName);
        preparedStatement.execute();
    } catch (SQLException foutmelding) {
        System.out.println("SQL error "+ foutmelding.getMessage());
    }
}

    public void storeGroup(Group group) {
        String sql = "INSERT INTO groep VALUES(DEFAULT, ?, ?, ?);";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setString(1, group.getGroupName());
            ps.setInt(2, group.getUserId());
            ps.setInt(3, group.getCourseId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    public void updateGroup(Group group) {
        String sql = "UPDATE groep SET naam = ?, docentid = ?, cursusid = ? where id = ?;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setString(1, group.getGroupName());
            ps.setInt(2, group.getUserId());
            ps.setInt(3, group.getCourseId());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }


}
