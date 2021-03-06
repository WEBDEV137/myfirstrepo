package database.mysql;

import model.Quiz;
import model.User;
import org.junit.jupiter.api.Test;
import view.Main;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOTest {

    @Test
    void getAll() {
        DBAccess dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        UserDAO userDAO = new UserDAO(dbAccess);
        ArrayList<User> users =userDAO.getAll();
        for (User user : users) {
            assertNotNull(user);
        }
    }

    @Test
    void getOneById() {
        DBAccess dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        UserDAO userDAO = new UserDAO(dbAccess);
        int id = 1;
        String rol = "Student";
        String userNaam = "Hans";
        String wachtwoord = "12345";
        String naam = "Hans";
        String tussenvoegsels = "";
        String achternaam = "Janssen";
        User testUser = new User(id,rol,userNaam,wachtwoord,naam,tussenvoegsels,achternaam);
        int expectedId = id;
        String expectedRol = rol;
        String expecteduserNaam = userNaam ;
        String expectedWachtwoord = wachtwoord;
        String expectedNaam = naam;
        String expectedTussenvoegsels = tussenvoegsels;
        String expectedAchternaam = achternaam;
        User actuelUser = (User) userDAO.getOneById(expectedId);
        int actuelId = actuelUser.getUserId();
        String actuelUserNaam = actuelUser.getUserName();
        String actuelRol = actuelUser.getRolName();
        String actuelWachtwoord = actuelUser.getPassword();
        String actuelNaam = actuelUser.getName();
        String actuelTussenvoegsels = actuelUser.getPrefix();
        String actuelAchternaam = actuelUser.getSurname();
        assertEquals(expectedId,actuelId);
        assertEquals(expectedRol,actuelRol);
        assertEquals(expectedWachtwoord,actuelWachtwoord);
        assertEquals(expectedNaam,actuelNaam);
        assertEquals(expecteduserNaam,actuelUserNaam);
        assertEquals(expectedAchternaam,actuelAchternaam);
        System.out.println(testUser);
        System.out.println(actuelUser);
    }

    @Test
    void storeOne() {
        DBAccess dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        UserDAO userDAO = new UserDAO(dbAccess);
        int id = 1;
        String rol = "Student";
        String userNaam = "Hans";
        String wachtwoord = "12345";
        String naam = "Hans";
        String tussenvoegsels = "";
        String achternaam = "Janssen";
        User testUser = new User(id,rol,userNaam,wachtwoord,naam,tussenvoegsels,achternaam);
        int expectedId = id;
        String expectedRol = rol;
        String expecteduserNaam = userNaam ;
        String expectedWachtwoord = wachtwoord;
        String expectedNaam = naam;
        String expectedTussenvoegsels = tussenvoegsels;
        String expectedAchternaam = achternaam;
        User actuelUser = (User) userDAO.getOneById(expectedId);
        int actuelId = actuelUser.getUserId();
        String actuelUserNaam = actuelUser.getUserName();
        String actuelRol = actuelUser.getRolName();
        String actuelWachtwoord = actuelUser.getPassword();
        String actuelNaam = actuelUser.getName();
        String actuelTussenvoegsels = actuelUser.getPrefix();
        String actuelAchternaam = actuelUser.getSurname();
        assertEquals(expectedId,actuelId);
        assertEquals(expectedRol,actuelRol);
        assertEquals(expectedWachtwoord,actuelWachtwoord);
        assertEquals(expectedNaam,actuelNaam);
        assertEquals(expecteduserNaam,actuelUserNaam);
        assertEquals(expectedAchternaam,actuelAchternaam);
    }
    @Test
    void getUserByInlognaam() {
      DBAccess dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        UserDAO userDAO = new UserDAO(dbAccess);
        assertNotNull(userDAO.getUserByInlognaam("Dorien"));
    }

    @Test
    void getUsersByRole() {
        DBAccess dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        UserDAO userDAO = new UserDAO(dbAccess);
        assertNotNull(userDAO.getUsersByRole("Student"));
    }
    @Test
    void getUserIdByLoginName() {
        DBAccess dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        UserDAO userDAO = new UserDAO(dbAccess);
        assertNotNull(userDAO.getUserIdByLoginName("Dorien"));
    }
    @Test
    void getUserNameById() {
        DBAccess dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        UserDAO userDAO = new UserDAO(dbAccess);
        assertNotNull(userDAO.getUserNameById(5));
    }

}