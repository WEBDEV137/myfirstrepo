package database.nosql;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Group;

import java.util.List;

public class GroupCouchDBDAO {
    private GroupCouchDBaccess gdb;
    private Gson gson;

    public GroupCouchDBDAO(GroupCouchDBaccess gdb) {
        super();
        this.gdb = gdb;
        gson = new Gson();
    }

    public String saveSingleGroup(Group group) {
        String jsonstring = gson.toJson(group);
        System.out.println(jsonstring);
        JsonParser parser = new JsonParser();
        JsonObject jsonobject = parser.parse(jsonstring).getAsJsonObject();
        String doc_Id = gdb.saveDocument(jsonobject);
        return doc_Id;
    }

    public Group getGroupByDocId(String doc_Id) {
        JsonObject json = gdb.getClient().find(JsonObject.class, doc_Id);
        Group resultaat = gson.fromJson(json, Group.class);
        return resultaat;
    }

/*    public Group getGroupByName(String groepNaam, int docentcode) {
        Group resultaat = null;
        List<JsonObject> allGroups = gdb.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject json : allGroups) {
            resultaat = gson.fromJson(json, Group.class);
            if (resultaat.getGroupName().equals(groepNaam) && (resultaat.getUserId() == docentcode)) {
                return resultaat;
            }
        }
        return resultaat;
    }*/


}
