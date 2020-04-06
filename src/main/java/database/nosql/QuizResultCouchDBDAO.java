package database.nosql;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Gebruiker;
import model.QuizResult;
import java.util.List;

public class QuizResultCouchDBDAO {
    private CouchDBaccess db;
    private Gson gson;

    public QuizResultCouchDBDAO(CouchDBaccess db) {
        super();
        this.db = db;
        gson = new Gson();
    }

    public String saveSingelQuizResult(QuizResult quizResult) {
        String jsonstring = gson.toJson(quizResult);
        System.out.println(jsonstring);
        JsonParser parser = new JsonParser();
        JsonObject jsonobject = parser.parse(jsonstring).getAsJsonObject();
        String doc_Id = db.saveDocument(jsonobject);
        return doc_Id;
    }

    public QuizResult getQuizResultByDocid(String doc_Id) {
        JsonObject json = db.getClient().find(JsonObject.class, doc_Id);
        QuizResult quizResult = gson.fromJson(json, QuizResult.class);
        return quizResult;
    }

/*    public QuizResult getQuizresult(int userId, ) {
        QuizResult quizResult = null;
        List<JsonObject> allQuizResults = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject json : allQuizResults) {
            quizResult = gson.fromJson(json, QuizResult.class);
            if (quizResult.getusername().equals(gebruikerNaam) && (allQuizResults. == rol)) {
                return allQuizResults;
            }
        }
        return allQuizResults;
    }*/
}
