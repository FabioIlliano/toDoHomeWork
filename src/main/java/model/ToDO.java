package Model;

import java.util.ArrayList;

public class ToDO {
    String Titolo;
    String Descrizione;
    String URL;
    boolean Stato;
    ArrayList<ToDO> ChecklistAttività;
    static String[] Utenti; //Essendo statica i dati all interno saranno gli stessi per TUTTE le istanze create
    //Non so bene che tipo mettere
    int DataScadenza;
    int Immagine;
    String ColoreSfondo;

    ToDO(String Titolo, String Descrizione, String URL, boolean Stato, int DataScadenza, int Immagine, String ColoreSfondo)
    {
        this.Titolo = Titolo;
        this.Descrizione = Descrizione;
        this.URL = URL;
        this.Stato = Stato;
        this.DataScadenza = DataScadenza;
        this.Immagine = Immagine;
        this.ColoreSfondo = ColoreSfondo;
        this.ChecklistAttività = new ArrayList<>();
    }

    @Override
    public String toString()
    {
        return "Titolo: " + Titolo + "\n" +
                "Descrizione: " + Descrizione + "\n" +
                "URL: " + URL + "\n" +
                "Stato: " + Stato + "\n" +
                "DataScadenza: " + DataScadenza + "\n" +
                "Immagine: " + Immagine + "\n" +
                "ColoreSfondo: " + ColoreSfondo;
    }

    public void ModificaStato()
    {
        this.Stato = !Stato;
    }
}
