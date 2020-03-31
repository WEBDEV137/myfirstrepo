package model;

public class TechnischBeheerder  extends User{
    //CONSTRUCTOR
    public TechnischBeheerder(String inlognaam, String wachtwoord, String rol) {
        super(inlognaam, wachtwoord, rol);
        getAllTasks().add(MANAGE_USERS);
        getAllTasks().add(CREATE_UPDATE_USERS);

    }
}
