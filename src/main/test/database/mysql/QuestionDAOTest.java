package database.mysql;

import model.Question;
import model.User;
import org.junit.jupiter.api.Test;
import view.Main;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class QuestionDAOTest {

    @Test
    void getAll() {
        DBAccess dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        QuestionDAO questionDAO = new QuestionDAO(dbAccess);
        ArrayList<Question> questions = questionDAO.getAll();
        for (Question question : questions) {
            assertNotNull(question);
        }
    }

    @Test
    void getOneById() {
        DBAccess dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        QuestionDAO questionDAO = new QuestionDAO(dbAccess);
        assertNotNull(questionDAO.getOneById(2));
    }

    @Test
    void storeOne() {
    }
}