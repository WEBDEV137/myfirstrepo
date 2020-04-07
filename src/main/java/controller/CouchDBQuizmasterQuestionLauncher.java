package controller;

import com.google.gson.JsonObject;
import database.nosql.*;
import model.Question;

import java.util.List;

public class CouchDBQuizmasterQuestionLauncher {

    private QuestionCouchDBaccess db;
    private QuestionCouchDBDAO qdb;


    public CouchDBQuizmasterQuestionLauncher() {
        super();
        this.db = new QuestionCouchDBaccess();
        this.qdb = new QuestionCouchDBDAO(db);
    }

    public static void main(String[] args) {
        CouchDBQuizmasterQuestionLauncher myself = new CouchDBQuizmasterQuestionLauncher();
        myself.run();
    }

    public void run() {
//        Maak een connectie met CouchDB, gebruik het CouchDbaccess object.
        try {
            db.setupConnection();
            System.out.println("Connectie open.");
        }
        catch (Exception e) {
            System.out.println("\nEr is iets fout gegaan.\n");
        }
        //Sla een vraag op in de CouchDb database.
        Question question = new Question("Staat hij in de database?", "Ja", "Nee", "Misschien", "Ik hoop het");
        System.out.println(question);
        qdb.saveSingleQuestion(question);

        // Bring een gebruiker met id.
        Question questionByDocIdId = qdb.getQuestionByDocId("9200cb272b8c4bc5aa688ba87cfafdfc");
        System.out.println(questionByDocIdId);

        // Bring all gebruikers.
        List<JsonObject> allDocs = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject d : allDocs) {
            System.out.println(d.getAsJsonObject());
        }

    }
}
