//Maken CourseDAO om gegevens vanuit database te halen
package database.mysql;


import controller.Coll;
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

public class CourseDAO extends AbstractDAO implements GenericDAO {
    public CourseDAO(final DBAccess dbAccess){
        super(dbAccess);
    }

    @Override
    public  ArrayList <Course> getAll() {
        final ArrayList<Course> courseSheet = new ArrayList<>();
        final String query = "SELECT * FROM cursus";
        Course course;
        try {
            final PreparedStatement preparedStatement = this.getStatement(query);
            final ResultSet resultSet = this.executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                final int id = resultSet.getInt("id");
                final String coursename = resultSet.getString("naam");
                final int coordinatorid = resultSet.getInt("coordinatorid");
                course = new Course(id, coursename, coordinatorid);
                courseSheet.add (course);
            }
        }
        catch (final SQLException e) {
            System.out.println(" SQL error " + e.getMessage());
        }
        return courseSheet;
    }



    public Course getOneById (final int id) {
        final String query = "SELECT * FROM cursus WHERE id = ?;";
        Course course = null;
        try {
            final PreparedStatement preparedStatement = this.getStatement(query);
            preparedStatement.setInt(1,id);
            final ResultSet resultSet = this.executeSelectPreparedStatement(preparedStatement);
            if (resultSet.next()) {
                final String name = resultSet.getString("naam");
                final int coordinatorId = resultSet.getInt("coordinatorid");
                course = new Course(id, name, coordinatorId);
            }
        } catch (final SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return course;
    }

    @Override
    public void storeOne(Object type) {

    }


    public void storeCourse(final Course course) {
        final String sql = "Insert into cursus values(DEFAULT,?,?) ;";
        try {
            System.out.println(course.getCoursename()+"1");
            final PreparedStatement ps = this.getStatement(sql);
            ps.setString(1, course.getCoursename());
            ps.setInt(2, course.getCoordinatorid());
            this.executeManipulatePreparedStatement(ps);
//            int key = executeInsertPreparedStatement(ps);
//            course.setId(key);
        } catch (final SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    /**
     * Sla een quiz op in de database en geeft de automatisch gegenereerde quiz-id terug als int.
     *
     * @param course
     *            De quiz die opgeslagen moet worden
     */
    public int storeOneAndReturnId(Course course){
        String query = "INSERT INTO cursus VALUES (DEFAULT, ?, ?);";
        try {
            PreparedStatement preparedStatement = getStatementWithKey(query);
            preparedStatement.setString(1, course.getCoursename());
            preparedStatement.setInt(2, course.getCoordinatorid());

            int courseId = executeInsertPreparedStatement(preparedStatement);
            return courseId;
        }
        catch (SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return 0;
    }

    public void updateCourse(final Course course) {
        final String sql = "Update cursus Set naam = ?, coordinatorid = ? where id = ?;";
        try {
            final PreparedStatement ps = this.getStatement(sql);
            ps.setString(1, course.getCoursename());
            ps.setInt(2, course.getCoordinatorid());
            ps.setInt(3, course.getId());
            this.executeManipulatePreparedStatement(ps);
        } catch (final SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
    }

    public ArrayList<Course> getAllByCoordinatorId(int coordinatorId) {
        ArrayList<Course> courses = null;
        final String query = "SELECT * FROM cursus WHERE coordinatorid = ? ;";
        try {
            final PreparedStatement preparedStatement = this.getStatement(query);
            preparedStatement.setInt(1, coordinatorId);
            final ResultSet resultSet = this.executeSelectPreparedStatement(preparedStatement);
            while (resultSet.next()) {
                if(courses == null){ courses = new ArrayList<>(); }
                final int id = resultSet.getInt("id");
                final String name = resultSet.getString("naam");
                final Course course = new Course(id, name, coordinatorId);
                courses.add(course);

            }
        } catch (final SQLException e) {
            System.out.println("SQL error " + e.getMessage());
        }
        return courses;
    }


    public void deleteCourse(final Course course){
        System.out.println("verwijder cursus");
        final String sql = "DELETE FROM cursus WHERE id = ?;";
        try {
            final PreparedStatement ps = this.getStatement(sql);
            ps.setInt(1, course.getId());
            ps.executeUpdate();
        } catch (final SQLException e){
            System.out.println("SQL Error "+e.getMessage());
        }

    }
    public int getCourseIdByName(final String courseName){
        int courseId = 0;
        final String sql = "SELECT * FROM cursus WHERE naam = ?;";
        try {
            final PreparedStatement preparedStatement = this.getStatement(sql);
            preparedStatement.setString(1, courseName);
            final ResultSet rs = this.executeSelectPreparedStatement(preparedStatement);
            if (rs.next()) {
                courseId = rs.getInt("id");
            }
        } catch (final SQLException e) {
            System.out.println("SQL error " + e.getMessage());
            Coll.showAlert("x", "x", "x");
        }
        return courseId;
    }

    public String getCourseNameById(final int courseId){
        String courseName = "";
        final String sql = "SELECT * FROM cursus WHERE id = ?;";
        try {
            final PreparedStatement preparedStatement = this.getStatement(sql);
            preparedStatement.setInt(1, courseId);
            final ResultSet rs = this.executeSelectPreparedStatement(preparedStatement);
            if (rs.next()) {
                courseName = rs.getString("naam");
            }
        } catch (final SQLException e) {
            System.out.println("SQL error " + e.getMessage());
            Coll.showAlert("x", "x", "x");
        }
        return courseName;
    }
    /**
     * om get aal inscrijving courses
     * @return
     */
    public  ArrayList <Course> getAllInscrijvingCourses(int studentid) {
        String query = "SELECT c.id ,c.naam  \n" +
                "FROM cursus c JOIN inschrijving i\n" +
                "ON c.id = i.cursusid where studentid = ?;";
        ArrayList<Course> inschrijvingList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getStatement(query);
            preparedStatement.setInt(1,studentid);
            ResultSet resultSet = executeSelectPreparedStatement(preparedStatement);
            Course course;
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String naam = resultSet.getString("naam");
                course = new Course(id,naam, studentid);
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

    /**
     * om uitscrijven van course
     * @param course
     */
    public void deleteInshrijvingCourse(Course course) {
        String sql = "DELETE FROM inschrijving WHERE cursusid = ?;";
        try {
            PreparedStatement ps = getStatement(sql);
            ps.setInt(1, course.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQL Error " + e.getMessage());
        }

    }
}
