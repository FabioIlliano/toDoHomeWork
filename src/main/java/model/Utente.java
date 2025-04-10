package model;

public class Utente {
    private String username;
    private String password;

    public Utente (String username, String password){
        this.username = username;
        this.password = password;
    }

    public boolean login (String username, String password){
        if (this.username.equals(username)&&this.password.equals(password))
            return true;
        return false;
    }
}
