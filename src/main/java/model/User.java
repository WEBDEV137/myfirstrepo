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

    public User(int userId, String userName) {
        this(userId, "onbekend", userName, "onbekend", "onbekend", "onbekend", "onbekend");
    }

    //SETTERS
    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPassword() {
        return password;
    }

    public String getRolName() {
        return rolName;
    }

    //GETTERS
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPrefix() {
        return prefix;
    }

    //METHODEN
    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder("");
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
