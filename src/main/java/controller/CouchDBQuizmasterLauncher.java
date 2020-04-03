package controller;

import com.google.gson.JsonObject;
import database.nosql.CouchDBaccess;
import database.nosql.UserCouchDBDAO;
import model.Gebruiker;

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
       /* Gebruiker a = new Gebruiker("emr","emir",null,"kilic","Student","12345");
        System.out.println(a);
        udb.saveSingleUser(a);*/

        // Bring een gebruiker met id.
        /*Gebruiker gebruikerId = udb.getUserByDocId("4f6afdaa493646b5bb4f2b8719ea7ea3");
        System.out.println(gebruikerId);*/

        // Bring all gebruikers.
       /* List<JsonObject> allDocs = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject d : allDocs) {
            System.out.println(d.getAsJsonObject());
        }*/

//        udb.getUser("emr","Student");
    }
}
