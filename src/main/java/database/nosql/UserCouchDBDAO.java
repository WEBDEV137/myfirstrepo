package database.nosql;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.User;

import java.util.List;

public class UserCouchDBDAO {
    private CouchDBaccess db;
    private Gson gson;

    public UserCouchDBDAO(CouchDBaccess db) {
        super();
        this.db = db;
        gson = new Gson();
    }

    public String saveSingleUser(User user) {
        String jsonstring = gson.toJson(user);
        System.out.println(jsonstring);
        JsonParser parser = new JsonParser();
        JsonObject jsonobject = parser.parse(jsonstring).getAsJsonObject();
        String doc_Id = db.saveDocument(jsonobject);
        return doc_Id;
    }

    public User getUserByDocId(String doc_Id) {
        JsonObject json = db.getClient().find(JsonObject.class, doc_Id);
        User resultaat = gson.fromJson(json, User.class);
        return resultaat;
    }

    public User getUser(String userNaam, String rol) { User resultaat = null;
        List<JsonObject> alleUsers = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject json : alleUsers) {
            resultaat = gson.fromJson(json, User.class);
            if (resultaat.getName().equals(userNaam)  && (resultaat.getRolName() == rol)) {
                return resultaat;
            }
        }
        return resultaat;
    }
}
