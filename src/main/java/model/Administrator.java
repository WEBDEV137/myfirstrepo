package model;

public class Administrator extends User{
    //CONSTRUCTOR
    public Administrator(String inlognaam, String wachtwoord, String rol) {
        super(inlognaam, wachtwoord, rol);
        getAllTasks().add(MANAGE_COURSES);
        getAllTasks().add(CREATE_UPDATE_COURSES);
        getAllTasks().add(MANAGE_GROUPS);
        getAllTasks().add(CREATE_UPDATE_GROUPS);

    }
}
