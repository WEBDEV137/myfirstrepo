package database.mysql;

import model.Group;
import model.Quiz;
import model.User;
import org.junit.jupiter.api.Test;
import view.Main;

import static org.junit.jupiter.api.Assertions.*;

class GroupDAOTest {

    @Test
    void getAll() {
    }

    @Test
    void getOneById() {
        DBAccess dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        GroupDAO groupDAO = new GroupDAO(dbAccess);
        int id = 3;
        String groupName = "Rood1";
        int userId = 9;
        int courseId = 4;
        Group group = new Group(id, groupName, userId, courseId);
        int expectedId = id;
        String expectedGroupName = groupName;
        int expectedUserId = userId;
        int expectedCourseId = courseId;
        Group actualGroup = groupDAO.getOneById(expectedId);
        int actualId = actualGroup.getGroupId();
        String actualGroupName = actualGroup.getGroupName();
        int actualUserId = actualGroup.getUserId();
        int actualCourseId = actualGroup.getCourseId();
        assertEquals(expectedId, actualId);
        assertEquals(expectedGroupName, actualGroupName);
        assertEquals(expectedUserId, actualUserId);
        assertEquals(expectedCourseId, actualCourseId);
        System.out.println(group);
        System.out.println(actualGroup);
    }


    @Test
    void storeOne() {
    }



}
