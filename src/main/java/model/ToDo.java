package model;

import java.awt.*;
import java.util.ArrayList;
import java.time.LocalDate; //Libreria per la gestione delle date (in questo caso la data di scadenza del ToDo)
import java.util.Scanner;

public class ToDo {
    private String titolo;
    private String descrizione;
    private String url;
    private boolean stato;
    private ArrayList<Attivita> checklistAttivita;
    private ArrayList<Utente> listaUtentiCondivisione;
    private LocalDate dataScadenza; //YYYY-MM-DD
    //Non so bene che tipo mettere
    private int immagine; //da capire il tipo
    private Color coloreSfondo;

    public ToDo(String titolo, String descrizione, String url, String dataScadenza, int immagine, Color coloreSfondo)
    {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.url = url;
        stato = false;
        this.dataScadenza = LocalDate.parse(dataScadenza);;
        this.immagine = immagine;
        this.coloreSfondo = coloreSfondo;
        this.checklistAttivita = new ArrayList<>();
    }
    //ricordiamoci che molte cose sono opzionali

    //nuovo costruttore
    public ToDo(String titolo)
    {
        this.titolo = titolo;
    }

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

    public void setDataScadenza(String dataScadenza) throws Exception{
        try{
            this.dataScadenza = LocalDate.parse(dataScadenza);
        } catch (Exception e) {
            throw new Exception(e);
        }

    }

    public void setImmagine(int immagine) {
        this.immagine = immagine;
    }

    public Color getColoreSfondo() {
        return coloreSfondo;
    }

    public void setColoreSfondo(Color coloreSfondo) {
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

    //metodo che condivide un todo ad un altro utente
    public void condividiToDo(Utente destinatario, String titoloBacheca)
    {
        try{
            destinatario.getBacheca(titoloBacheca).aggiungiToDo(this);
            listaUtentiCondivisione.add(destinatario);
        }catch (Exception e){
            System.out.println("Condivisione non andata a buon fine");
        }

    }

    //metodo che elimina la condivisione da un altro utente
    public void eliminaCondivisione(Utente destinatario, String titoloBacheca)
    {
        try{
            destinatario.getBacheca(titoloBacheca).rimuoviToDo(this);
            listaUtentiCondivisione.remove(destinatario);
        }catch (NullPointerException e){
            System.out.println("Non c'è nessun ToDo condiviso");
        }
        catch (Exception e){
            System.out.println("Il todo richiesto non è stato trovato");
        }

    }

    //metodo che crea e aggiunge attività all'arraylist di attività
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
