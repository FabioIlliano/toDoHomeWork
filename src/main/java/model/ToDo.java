package model;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate; //Libreria per la gestione delle date (in questo caso la data di scadenza del ToDo)
import java.util.Scanner;

/**
 * The type To do.
 */
public class ToDo {
    private String titolo;
    private String descrizione;
    private String url;
    private boolean stato;
    private ArrayList<Attivita> checklistAttivita;
    private ArrayList<Utente> listaUtentiCondivisione;
    private LocalDate dataScadenza; //YYYY-MM-DD
    //Non so bene che tipo mettere
    private Image immagine; //da capire il tipo
    private Color coloreSfondo;

    /**
     * Instantiates a new To do.
     *
     * @param titolo       the titolo
     * @param descrizione  the descrizione
     * @param url          the url
     * @param dataScadenza the data scadenza
     * @param immagine     the immagine
     * @param coloreSfondo the colore sfondo
     */
//costruttore inutile
    public ToDo(String titolo, String descrizione, String url, String dataScadenza, int immagine, Color coloreSfondo)
    {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.url = url;
        stato = false;
        this.dataScadenza = LocalDate.parse(dataScadenza);;
        //this.immagine = immagine;
        this.coloreSfondo = coloreSfondo;
        this.checklistAttivita = new ArrayList<>();
    }
    //ricordiamoci che molte cose sono opzionali


    /**
     * Instantiates a new To do.
     *
     * @param titolo the titolo
     */
//nuovo costruttore
    public ToDo(String titolo)
    {
        this.titolo = titolo;
    }

    /**
     * Get titolo string.
     *
     * @return the string
     */
    public String getTitolo(){
        return titolo;
    }

    /**
     * Get stato boolean.
     *
     * @return the boolean
     */
    public boolean getStato(){return stato;}

    /**
     * Gets descrizione.
     *
     * @return the descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Is stato boolean.
     *
     * @return the boolean
     */
    public boolean isStato() {
        return stato;
    }

    /**
     * Sets stato.
     *
     * @param stato the stato
     */
    public void setStato(boolean stato) {
        this.stato = stato;
    }

    /**
     * Gets immagine.
     *
     * @return the immagine
     */
    public Image getImmagine() {
        return immagine;
    }

    /**
     * Set immagine.
     *
     * @param img the img
     */
    public void setImmagine(Image img){
        this.immagine = img;
    }

    /**
     * Modifica stato.
     */
    public void ModificaStato()
    {
        this.stato = !stato;
    }

    /**
     * Sets titolo.
     *
     * @param titolo the titolo
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Sets descrizione.
     *
     * @param descrizione the descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Sets url.
     *
     * @param url the url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Sets data scadenza.
     *
     * @param dataScadenza the data scadenza
     * @throws Exception the exception
     */
    public void setDataScadenza(String dataScadenza) throws Exception{
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            this.dataScadenza = LocalDate.parse(dataScadenza, formatter);
        } catch (Exception e) {
            throw new Exception(e);
        }

    }


    /**
     * Gets colore sfondo.
     *
     * @return the colore sfondo
     */
    public Color getColoreSfondo() {
        return coloreSfondo;
    }

    /**
     * Sets colore sfondo.
     *
     * @param coloreSfondo the colore sfondo
     */
    public void setColoreSfondo(Color coloreSfondo) {
        this.coloreSfondo = coloreSfondo;
    }

    /**
     * Gets data scadenza.
     *
     * @return the data scadenza
     */
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

    /**
     * Condividi to do.
     *
     * @param destinatario  the destinatario
     * @param titoloBacheca the titolo bacheca
     */
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

    /**
     * Elimina condivisione.
     *
     * @param destinatario  the destinatario
     * @param titoloBacheca the titolo bacheca
     */
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

    /**
     * Aggiungi attivita.
     */
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

    /**
     * Aggiunti attivita gui.
     *
     * @param titolo the titolo
     */
    public void aggiuntiAttivitaGUI(String titolo)
    {
        if (checklistAttivita==null)
            checklistAttivita = new ArrayList<>();

        Attivita nuovaattivita = new Attivita(titolo);
        checklistAttivita.add(nuovaattivita);
    }

    /**
     * Gets checklist attivita.
     *
     * @return the checklist attivita
     */
    public ArrayList<Attivita> getChecklistAttivita() {
        return checklistAttivita;
    }

    public boolean checkChecklist()
    {
        for (Attivita attivita : checklistAttivita)
        {
            if (!attivita.isStato())
                return false;

        }
        return true;
    }

    public void setStatoAttivita(String nome)
    {
        for (Attivita attivita : checklistAttivita)
        {
            if (attivita.getNome().equals(nome))
                attivita.modificaStato();
        }
    }
}
