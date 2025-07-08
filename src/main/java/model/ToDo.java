package model;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate; //Libreria per la gestione delle date (in questo caso la data di scadenza del ToDo)


/**
 * The type To do.
 */
public class ToDo {
    private int idToDo;
    private String titolo;
    private String descrizione;
    private String url;
    private boolean stato;
    private ArrayList<Attivita> checklistAttivita;
    private ArrayList<Utente> listaUtentiCondivisione;
    private LocalDate dataScadenza; //YYYY-MM-DD
    private Image immagine;
    private Color coloreSfondo;
    private String imgPath;

    /**
     * Instantiates a new To do.
     *
     * @param titolo the titolo
     */

    public ToDo(String titolo)
    {
        this.titolo = titolo;
    }

    public ToDo (String titolo, int idToDo){
        this.idToDo = idToDo;
        this.titolo = titolo;
    }

    public ToDo(){}

    public int getIdToDo() {
        return idToDo;
    }

    public void setIdToDo(int idToDo) {
        this.idToDo = idToDo;
    }

    public void setChecklistAttivita(ArrayList<Attivita> checklistAttivita) {
        this.checklistAttivita = checklistAttivita;
    }

    public ArrayList<Utente> getListaUtentiCondivisione() {
        return listaUtentiCondivisione;
    }

    public void setListaUtentiCondivisione(ArrayList<Utente> listaUtentiCondivisione) {
        this.listaUtentiCondivisione = listaUtentiCondivisione;
    }

    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
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
    public void modificaStato()
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
        if (dataScadenza==null || dataScadenza.trim().isEmpty()){
            this.dataScadenza = null;
            return;
        }
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
    public int condividiToDo(Utente destinatario, String titoloBacheca)
    {
        try{
            destinatario.getBacheca(titoloBacheca).aggiungiToDo(this);
            listaUtentiCondivisione.add(destinatario);
            return 0;
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * Elimina condivisione.
     *
     * @param destinatario  the destinatario
     * @param titoloBacheca the titolo bacheca
     */
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
     * Aggiunti attivita gui.
     *
     * @param titolo the titolo
     */
    public void aggiuntiAttivita(String titolo) {
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
            if (attivita.getNome().equals(nome)) {
                attivita.modificaStato();
                return;
            }
        }
    }

    public Attivita cercaAttivita(String nome){
        for (Attivita a : checklistAttivita)
            if (a.getNome().equals(nome))
                return a;
        return null;
    }
}
