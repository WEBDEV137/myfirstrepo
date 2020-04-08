package controller;

import com.google.gson.JsonObject;
import database.nosql.CourseCouchDBAcces;
import database.nosql.CourseCouchDBDAO;
import database.nosql.UserCouchDBDAO;
import model.Course;
import model.User;

import java.util.List;

public class CouchDBQuizmasterCourseLauncher {
    private CourseCouchDBAcces cDB;
    private CourseCouchDBDAO uDB;

    public CouchDBQuizmasterCourseLauncher() {
        super();
        this.cDB = new CourseCouchDBAcces();
        this.uDB = new CourseCouchDBDAO(cDB);
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

        Course courseId = uDB.getCourseByDocId("d4ca6644edc24891a1171c7d3df5b70e");
        System.out.println(courseId);
    }

}
