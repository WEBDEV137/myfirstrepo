package database.nosql;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Course;

import java.util.List;

public class CourseCouchDBDAO {
    private CourseCouchDBAcces db;
    private Gson gson;

    public CourseCouchDBDAO(CourseCouchDBAcces db) {
        super();
        this.db = db;
        gson = new Gson();
    }

    public String saveSingleCourse(Course course) {
        String jsonstring = gson.toJson(course);
        System.out.println(jsonstring);
        JsonParser parser = new JsonParser();
        JsonObject jsonobject = parser.parse(jsonstring).getAsJsonObject();
        String doc_Id = db.saveDocument(jsonobject);
        return doc_Id;
    }

    public Course getCourseByDocId(String doc_Id) {
        JsonObject json = db.getClient().find(JsonObject.class, doc_Id);
        Course resultaat = gson.fromJson(json, Course.class);
        return resultaat;
    }

    public Course getCourse(int id, String cursusnaam) {
        Course resultaat = null;
        List<JsonObject> allCourses = db.getClient().view("_all_docs").includeDocs(true).query(JsonObject.class);
        for (JsonObject json : allCourses) {
            resultaat = gson.fromJson(json, Course.class);
            if (resultaat.getId() == id  && (resultaat.getCoursename().equals(cursusnaam))) {
                return resultaat;
            }
        }
        return resultaat;
    }


}
