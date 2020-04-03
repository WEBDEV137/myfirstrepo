package model;


public class User {
    //CONSTANTEN
    protected final static String QUIZ_BEHEREN = "Manage Quiz";
    protected final static String QUIZ_AANMAKEN_WIJZIGEN = "Create/ update quiz";
    protected final static String VOORTGANG_BEKIJKEN = "Check results";
    protected final static String QUIZ_DASHBOARD = "Quiz Dashboard";
    protected final static String VOORTGANG_DASHBOARD = "results Dashboard";
    protected final static String COURSE_SIGN_IN_OUT ="Sign in / out Courses";
    protected final static String FILL_OUT_QUIZ ="Fill out quiz";
    protected final static String MANAGE_USERS ="Manage users";
    protected final static String CREATE_UPDATE_USERS ="Create / update users";
    protected final static String MANAGE_GROUPS ="Manage users";
    protected final static String CREATE_UPDATE_GROUPS ="Create / update users";
    protected final static String MANAGE_COURSES ="Manage courses";
    protected final static String CREATE_UPDATE_COURSES ="Create / update courses";

    //VARIABELEN
    private int userId;
    private String rolName;
    private String userName;
    private String password;
    private String name;
    private String prefix;
    private String surname;

    //CONSTRUCTOR
    public User(int userId, String rolName, String userName, String password, String name, String prefix, String surname) {
        this.userId = userId;
        this.rolName = rolName;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.prefix = prefix;
        this.surname = surname;
    }

    public User(String rolName, String userName, String password, String name, String prefix, String surname) {
        this.rolName = rolName;
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.prefix = prefix;
        this.surname = surname;
    }

    //GETTERS
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        if (prefix != null) {
            return prefix;
        } else {
            return "";
        }

    }

    public void setPrefix(String prefix) {
            this.prefix = prefix;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getRolName() {
        return rolName;
    }

    public void setRolName(String rolName) {
        this.rolName = rolName;
    }

    //METHODEN
    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder("");
        resultString.append(userId + " ");
        resultString.append(name + " ");
        if (prefix != null) {
            resultString.append(prefix + " ");
        }
        resultString.append(surname + " => ");
        resultString.append(rolName + " ");
        return resultString.toString();
    }
}
