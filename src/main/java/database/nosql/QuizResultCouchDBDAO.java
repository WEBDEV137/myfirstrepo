package database.nosql;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.QuizResult;

import java.util.Date;
import java.util.List;


public class QuizResultCouchDBDAO {
    private QuizResultCouchDBaccess db;
    private Gson gson;

    public QuizResultCouchDBDAO(QuizResultCouchDBaccess db) {
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

    public QuizResult getQuizresult(int userId, int quizid,  Date date) {
        QuizResult quizResult = null;
        List<JsonObject> allQuizResults = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject json : allQuizResults) {
            quizResult = gson.fromJson(json, QuizResult.class);
            if (quizResult.getUserid() == userId && quizResult.getQuizId() == quizid && quizResult.getDate() == date) {
                return quizResult;
            }
        }
        return quizResult;
    }
}
