package model;

import java.util.ArrayList;
import java.time.LocalDate; //Libreria per la gestione delle date (in questo caso la data di scadenza del ToDo)
import java.util.Scanner;

public class ToDo {
    private String titolo;
    private String descrizione;
    private String url;
    private boolean stato;
    private ArrayList<Attivita> checklistAttivita;
    // a che serve? private static ArrayList<Utente> utenti; //Essendo statica i dati all interno saranno gli stessi per TUTTE le istanze create
    private LocalDate dataScadenza; //Per mettere anche l ora basterebbe usare il tipo LocalDateTime ma non c ho sbatti
    //Non so bene che tipo mettere
    private int immagine; //da capire il tipo
    private String coloreSfondo;
    //non so se va tenuta così
    private ArrayList<Utente> listaUtentiCondivisione;

    public ToDo(String titolo, String descrizione, String url, String dataScadenza, int immagine, String coloreSfondo)
    {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.url = url;
        stato = false;
        this.dataScadenza = LocalDate.parse(dataScadenza);;
        this.immagine = immagine;
        this.coloreSfondo = coloreSfondo;
        //this.checklistAttivita = new ArrayList<>(); questa non ci va perchè creare l'arraylist di attività è opzionale
        listaUtentiCondivisione = new ArrayList<>();
    }
    //ricordiamoci che molte cose sono opzionali

    public String getTitolo(){
        return titolo;
    }

    public boolean getStato(){return stato;}

    public void ModificaStato()
    {
        this.stato = !stato;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDataScadenza(String dataScadenza) {
        this.dataScadenza = LocalDate.parse(dataScadenza);
    }

    public void setImmagine(int immagine) {
        this.immagine = immagine;
    }

    public void setColoreSfondo(String coloreSfondo) {
        this.coloreSfondo = coloreSfondo;
    }

    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    @Override
    public String toString()
    {
        String s;
        if (stato)
            s = "completato";
        else
            s = "non completato";

        return "Titolo: " + titolo + "\n" +
                "Descrizione: " + descrizione + "\n" +
                "URL: " + url + "\n" +
                "Stato: " + s + "\n" +
                "DataScadenza: " + dataScadenza + "\n" +
                "Immagine: " + immagine + "\n" +
                "ColoreSfondo: " + coloreSfondo;
    }

    public void condividiToDo(Utente destinatario, String titoloBacheca)
    {
        destinatario.getBacheca(titoloBacheca).aggiungiToDo(this);
    }

    public void eliminaCondivisione(Utente destinatario, String titoloBacheca)
    {
        try{
            destinatario.getBacheca(titoloBacheca).rimuoviToDo(this);
        }catch (NullPointerException e){
            System.out.println("Non c'è nessun ToDo condiviso");
        }
        catch (Exception e){
            System.out.println("Il todo richiesto non è stato trovato");
        }

    }

    //in un certo senso ingola creaCheckList
    public void aggiungiAttivita()
    {
        if (checklistAttivita==null)
            checklistAttivita = new ArrayList<>();

        Scanner in = new Scanner(System.in);
        String nome;

        System.out.print("Inserisci il nome della nuova attività: ");
        nome = in.nextLine();
        Attivita nuovaattivita = new Attivita(nome);
        checklistAttivita.add(nuovaattivita);
    }
}
