package model;

import java.util.ArrayList;
import java.util.Scanner;

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

    public void ModificaDescrizione()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Inserisci la nuova descrizione");
        String NuovaDescrizione;

        NuovaDescrizione = in.nextLine();
        this.descrizione = NuovaDescrizione;
    }

    public void CreaToDo()
    {
        Scanner in = new Scanner(System.in);
        String Titolo;
        String Descrizione;
        String URL;
        String DataScadenza;
        int Immagine;
        String ColoreSfondo;

        System.out.print("Inserisci il titolo del nuovo ToDo: ");
        Titolo = in.nextLine();
        System.out.print("Inserisci la descrizione: ");
        Descrizione = in.nextLine();
        System.out.print("Inserisci un URL: ");
        URL = in.nextLine();
        System.out.print("Inserisci la data di scadenza (YYYY-MM-DD): ");
        DataScadenza = in.nextLine();
        System.out.print("Inserisci un immagine (per il momento degli int a caso): ");
        Immagine = in.nextInt();
        in.nextLine();
        System.out.print("Inserisci il colore sfondo: ");
        ColoreSfondo = in.nextLine();
        ToDO NuovoToDo = new ToDO(Titolo, Descrizione, URL, DataScadenza, Immagine, ColoreSfondo);
        listaToDo.add(NuovoToDo);
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
