package model;

import java.util.ArrayList;
import java.util.Scanner;

public class Bacheca {
    private TitoloBacheca titolo;
    private String descrizione;
    private ArrayList<ToDo> listaToDo;

    public Bacheca(TitoloBacheca titolo, String descrizione)
    {
        this.titolo = titolo;
        this.descrizione = descrizione;
        listaToDo = new ArrayList<ToDo>();
    }

    public void modificaDescrizione(String titolo)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Inserisci la nuova descrizione");
        String NuovaDescrizione;

        NuovaDescrizione = in.nextLine();
        this.descrizione = NuovaDescrizione;
    }

    public void creaToDo()
    {
        Scanner in = new Scanner(System.in);
        String titolo;
        String descrizione;
        String url;
        String dataScadenza;
        int immagine;
        String coloreSfondo;

        System.out.print("Inserisci il titolo del nuovo ToDo: ");
        titolo = in.nextLine();
        System.out.print("Inserisci la descrizione: ");
        descrizione = in.nextLine();
        System.out.print("Inserisci un URL: ");
        url = in.nextLine();
        System.out.print("Inserisci la data di scadenza (YYYY-MM-DD): ");
        dataScadenza = in.nextLine();
        System.out.print("Inserisci un immagine (per il momento degli int a caso): ");
        immagine = in.nextInt();
        in.nextLine();
        System.out.print("Inserisci il colore sfondo: ");
        coloreSfondo = in.nextLine();
        ToDo nuovoToDo = new ToDo(titolo, descrizione, url, dataScadenza, immagine, coloreSfondo);
        listaToDo.add(nuovoToDo);
    }

    public void mostraTutti()
    {
        for (ToDo t : listaToDo) {
            System.out.println(t);
        }
    }

    public ToDo getToDo(String titolo){
        for(ToDo todo : listaToDo)
        {
            if(todo.getTitolo().equalsIgnoreCase(titolo))
            {
                return todo;
            }
        }
        return null;
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
