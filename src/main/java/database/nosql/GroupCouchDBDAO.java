package database.nosql;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Group;

import java.util.List;

public class GroupCouchDBDAO {
    private CouchDBaccess db;
    private Gson gson;

    public GroupCouchDBDAO(CouchDBaccess db) {
        super();
        this.db = db;
        gson = new Gson();
    }

    public String saveSingleGroup(Group group) {
        String jsonstring = gson.toJson(group);
        System.out.println(jsonstring);
        JsonParser parser = new JsonParser();
        JsonObject jsonobject = parser.parse(jsonstring).getAsJsonObject();
        String doc_Id = db.saveDocument(jsonobject);
        return doc_Id;
    }

    public Group getGroupByDocId(String doc_Id) {
        JsonObject json = db.getClient().find(JsonObject.class, doc_Id);
        Group resultaat = gson.fromJson(json, Group.class);
        return resultaat;
    }

    public Group getGroup(int groepId, String groepNaam) {
        Group resultaat = null;
        List<JsonObject> allGroups = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject json : allGroups) {
            resultaat = gson.fromJson(json, Group.class);
            if (resultaat.getGroupId() == groepId  && (resultaat.getGroupName() == groepNaam)) {
                return resultaat;
            }
        }
        return resultaat;
    }

}
