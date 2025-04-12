package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.LocalDate; //Libreria per la gestione delle date (in questo caso la data di scadenza del ToDo)

public class ToDO {
    String Titolo;
    String Descrizione;
    String URL;
    boolean Stato;
    ArrayList<ToDO> ChecklistAttività;
    static String[] Utenti; //Essendo statica i dati all interno saranno gli stessi per TUTTE le istanze create
    LocalDate DataScadenza; //Per mettere anche l ora basterebbe usare il tipo LocalDateTime ma non c ho sbatti
    //Non so bene che tipo mettere
    int Immagine;
    String ColoreSfondo;

    ToDO(String Titolo, String Descrizione, String URL, String DataScadenza, int Immagine, String ColoreSfondo)
    {
        this.Titolo = Titolo;
        this.Descrizione = Descrizione;
        this.URL = URL;
        this.Stato = false;
        this.DataScadenza = LocalDate.parse(DataScadenza);
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
