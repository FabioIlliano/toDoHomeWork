package controller;

import model.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * La classe controller fa comunicare la GUI con il model.
 */
public class Controller {
    private Utente utente;
    private String titoloBacheca;
    private String titoloToDoCorrente;


    /**
     * instanzia un controller.
     */
    public Controller(){

    }

    //prima era privato
    public void creaUtente(String username, String password){
        utente = new Utente(username, password);
    }

    /**
     * Login boolean.
     *
     * @param username the username
     * @param password the password
     * @return the boolean
     */
    public boolean login(String username, String password){
        if (utente == null){
            creaUtente("admin", "1234");
        }
        return utente.loginGUI(username, password);
    }

    /**
     * Controlla se tutte le bacheche esistono.
     *
     * @return the boolean
     */
    public boolean checkBacheche(){
        return utente.contaBacheche();
    }

    /**
     * Crea bacheca una nuova bacheca.
     *
     * @param t il titolo
     * @param d la descrizione
     * @throws Exception l eccezione
     */
    public void creaBacheca (TitoloBacheca t, String d) throws Exception{
        if (!checkBacheche())

            return; //andrebbe gestito
        utente.creaBachechaGUI(t, d);
    }

    /**
     * Crea un nuovo todo.
     *
     * @param titoloToDo il titolo del todo
     */
    public void creaToDo(String titoloToDo)
    {
        utente.getBacheca(titoloBacheca).creaToDoGUI(titoloToDo);
    }


    /**
     * Elimina il todo.
     *
     * @param t il titolo del todo
     */
    public void eliminaToDo(String t)
    {
        utente.getBacheca(getTitoloBacheca()).eliminaToDoGUI(t);
    }

    /**
     * Cambia titolo del todo.
     *
     * @param t the titolo
     */
    public void cambiaTitoloToDo(String t)
    {
        utente.getBacheca(getTitoloBacheca()).getToDoTitolo(getTitoloToDoCorrente()).setTitolo(t);
    }

    /**
     * Cambia descrizione del todo.
     *
     * @param s la nuova descrizione
     */
    public void cambiaDescToDo(String s){
        utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setDescrizione(s);
    }

    /**
     * Cambia url del todo.
     *
     * @param s la nuova descrizione
     */
    public void cambiaURLToDo(String s){
        utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setUrl(s);
    }

    /**
     * Cambia data scadenza del todo.
     *
     * @param s la data di scadenza del ToDo
     * @return un booleano
     */
    public boolean cambiaDataScadToDo(String s){
        try{

            utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setDataScadenza(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Controlla la data di scadenza del ToDo.
     *
     * @param s la data del todo
     * @return un booleano
     */
    public boolean checkData(String s){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataScadenza = LocalDate.parse(s, formatter);
        LocalDate dataOggi = LocalDate.now();
        return !dataScadenza.isBefore(dataOggi);
    }

    /**
     * Cambia il colore del todo.
     *
     * @param c il colore del todo
     */
    public void cambiaBgColorToDo(Color c){
        utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setColoreSfondo(c);
    }

    /**
     * prende il colore del todo
     *
     * @return the color
     */
    public Color getColorBG(){
        return this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).getColoreSfondo();
    }

    /**
     * Sposta un todo tra una bacheca e l'altra
     *
     * @param nuova la nuova bacheca
     * @return un booleano
     */
    public boolean spostaToDo(TitoloBacheca nuova)
    {
        return utente.spostaToDoGUI(titoloBacheca, nuova.toString(), titoloToDoCorrente);
    }

    /**
     * Ordina todo alfabeticamente.
     */
    public void ordinaToDoAlfabeticamente() {
        //if (utente != null && titoloBacheca != null) {
            Bacheca b = utente.getBacheca(titoloBacheca);
            //if (b != null) {
                b.ordinaToDoTitolo();
            //} i controlli a che servono?
        //}
    }

    /**
     * Ordina todo per scadenza.
     */
    public void ordinaToDoPerScadenza() {
        //if (utente != null && titoloBacheca != null) {
            Bacheca b = utente.getBacheca(titoloBacheca);
            //if (b != null) {
                b.ordinaToDoDataScad();
            //} i controlli a che servono?
        //}
    }

    /**
     * prende i todo che scadono nella data odierna
     *
     * @return l' arraylist
     */
    public ArrayList<ToDo> getToDoScadenzaOggi(){
        if (utente.getBacheca(titoloBacheca).getListaToDo()!=null){
            Bacheca b = utente.getBacheca(titoloBacheca);
            return b.getToDoScadenzaOggi();
        }
        return null;
    }

    /**
     * prende i todo che scadono in una data scelta
     *
     * @param data la data
     * @return l arraylist
     */
    public ArrayList<ToDo> getToDoScadenzaFissa(LocalDate data){
        Bacheca b = utente.getBacheca(titoloBacheca);
        return b.getToDoScadenzaFissata(data);
    }

    /**
     * prende la lista dei ToDo
     *
     * @return l arraylist
     */
    public ArrayList<ToDo> getListaToDo(){
        return utente.getBacheca(titoloBacheca).getListaToDo();
    }

    /**
     * Gets titolo todo corrente.
     *
     * @return il titolo del todo corrente
     */
    public String getTitoloToDoCorrente() {
        return titoloToDoCorrente;
    }

    /**
     * modifica il titolo del todo corrente.
     *
     * @param t il nuovo titolo
     */
    public void setTitoloToDoCorrente(String t) {
        this.titoloToDoCorrente = t;
    }

    /**
     * prende l'utente.
     *
     * @return l'utente
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * prende il titolo della bacheca.
     *
     * @return il titolo bacheca
     */
    public String getTitoloBacheca() {
        return titoloBacheca;
    }

    /**
     * modifica lo tato del ToDo
     *
     * @param b lo stato
     */
    public void setCompletoToDo(boolean b){
        this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setStato(b);
    }

    /**
     * restituisce lo stato del todo
     *
     * @return lo stato del todo booleano
     */
    public boolean getCompletoToDo(){
        return this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).isStato();
    }

    /**
     * restituisce la data di scadenza del ToDo
     *
     * @return la data di scadenza
     */
    public String getDataScadToDo(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate datascad = this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).getDataScadenza();
        if (datascad!=null)
            return datascad.format(formatter);
        else
            return null;
    }

    /**
     * restituisce la descrizione del ToDo.
     *
     * @return una string ovvero la descrizione del ToDo
     */
    public String getDescrizioneToDo(){
        return this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).getDescrizione();
    }

    /**
     * restituisce l'URL.
     *
     * @return una string ovvero l'URL del ToDo
     */
    public String getUrlToDo(){
        return this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).getUrl();
    }

    /**
     * restituisce il colore del ToDo
     *
     * @return un color ovvero il colore del ToDo
     */
    public Color getBGColorToDo(){
        return this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).getColoreSfondo();
    }

    /**
     * Modifica il titolo della bacheca.
     *
     * @param titoloBacheca il titolo della bacheca
     */
    public void setTitoloBacheca(String titoloBacheca) {
        this.titoloBacheca = titoloBacheca;
    }

    /**
     * Modifica l'immagine del ToDo.
     *
     * @param img l'immagine
     */
    public void setIMGToDo(Image img){
        this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setImmagine(img);
    }

    /**
     * restituisce l'immagine del ToDo.
     *
     * @return l'immagine del ToDo
     */
    public Image getIMGToDo(){
        return this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).getImmagine();
    }

    /**
     * Controlla se la bacheca esiste.
     *
     * @param nomeBacheca il nome della bacheca
     * @return un booleano
     */
    public boolean checkBacheca (String nomeBacheca){
        if (utente.getBacheca(nomeBacheca) == null)
            return false;
        else
            return true;
    }

    /**
     * restituisce la descrizione della bacheca.
     *
     * @param t il titolo della bacheca
     * @return una string
     */
    public String getDescrizioneBacheca(String t){
        return utente.getBacheca(t).getDescrizione();
    }

    /**
     * Cerca un todo in un array list.
     *
     * @param titoloToDo il titolo del ToDo da cercare
     * @return l'arrayList formato da tutti i ToDo che hanno quel nome;
     */
    public ArrayList<ToDo> cercaToDo(String titoloToDo){
        return utente.getBacheca(titoloBacheca).cercaToDo(titoloToDo);
    }

    /**
     * restituisce true se nessuna bacheca esiste
     *
     * @return un booleaeno
     */
    public boolean noBacheca(){
        if (utente.getBacheca(TitoloBacheca.LAVORO.toString())==null && utente.getBacheca(TitoloBacheca.TEMPO_LIBERO.toString())==null && utente.getBacheca(TitoloBacheca.UNIVERSITA.toString())==null )
            return true;
        else
            return false;
    }

    /**
     * Crea attivita.
     *
     * @param titoloAttivita il titolo dell'attivita
     */
    public void creaAttivita(String titoloAttivita)
    {
        utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).aggiuntiAttivitaGUI(titoloAttivita);
    }

    /**
     * restituisce la lista attivita.
     *
     * @return l'arraylist
     */
    public ArrayList<Attivita> getListaAttivita(){
        return utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).getChecklistAttivita();
    }

    /**
     * Modifica lo stato di un attivita.
     */
    public void setStato(String nome){
        utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setStatoAttivita(nome);
    }

    /**
     * restituisce true se tutti i ToDo sono completati.
     *
     * @return un boolean
     */
    public boolean checkChecklist(){
        return utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).checkChecklist();
    }

}
