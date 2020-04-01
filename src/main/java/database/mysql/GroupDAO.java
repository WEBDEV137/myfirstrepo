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

    public ArrayList<Group> getAllGroupsByName() {
        String sql = "SELECT naam FROM groep";
        ArrayList result = new ArrayList();
        try {
            PreparedStatement ps = this.getStatement(sql);
            ResultSet rs = super.executeSelectPreparedStatement(ps);
            while(rs.next()) {
                String groupName = rs.getString("naam");
                result.add(groupName);
            }
        } catch (SQLException errormessage) {
            System.out.println("SQL error " + errormessage.getMessage());
        }

        return result;
    }


    public void deleteGroup(Group group){
        String sql = "DELETE FROM groep WHERE naam = ?;";
        try {
            PreparedStatement ps = this.getStatement(sql);
            ps.setString(2, String.valueOf(group));
        } catch (SQLException e){
            System.out.println("SQL Error "+e.getMessage());
        }

    }



}
