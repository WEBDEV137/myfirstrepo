package controller;

import com.google.gson.JsonObject;
import database.nosql.CouchDBaccess;
import database.nosql.UserCouchDBDAO;
import model.User;

import java.util.List;

public class CouchDBQuizmasterLauncher {
    private CouchDBaccess db;
    private UserCouchDBDAO udb;


    public CouchDBQuizmasterLauncher() {
        super();
        this.db = new CouchDBaccess();
        this.udb = new UserCouchDBDAO(db);
    }

    public static void main(String[] args) {
        CouchDBQuizmasterLauncher myself = new CouchDBQuizmasterLauncher();
        myself.run();
    }

    /** om connectin maken met couchDB, sla gebruiker in database, bring gebruiker bij id en bring all gebruikrs.
     * @run
     */
    public void run() {
//        Maak een connectie met CouchDB, gebruik het CouchDbaccess object.
        try {
            db.setupConnection();
            System.out.println("Connection open");
        }
        catch (Exception e) {
            System.out.println("\nEr is iets fout gegaan\n");
        }
        //Sla een gerbruiker in de CouchDb database.
        User a = new User(10,"Docent","minke1234","12345","Minke2",null,"Jansen");
        System.out.println(a);
        udb.saveSingleUser(a);

        // Bring een gebruiker met id.
        User userId = udb.getUserByDocId("b42142f6e261475ebd97b684477bda1e");
        System.out.println(userId);

        // Bring all gebruikers.
        List<JsonObject> allDocs = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject d : allDocs) {
            System.out.println(d.getAsJsonObject());
        }

    }
}


