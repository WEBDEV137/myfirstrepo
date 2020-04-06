package database.mysql;

import model.Course;
import view.Main;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class CourseDAOTest {



//    }
//    @org.junit.jupiter.api.Test
//    void getOneById() {
//        DBAccess dbAccess = Main.getDBaccess();
//        dbAccess.openConnection();
//        QuizDAO quizDAO = new QuizDAO(dbAccess);
//        int id = 25;
//        String name = "TestNaam";
//        int succesDefinition = 100;
//        int courseId = 5;
//        Quiz quiz = new Quiz(id, name, succesDefinition, courseId);
//        String expectedName = name;
//        int expectedSuccesDefinition = succesDefinition;
//        int expectedCourseId = courseId;
//        int returnedId = quizDAO.storeOneAndReturnId(quiz);
//        Quiz actualQuiz = quizDAO.getOneById(returnedId);
//        String actualName = actualQuiz.getName();
//        int actualSuccesDefinition = actualQuiz.getSuccesDefinition();
//        int actualCourseId = actualQuiz.getCourseId();
//        assertEquals(expectedName,actualName);
//        assertEquals(expectedSuccesDefinition,actualSuccesDefinition);
//        assertEquals(expectedCourseId,actualCourseId);
//    }
//
//    @org.junit.jupiter.api.Test
//    void storeOne() {
//        DBAccess dbAccess = Main.getDBaccess();
//        dbAccess.openConnection();
//        QuizDAO quizDAO = new QuizDAO(dbAccess);
//        int id = 2;
//        String name = "QuizmasterTest";
//        int succesDefinition = 10;
//        int courseId = 2;
//        Quiz quiz = new Quiz(id, name, succesDefinition, courseId);
//        String expectedName = name;
//        int expectedSuccesDefinition = succesDefinition;
//        int expectedCourseId = courseId;
//        int returnedId = quizDAO.storeOneAndReturnId(quiz);
//        Quiz actualQuiz = quizDAO.getOneById(returnedId);
//        String actualName = actualQuiz.getName();
//        int actualSuccesDefinition = actualQuiz.getSuccesDefinition();
//        int actualCourseId = actualQuiz.getCourseId();
//        assertEquals(expectedName,actualName);
//        assertEquals(expectedSuccesDefinition,actualSuccesDefinition);
//        assertEquals(expectedCourseId,actualCourseId);
//    }
}