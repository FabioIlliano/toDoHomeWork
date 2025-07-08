package dao;

import model.Bacheca;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface UtenteDAO {
    public int inserisciutenteDB (String username, String password);

    public int loginUtenteDB (String username, String password);

    public ArrayList<Bacheca> getBachecheUtenteDB (String username);

    public int getUtente (String username);
}
