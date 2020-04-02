//Maken CourseDAO om gegevens vanuit database te halen
package database.mysql;

import model.Course;
import model.Quiz;
import model.User;

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
//Wegschrijven van nieuwe course naar SQL
    public void storeCourse(Course course) {
        String sql = "Insert into cursus(naam, coordinatorid) values(?,?) ;";
        try {
            PreparedStatement ps = getStatementWithKey(sql);
            ps.setString(1, course.getCoursename());
            ps.setInt(2, course.getCoordinatorid());
            int key = executeInsertPreparedStatement(ps);
            course.setId(key);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    public void updateCourse(Course course) {
        String sql = "Update cursus Set naam = ?, coordinatorid = ? where id = ?;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setString(1, course.getCoursename());
            ps.setInt(2, course.getCoordinatorid());
            ps.setInt(3, course.getId());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    public ArrayList<Course> getAllByCoordinatorId(int coordinatorId) {
        ArrayList<Course> courses = null;
        String query = "SELECT * FROM cursus WHERE coordinatorid = ? ;";
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1, coordinatorId);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                System.out.println("check 111");
                if(courses == null){ courses = new ArrayList<>();
                    System.out.println("check 222");}
                int id = resultSet.getInt("id");
                System.out.println(id);
                String name = resultSet.getString("naam");
                System.out.println(name);
                Course course = new Course(id, name, coordinatorId);
                courses.add(course);
                System.out.println(course);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return courses;
    }
}
