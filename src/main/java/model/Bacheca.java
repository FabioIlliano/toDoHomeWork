package model;

import java.util.ArrayList;

public class Bacheca {
    private TitoloBacheca titolo;
    private String descrizione;
    private ArrayList<ToDO> listaToDo;

    public Bacheca(TitoloBacheca titolo, String descrizione)
    {
        this.titolo = titolo;
        this.descrizione = descrizione;
        listaToDo = new ArrayList<ToDO>();
    }

    public void aggiungiToDo(ToDO nuovoToDo)
    {
        listaToDo.add(nuovoToDo);
    }

    public void mostraTutti()
    {
        for (ToDO t : listaToDo) {
            System.out.println(t);
        }
    }

    public TitoloBacheca getTitolo(){
        return titolo;
    }

    public String getDescrizione(){
        return descrizione;
    }

    @Override
    public String toString()
    {
        return "Il titolo della bacheca è: " + titolo + "\n" +
                "Descrizione: " + descrizione + "\n";
    }
}
