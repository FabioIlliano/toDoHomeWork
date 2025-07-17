package model;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Rappresenta un elemento ToDo contenente titolo, descrizione, checklist di attività,
 * data di scadenza, stato, colore di sfondo, immagine e link URL.
 */
public class ToDo {
    private int idToDo;
    private String titolo;
    private String descrizione;
    private String url;
    private boolean stato;
    private ArrayList<Attivita> checklistAttivita;
    private LocalDate dataScadenza; //YYYY-MM-DD
    private byte[] immagine;
    private Color coloreSfondo;

    /**
     * Costruisce un ToDo con solo il titolo.
     *
     * @param titolo il titolo del ToDo
     */
    public ToDo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Costruttore vuoto.
     */
    public ToDo(){}

    /**
     * Restituisce l'ID del ToDo.
     *
     * @return l'identificativo del ToDo
     */
    public int getIdToDo() {
        return idToDo;
    }

    /**
     * Imposta l'ID del ToDo.
     *
     * @param idToDo l'identificativo del ToDo
     */
    public void setIdToDo(int idToDo) {
        this.idToDo = idToDo;
    }

    /**
     * Imposta la checklist delle attività.
     *
     * @param checklistAttivita la checklist da associare
     */
    public void setChecklistAttivita(ArrayList<Attivita> checklistAttivita) {
        this.checklistAttivita = checklistAttivita;
    }

    /**
     * Imposta la data di scadenza.
     *
     * @param dataScadenza la data di scadenza
     */
    public void setDataScadenza(LocalDate dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    /**
     * Restituisce il titolo del ToDo.
     *
     * @return il titolo
     */
    public String getTitolo(){
        return titolo;
    }

    /**
     * Restituisce lo stato di completamento del ToDo.
     *
     * @return true se completato, false altrimenti
     */
    public boolean getStato(){return stato;}

    /**
     * Restituisce la descrizione del ToDo.
     *
     * @return la descrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * Restituisce l'URL associato.
     *
     * @return l'URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Imposta lo stato di completamento del ToDo.
     *
     * @param stato true se completato, false altrimenti
     */
    public void setStato(boolean stato) {
        this.stato = stato;
    }

    /**
     * Restituisce l'immagine associata al ToDo.
     *
     * @return l'immagine in byte[]
     */
    public byte[] getImmagine() {
        return immagine;
    }

    /**
     * Imposta l'immagine associata al ToDo.
     *
     * @param immagine l'immagine in byte[]
     */
    public void setImmagine(byte[] immagine) {
        this.immagine = immagine;
    }

    /**
     * Imposta il titolo del ToDo.
     *
     * @param titolo il nuovo titolo
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    /**
     * Imposta la descrizione del ToDo.
     *
     * @param descrizione la nuova descrizione
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * Imposta l'URL associato al ToDo.
     *
     * @param url l'URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Imposta la data di scadenza da stringa nel formato "dd-MM-yyyy".
     *
     * @param dataScadenza la data come stringa
     * @throws Exception se la stringa non è in formato valido
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
     * Restituisce il colore di sfondo del ToDo.
     *
     * @return il colore di sfondo
     */
    public Color getColoreSfondo() {
        return coloreSfondo;
    }

    /**
     * Imposta il colore di sfondo del ToDo.
     *
     * @param coloreSfondo il colore da impostare
     */
    public void setColoreSfondo(Color coloreSfondo) {
        this.coloreSfondo = coloreSfondo;
    }

    /**
     * Restituisce la data di scadenza.
     *
     * @return la data di scadenza
     */
    public LocalDate getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Aggiunge una nuova attività alla checklist dato il titolo
     *
     * @param titolo il titolo dell'attività
     */
    public void aggiuntiAttivita(String titolo) {
        if (checklistAttivita==null)
            checklistAttivita = new ArrayList<>();

        Attivita nuovaattivita = new Attivita(titolo);
        checklistAttivita.add(nuovaattivita);
    }

    /**
     * Rimuove un'attività dalla checklist dato il titolo.
     *
     * @param titoloAttivita il nome dell'attività da rimuovere
     */
    public void rimuoviAttivita (String titoloAttivita){
        for (Attivita a : checklistAttivita){
            if (a.getNome().equals(titoloAttivita)){
                checklistAttivita.remove(a);
                return;
            }
        }
    }

    /**
     * Restituisce la lista delle attività del ToDo.
     *
     * @return la checklist delle attività
     */
    public ArrayList<Attivita> getChecklistAttivita() {
        return checklistAttivita;
    }

    /**
     * Modifica lo stato di una specifica attività.
     *
     * @param nome il nome dell'attività
     * @param b    lo stato da impostare
     */
    public void setStatoAttivita(String nome, boolean b)
    {
        for (Attivita attivita : checklistAttivita)
        {
            if (attivita.getNome().equals(nome)) {
                attivita.modificaStato(b);
                return;
            }
        }
    }

    /**
     * Cerca un'attività nella checklist dato il nome.
     *
     * @param nome il nome dell'attività
     * @return l'attività trovata, oppure null
     */
    public Attivita cercaAttivita(String nome){
        for (Attivita a : checklistAttivita)
            if (a.getNome().equals(nome))
                return a;
        return null;
    }
}
