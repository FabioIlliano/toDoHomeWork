package model;

import java.util.ArrayList;
import java.util.Date;

public class ToDO {
    private String titolo;
    private String descrizione;
    private String url;
    private boolean stato;
    private ArrayList<Attivita> checklistAttivita;
    private static ArrayList<Utente> utenti; //Essendo statica i dati all interno saranno gli stessi per TUTTE le istanze create
    //Non so bene che tipo mettere
    private Date dataScadenza; //esiste questa classe ma non so come si usi
    private int immagine; //da capire il tipo
    private String coloreSfondo;

    public ToDO(String titolo, String descrizione, String url, Date dataScadenza, int immagine, String coloreSfondo)
    {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.url = url;
        stato = false;
        this.dataScadenza = dataScadenza;
        this.immagine = immagine;
        this.coloreSfondo = coloreSfondo;
        this.checklistAttivita = new ArrayList<>();
    }
    //ricordiamoci che molte cose sono opzionali

    @Override
    public String toString()
    {
        return "Titolo: " + titolo + "\n" +
                "Descrizione: " + descrizione + "\n" +
                "URL: " + url + "\n" +
                "Stato: " + stato + "\n" +
                "DataScadenza: " + dataScadenza + "\n" +
                "Immagine: " + immagine + "\n" +
                "ColoreSfondo: " + coloreSfondo;
    }

    public void ModificaStato()
    {
        this.stato = !stato;
    }
}
