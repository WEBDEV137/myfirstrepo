package controller;

import com.google.gson.JsonObject;
import database.nosql.GroupCouchDBDAO;
import database.nosql.GroupCouchDBaccess;
import model.Group;

import java.util.List;

public class GroupCouchDBLauncher {
    private GroupCouchDBaccess dbGroupaccess;
    private GroupCouchDBDAO gdb;


    public GroupCouchDBLauncher() {
        super();
        this.dbGroupaccess = new GroupCouchDBaccess();
        this.gdb = new GroupCouchDBDAO(dbGroupaccess);
    }

    public static void main(String[] args) {
        GroupCouchDBLauncher myself = new GroupCouchDBLauncher ();
        myself.run();
    }

    // om connectin maken met couchDB, sla gebruiker in database, bring gebruiker bij id en bring all gebruikrs.
    // @run

    public void run() {
//        Maak een connectie met CouchDB, gebruik het CouchDbaccess object.
        try {
            dbGroupaccess.setupConnection();
            System.out.println("Connection open");
        } catch (Exception e) {
            System.out.println("\nEr is iets fout gegaan\n");
        }

        // sla een groep op in de CouchDB-database
/*        Group Mauve4 = new Group("Mauve4", 3, 5);
        System.out.println(Mauve4);
        gdb.saveSingleGroup(Mauve4);*/

/*        // haal een groep op met JSON-docId
        Group group = gdb.getGroupByDocId("817087e66a4e486cb0b4afcbc941a4be");
        System.out.println(group);*/

        // hier nog een andere query/methode toevoegen voor group*/
/*        Group Mauve4 = gdb.getGroupByName("Mauve4");
        System.out.println(Mauve4);*/

        // alle groepen ophalen
/*        List<JsonObject> allDocs = dbGroupaccess.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject d : allDocs) {
            System.out.println(d.getAsJsonObject());
        }*/

    }
}
