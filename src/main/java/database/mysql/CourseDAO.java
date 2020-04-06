//Maken CourseDAO om gegevens vanuit database te halen
package database.mysql;

import controller.AbstractController;
import model.Course;
import model.Group;
import model.Quiz;
import model.User;
import view.Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CourseDAO extends AbstractDAO {
    private User user;
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
                UserDAO userDAO = new UserDAO(dBaccess);
                User userCoordinator = userDAO.getOneById(coordinatorId);
                course = new Course(id, name, coordinatorId);
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return course;
    }

    /**
     * Wegschrijven van nieuwe course naar SQL TO DO: Bewaren lukt nog niet
     */

    public void storeCourse(Course course) {
        String sql = "Insert into cursus values(DEFAULT,?,?) ;";
        try {
            System.out.println(course.getCoursename()+"1");
            PreparedStatement ps = getStatement(sql);
            ps.setString(1, course.getCoursename());
            ps.setInt(2, course.getCoordinatorid());
            executeManipulatePreparedStatement(ps);


//            int key = executeInsertPreparedStatement(ps);
//            course.setId(key);
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
                if(courses == null){ courses = new ArrayList<>(); }
                int id = resultSet.getInt("id");
                String name = resultSet.getString("naam");
                Course course = new Course(id, name, coordinatorId);
                courses.add(course);

            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return courses;
    }

    public void deleteCourse(Course course){
        System.out.println("verwijder cursus");
        String sql = "DELETE FROM cursus WHERE id = ?;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, course.getId());
            ps.executeUpdate();
        } catch (SQLException e){
            System.out.println("SQL Error "+e.getMessage());
        }

    }
    public int getCourseIdByName(String courseName){
        int courseId = 0;
        String sql = "SELECT * FROM cursus WHERE naam = ?;";
        try {
            PreparedStatement preparedStatement = getStatement(sql);
            preparedStatement.setString(1, courseName);
            ResultSet rs = executeSelectPreparedStatement(preparedStatement);
            if (rs.next()) {
                courseId = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
            AbstractController.showAlert("x", "x", "x");
        }
        return courseId;
    }

    /**
     * om get aal inscrijving courses
     * @return
     */
    public  ArrayList <Course> getAllInscrijvingCourses(int id) {

        String query = "SELECT * FROM quizmaster.inschrijving where studentid = ?;";
        ArrayList<Course> inschrijvingList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            Course course;
            while (resultSet.next()) {
                int coursusid = resultSet.getInt("cursusid");
                course = new Course(id,coursusid);
                inschrijvingList.add (course);
            }
        }
        catch (SQLException e) {
            System.out.println(" SQL error " + e.getMessage());
        }
        return inschrijvingList;
    }

    /**
     * om inschrijving course op te slaan
     * @param course
     */
    public void storeInscrijving(Course course) {
        String sql = "Insert into inschrijving values(?, ?) ;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, Main.getCurrentUser().getUserId());
            ps.setInt(2, course.getId());
            executeManipulatePreparedStatement(ps);
        } catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    public void deleteInshrijvingCourse(Course course) {
        String sql = "DELETE FROM inschrijving WHERE id = ?;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, course.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error " + e.getMessage());
        }

    }
}
