package dao;

import model.Attivita;

import java.util.ArrayList;

public interface AttivitaDAO {
    public int creaAttivita (int idToDo, String nome);

    public ArrayList<Attivita> getAttivita (int idToDo);

    public int setStato (boolean stato, String nome, int idToDo);

    public int eliminaAttivita (String nome, int idToDo);
}
