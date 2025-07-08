package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CondividiToDoDAO {
    public int aggiungiCondivisione (String utenteMitt, String utenteDest, int idToDo) throws SQLException;

    public int rimuoviCondivisione (String utenteMitt, String utenteDest, int idToDo);

    public ArrayList<String> getListaCondivisioni(int idToDo);
}
