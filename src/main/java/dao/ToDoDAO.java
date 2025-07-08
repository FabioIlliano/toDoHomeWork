package dao;

import model.ToDo;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public interface ToDoDAO {
    public ArrayList<ToDo> caricaDatiToDo(String bacheca, String username);

    public ArrayList<ToDo> caricaDatiToDoCondivisi(String bacheca, String username);

    public int creaToDo (String titolo, String bacheca, String username);

    public void aggiornaToDo (ToDo t) throws SQLException;

    public void eliminaToDo (int idToDo);

    public int spostaToDo (int idToDo, String bacheca, String username);

}
