package controller;

import com.google.gson.JsonObject;
import database.nosql.CouchDBaccess;
import database.nosql.CourseCouchDBDAO;
import database.nosql.UserCouchDBDAO;
import model.Course;
import model.User;

import java.util.List;

public class CouchDBQuizmasterCourseLauncher {
    private CouchDBaccess cDB;
    private CourseCouchDBDAO uDB;

    public CouchDBQuizmasterCourseLauncher() {
        super();
        cDB = new CouchDBaccess();
        uDB = new CourseCouchDBDAO(cDB);
    }


    public static void main(String[] args) {
        CouchDBQuizmasterCourseLauncher myself = new CouchDBQuizmasterCourseLauncher();
        myself.run();
    }

    /**
     * Connectie maken met couchDB.
     *
     * @run
     */
    public void run() {
        try {
            cDB.setupConnection();
            System.out.println("Connection open");
        } catch (Exception e) {
            System.out.println("\nEr is iets fout gegaan\n");
        }
        //Sla een cursus op in de CouchDb database.
        Course courseCouch = new Course(100, "Cursus CouchDB", 15);
        System.out.println(courseCouch);
        uDB.saveSingleCourse(courseCouch);

//Haal een cursus op uit Couch DB

        Course courseId = uDB.getCourseByDocId("842addd1eab945b3828b35da56591e48");
        System.out.println(courseId);

//        // Bring all gebruikers.
//        List<JsonObject> allDocs = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
//        for (JsonObject d : allDocs) {
//            System.out.println(d.getAsJsonObject());
//        }
//
    }

 }
