package Model;

import java.util.ArrayList;
import java.util.Scanner;

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

    public void ModificaDescrizione()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Inserisci la nuova descrizione");
        String NuovaDescrizione;

        NuovaDescrizione = in.nextLine();
        this.Descrizione = NuovaDescrizione;
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
        ListaToDo.add(NuovoToDo);
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
