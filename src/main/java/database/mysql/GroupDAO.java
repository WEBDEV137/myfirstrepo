package database.mysql;

import javafx.scene.control.Alert;
import model.Group;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDAO extends AbstractDAO implements GenericDAO {

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
                Group group = new Group(groupId, groupName, teacherId, courseId);
                result.add(group);
            }
        } catch (SQLException errormessage) {
            System.out.println("SQL error " + errormessage.getMessage());
        }
        return result;
    }

    @Override
    public Group getOneById(int id) {
        String query = "SELECT * FROM groep WHERE id = ?;";
        Group group = null;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet rs = executeSelectPreparedStatement(preparedStatement);
            if (rs.next()) {
                String groupName = rs.getString("naam");
                int teacherId = rs.getInt("docentid");
                int courseId = rs.getInt("cursusid");
                group = new Group(id, groupName, teacherId, courseId);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return group;
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

    @Override
    public void storeOne (Object type) {
        String sql = "INSERT INTO groep VALUES(DEFAULT, ?, ?, ?);";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setString(1, ((Group)type).getGroupName());
            ps.setInt(2, ((Group)type).getUserId());
            ps.setInt(3, ((Group)type).getCourseId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }


    public void updateGroup(Group group) {
        String sql = "UPDATE groep SET naam = ?, docentid = ? where id = ?;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setString(1, group.getGroupName());
            ps.setInt(2, group.getUserId());
            ps.setInt(3, group.getGroupId());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }


}
