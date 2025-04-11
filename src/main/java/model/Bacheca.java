package Model;

import java.util.ArrayList;

public class Bacheca {
    public TitoloBacheca Titolo;
    public String Descrizione;
    ArrayList<ToDO> ListaToDo;

    Bacheca(TitoloBacheca Titolo, String Descrizione)
    {
        this.Titolo = Titolo;
        this.Descrizione = Descrizione;
        this.ListaToDo = new ArrayList<>();
    }

    public void aggiungiToDo(ToDO nuovoToDo)
    {
        ListaToDo.add(nuovoToDo);
    }

    public void mostraTutti()
    {
        for (ToDO t : ListaToDo) {
            System.out.println(t);
        }
    }

    @Override
    public String toString()
    {
        return "Il titolo della bacheca è: " + Titolo + "\n" +
                "Descrizione: " + Descrizione + "\n";
    }
}
