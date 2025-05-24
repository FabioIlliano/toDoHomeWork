package Controller;

import model.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The type Controller.
 */
public class Controller {
    private ToDo todo;
    private Utente utente;
    private Attivita attivita;
    private String titoloBacheca;
    private String titoloToDoCorrente;
    private String descrizioneBacheca;


    /**
     * Instantiates a new Controller.
     */
    public Controller(){

    }

    private void creaUtente(String username, String password){
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
     * Check bacheche boolean.
     *
     * @return the boolean
     */
    public boolean checkBacheche(){
        return utente.contaBacheche();
    }

    /**
     * Crea bacheca.
     *
     * @param t the t
     * @param d the d
     * @throws Exception the exception
     */
    public void creaBacheca (TitoloBacheca t, String d) throws Exception{
        if (!checkBacheche())

            return; //andrebbe gestito
        utente.creaBachechaGUI(t, d);
    }

    /**
     * Crea to do.
     *
     * @param titoloToDo the titolo to do
     */
    public void creaToDo(String titoloToDo)
    {
        utente.getBacheca(titoloBacheca).creaToDoGUI(titoloToDo);
    }


    /**
     * Elimina to do.
     *
     * @param t the t
     */
    public void eliminaToDo(String t)
    {
        utente.getBacheca(getTitoloBacheca()).eliminaToDoGUI(t);
    }

    /**
     * Cambia titolo to do.
     *
     * @param t the t
     */
    public void cambiaTitoloToDo(String t)
    {
        utente.getBacheca(getTitoloBacheca()).getToDoTitolo(getTitoloToDoCorrente()).setTitolo(t);
    }

    /**
     * Cambia desc to do.
     *
     * @param s the s
     */
    public void cambiaDescToDo(String s){
        utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setDescrizione(s);
    }

    /**
     * Cambia url to do.
     *
     * @param s the s
     */
    public void cambiaURLToDo(String s){
        utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setUrl(s);
    }

    /**
     * Cambia data scad to do boolean.
     *
     * @param s the s
     * @return the boolean
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
     * Check data boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean checkData(String s){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataScadenza = LocalDate.parse(s, formatter);
        LocalDate dataOggi = LocalDate.now();
        return !dataScadenza.isBefore(dataOggi);
    }

    /**
     * Cambia bg color to do.
     *
     * @param c the c
     */
    public void cambiaBgColorToDo(Color c){
        utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setColoreSfondo(c);
    }

    /**
     * Get color bg color.
     *
     * @return the color
     */
    public Color getColorBG(){
        return this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).getColoreSfondo();
    }

    /**
     * Sposta to do boolean.
     *
     * @param nuova the nuova
     * @return the boolean
     */
    public boolean spostaToDo(TitoloBacheca nuova)
    {
        return utente.spostaToDoGUI(titoloBacheca, nuova.toString(), titoloToDoCorrente);
    }

    /**
     * Ordina to do alfabeticamente.
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
     * Ordina to do per scadenza.
     */
    public void ordinaToDoPerScadenza() {
        //if (utente != null && titoloBacheca != null) {
            Bacheca b = utente.getBacheca(titoloBacheca);
            //if (b != null) {
                b.ordinaToDoDataScad();
            //} i controlli a che servono?
        //}
    }

    public ArrayList<ToDo> getToDoScadenzaOggi(){
        if (utente.getBacheca(titoloBacheca).getListaToDo()!=null){
            Bacheca b = utente.getBacheca(titoloBacheca);
            return b.getToDoScadenzaOggi();
        }
        return null;
    }

    public ArrayList<ToDo> getToDoScadenzaFissa(LocalDate data){
        Bacheca b = utente.getBacheca(titoloBacheca);
        return b.getToDoScadenzaFissata(data);
    }

    /**
     * Get lista to do 2 arraylist.
     *
     * @return the array list
     */
    public ArrayList<ToDo> getListaToDo(){
        return utente.getBacheca(titoloBacheca).getListaToDo();
    }

    /**
     * Gets titolo to do corrente.
     *
     * @return the titolo to do corrente
     */
    public String getTitoloToDoCorrente() {
        return titoloToDoCorrente;
    }

    /**
     * Sets titolo to do corrente.
     *
     * @param t the t
     */
    public void setTitoloToDoCorrente(String t) {
        this.titoloToDoCorrente = t;
    }

    /**
     * Gets utente.
     *
     * @return the utente
     */
    public Utente getUtente() {
        return utente;
    }

    /**
     * Gets titolo bacheca.
     *
     * @return the titolo bacheca
     */
    public String getTitoloBacheca() {
        return titoloBacheca;
    }

    /**
     * Set completo to do.
     *
     * @param b the b
     */
    public void setCompletoToDo(boolean b){
        this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setStato(b);
    }

    /**
     * Get completo to do boolean.
     *
     * @return the boolean
     */
    public boolean getCompletoToDo(){
        return this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).isStato();
    }

    /**
     * Get data scad to do string.
     *
     * @return the string
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
     * Get descrizione to do string.
     *
     * @return the string
     */
    public String getDescrizioneToDo(){
        return this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).getDescrizione();
    }

    /**
     * Get url to do string.
     *
     * @return the string
     */
    public String getUrlToDo(){
        return this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).getUrl();
    }

    /**
     * Get bg color to do color.
     *
     * @return the color
     */
    public Color getBGColorToDo(){
        return this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).getColoreSfondo();
    }

    /**
     * Sets titolo bacheca.
     *
     * @param titoloBacheca the titolo bacheca
     */
    public void setTitoloBacheca(String titoloBacheca) {
        this.titoloBacheca = titoloBacheca;
    }

    /**
     * Set img to do.
     *
     * @param img the img
     */
    public void setIMGToDo(Image img){
        this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setImmagine(img);
    }

    /**
     * Get img to do image.
     *
     * @return the image
     */
    public Image getIMGToDo(){
        return this.utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).getImmagine();
    }

    /**
     * Check bacheca boolean.
     *
     * @param nomeBacheca the nome bacheca
     * @return the boolean
     */
    public boolean checkBacheca (String nomeBacheca){
        if (utente.getBacheca(nomeBacheca) == null)
            return false;
        else
            return true;
    }

    /**
     * Get descrizione bacheca string.
     *
     * @param t the t
     * @return the string
     */
    public String getDescrizioneBacheca(String t){
        return utente.getBacheca(t).getDescrizione();
    }

    public ArrayList<ToDo> cercaToDo(String titoloToDo){
        return utente.getBacheca(titoloBacheca).cercaToDo(titoloToDo);
    }

    /**
     * No bacheca boolean.
     *
     * @return the boolean
     */
    public boolean noBacheca(){
        if (utente.getBacheca(TitoloBacheca.LAVORO.toString())==null && utente.getBacheca(TitoloBacheca.TEMPO_LIBERO.toString())==null && utente.getBacheca(TitoloBacheca.UNIVERSITA.toString())==null )
            return true;
        else
            return false;
    }


}
