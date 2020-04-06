package model;


public class User implements Comparable<User> {

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

    public User (int userId, String userName){
        this(userId, "onbekend", userName, "onbekend", "onbekend", "onbekend", "onbekend");
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
//        resultString.append(userId + " ");
        resultString.append(name + " ");
        if (prefix != null) {
            resultString.append(prefix + " ");
        }
        resultString.append(surname + " ");
        resultString.append(rolName + " ");
        return resultString.toString();
    }

    @Override
    public int compareTo(User o) {
        return name.compareTo(o.getName());
    }
}
