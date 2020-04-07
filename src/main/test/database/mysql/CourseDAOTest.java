package database.mysql;

import model.Course;
import view.Main;

import static org.junit.jupiter.api.Assertions.*;

class CourseDAOTest {


    @org.junit.jupiter.api.Test
    void getOneById() {
        DBAccess dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        CourseDAO courseDAO = new CourseDAO(dbAccess);
        int id = 5;
        String cursusnaam = "Natuurkunde";
        int coordinatorid = 2;
        Course course = new Course(id, cursusnaam, coordinatorid);
        int idVerwacht = id;
        String courseVerwacht = cursusnaam;
        int coordinatoridVerwacht = coordinatorid;
        int returnedId = courseDAO.storeOneAndReturnId(course);
        Course actualcourse = courseDAO.getOneById(returnedId);

        String actualCoursename = actualcourse.getCoursename();
        int actualCoordId = actualcourse.getCoordinatorid();
        assertEquals (courseVerwacht, actualCoursename);
        assertEquals(coordinatoridVerwacht, actualCoordId);
    }



}