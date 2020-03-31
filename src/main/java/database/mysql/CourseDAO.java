//Maken CourseDAO om gegevens vanuit database te halen
package database.mysql;

import model.Course;
import model.Quiz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CourseDAO extends AbstractDAO {
    public CourseDAO(DBAccess dbAccess){
        super(dbAccess);
    }

    public  List <Course> getAllCourses() {
        List<Course> courseSheet = new ArrayList<>();
        String query = "SELECT * FROM cursus";
        Course course = null;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String coursename = resultSet.getString("naam");
                int coordinatorid = resultSet.getInt("coordinatorid");
                course = new Course(id, coursename, coordinatorid);
                courseSheet.add (course);
            }
        }
        catch (SQLException e) {
            System.out.println(" SQL error " + e.getMessage());
        }
        return courseSheet;
    }
    public Course getOneById (int id) {
        String query = "SELECT * FROM cursus WHERE id = ?;";
        Course course = null;
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            if (resultSet.next()) {
                String name = resultSet.getString("naam");
                int coordinatorId = resultSet.getInt("coordinatorid");
                course = new Course(id, name, coordinatorId);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return course;
    }

}
