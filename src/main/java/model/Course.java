// Class course aangemaakt Minke Pronk
package model;

public class Course implements Comparable<Course> {

    //variabelen
    private int id;
    private String coursename;
    private int coordinatorid;
    private User user;

    public Course(int id, String coursename, int coordinatorid) {
        this.id = id;
        this.coursename = coursename;
        this.coordinatorid = coordinatorid;
    }

    public Course(String coursename, int coordinatorid) {
        this.coursename = coursename;
        this.coordinatorid = coordinatorid;
    }

    public Course(int id, String coursename) {
        this.id = id;
        this.coursename = coursename;
    }

    public Course() {
        this(Const.ONBEKEND_INT, Const.ONBEKEND_STRING, Const.ONBEKEND_INT);
    }

    public Course(int coordinatorid) {
        this.coordinatorid = coordinatorid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCoursename() {
        return this.coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public int getCoordinatorid() {
        return this.coordinatorid;
    }


    public String toString() {
        return String.format("%s", this.coursename);
    }

    @Override
    public int compareTo(Course o) {
        return coursename.compareTo(o.getCoursename());
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == (course.id) && coordinatorid == (course.coordinatorid) && coursename == (course.coursename);
    }
}


