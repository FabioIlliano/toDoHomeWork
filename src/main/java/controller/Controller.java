package controller;

import dao.*;
import implementazioniPostgresDAO.*;
import model.*;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * La classe controller fa comunicare la GUI con il model.
 */
public class Controller {
    private Utente utenteCorrente;
    private String titoloBachecaCorrente;
    private String titoloToDoCorrente;
    private int idToDoCorrente;


    /**
     * instanzia un controller.
     */
    public Controller(){

    }

    public void logout(){
        utenteCorrente = null;
        titoloBachecaCorrente = null;
        idToDoCorrente = -1;
    }


    public int creaUtente(String username, String password){
        try{
            UtenteDAO u = new UtenteImplementazionePostgresDAO();
            return u.inserisciutenteDB(username, password);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<ToDo> getListaToDoLocale() {
        return utenteCorrente.getBacheca(titoloBachecaCorrente).getListaToDo();
    }

    public int getIdToDoCorrente() {
        return idToDoCorrente;
    }

    public void setIdToDoCorrente(int idToDoCorrente) {
        this.idToDoCorrente = idToDoCorrente;
    }


    /**
     * Login boolean.
     *
     * @param username the username
     * @param password the password
     * @return the boolean
     */
    public boolean login(String username, String password){
        try{
            UtenteDAO u = new UtenteImplementazionePostgresDAO();
            int r = u.loginUtenteDB(username, password);
            if (r==0){
                utenteCorrente = new Utente(username, password);

                return true;
            }
            else
                return false;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * Controlla se tutte le bacheche esistono.
     *
     * @return the boolean
     */
    public boolean checkBacheche(){
        return utenteCorrente.puoAggiungereBacheca();
    }

    /**
     * Crea bacheca una nuova bacheca.
     *
     * @param t il titolo
     * @param d la descrizione
     * @throws Exception l eccezione
     */
    public void creaBacheca (TitoloBacheca t, String d) throws Exception{
        if (!utenteCorrente.puoAggiungereBacheca())
            throw new Exception("BACHECHE GIA CREATE!!");

        if (utenteCorrente.getBacheca(t.toString())!=null)
            throw new Exception("NOME BACHECHA GIA UTILIZZATO!!");

        BachecaDAO b = new BachecaImplementazionePostgresDAO();
        int r = b.creaBacheca(t, d, utenteCorrente.getUsername());
        if (r!=0)
            throw new Exception("Errore nella creazione");

        utenteCorrente.creaBachecaGUI(t, d);

    }

    public void caricaBacheche () throws Exception{
        UtenteDAO u = new UtenteImplementazionePostgresDAO();
        ArrayList<Bacheca> a = u.getBachecheUtenteDB(utenteCorrente.getUsername());
        if (a.isEmpty())
            return;
        utenteCorrente.pulisciBacheche();
        for (Bacheca b : a)
            utenteCorrente.caricaBacheca(b.getTitolo(), b.getDescrizione());

    }

    /**
     * Crea un nuovo todo.
     *
     * @param titoloToDo il titolo del todo
     */
    public int creaToDo(String titoloToDo)
    {
        try{
            ToDoDAO toDoDAO = new ToDoImplementazionePostgresDAO();
            int r = toDoDAO.creaToDo(titoloToDo, titoloBachecaCorrente, utenteCorrente.getUsername());
            if (r!=-1)
                utenteCorrente.getBacheca(titoloBachecaCorrente).creaToDoGUI(titoloToDo);
            return r;
        }catch (Exception e){
            return -1;
        }
    }

    public void aggiornaToDo() throws SQLException {
        ToDoDAO toDoDAO = new ToDoImplementazionePostgresDAO();
        toDoDAO.aggiornaToDo(getToDo());
    }

    public ToDo getToDo (){
        return utenteCorrente.getBacheca(titoloBachecaCorrente).getToDoId(idToDoCorrente);
    }


    /**
     * Elimina il todo.
     *
     */
    public void eliminaToDo()
    {
        try{
            ToDoDAO toDoDAO = new ToDoImplementazionePostgresDAO();
            toDoDAO.eliminaToDo(idToDoCorrente);
            utenteCorrente.getBacheca(titoloBachecaCorrente).eliminaToDoGUI(idToDoCorrente);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Cambia titolo del todo.
     *
     * @param t the titolo
     */
    public void cambiaTitoloToDo(String t)
    {
        getToDo().setTitolo(t);
    }

    /**
     * Cambia descrizione del todo.
     *
     * @param s la nuova descrizione
     */
    public void cambiaDescToDo(String s){
        getToDo().setDescrizione(s);
    }

    /**
     * Cambia url del todo.
     *
     * @param s la nuova descrizione
     */
    public void cambiaURLToDo(String s){
        getToDo().setUrl(s);
    }

    /**
     * Cambia data scadenza del todo.
     *
     * @param s la data di scadenza del ToDo
     * @return un booleano
     */
    public boolean cambiaDataScadToDo(String s){
        try{
            getToDo().setDataScadenza(s);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
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
        getToDo().setColoreSfondo(c);
    }

    /**
     * prende il colore del todo
     *
     * @return the color
     */
    public Color getColorBG(){
        return this.getToDo().getColoreSfondo();
    }

    /**
     * Sposta un todo tra una bacheca e l'altra
     *
     * @param nuova la nuova bacheca
     * @return un booleano
     */
    public boolean spostaToDo(TitoloBacheca nuova) {
        try{
            ToDoDAO toDoDAO = new ToDoImplementazionePostgresDAO();
            int r = toDoDAO.spostaToDo(idToDoCorrente, nuova.toString(), utenteCorrente.getUsername());
            if (r==0)
                utenteCorrente.spostaToDoGUI(titoloBachecaCorrente, nuova.toString(), getToDo());
            return r==0;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Ordina todo alfabeticamente.
     */
    public void ordinaToDoAlfabeticamente() {
        Bacheca b = utenteCorrente.getBacheca(titoloBachecaCorrente);
        b.ordinaToDoTitolo();
    }

    /**
     * Ordina todo per scadenza.
     */
    public void ordinaToDoPerScadenza() {
        Bacheca b = utenteCorrente.getBacheca(titoloBachecaCorrente);
        b.ordinaToDoDataScad();
    }

    /**
     * prende i todo che scadono nella data odierna
     *
     * @return l' arraylist
     */
    public ArrayList<ToDo> getToDoScadenzaOggi(){
        if (utenteCorrente.getBacheca(titoloBachecaCorrente).getListaToDo()!=null){
            Bacheca b = utenteCorrente.getBacheca(titoloBachecaCorrente);
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
        Bacheca b = utenteCorrente.getBacheca(titoloBachecaCorrente);
        return b.getToDoScadenzaFissata(data);
    }

    /**
     * prende la lista dei ToDo
     *
     * @return l arraylist
     */
    public ArrayList<ToDo> getListaToDo(){
        try {
            ToDoDAO toDoDAO = new ToDoImplementazionePostgresDAO();
            ArrayList<ToDo> list = toDoDAO.caricaDatiToDo(titoloBachecaCorrente, utenteCorrente.getUsername());
            list.addAll(toDoDAO.caricaDatiToDoCondivisi(titoloBachecaCorrente, utenteCorrente.getUsername()));
            return list;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    public ArrayList<ToDo> getToDoScaduti(){
        try {
            ToDoDAO toDoDAO = new ToDoImplementazionePostgresDAO();
            return toDoDAO.caricaDatiToDo(titoloBachecaCorrente, utenteCorrente.getUsername());
        }
        catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    public void setListToDo(ToDo t){
        utenteCorrente.getBacheca(titoloBachecaCorrente).aggiungiToDo(t);
    }

    public void pulisciListaToDo(){
        utenteCorrente.getBacheca(titoloBachecaCorrente).getListaToDo().clear();
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
        return utenteCorrente;
    }

    /**
     * prende il titolo della bacheca.
     *
     * @return il titolo bacheca
     */
    public String getTitoloBacheca() {
        return titoloBachecaCorrente;
    }

    /**
     * modifica lo tato del ToDo
     *
     * @param b lo stato
     */
    public void setCompletoToDo(boolean b){
        getToDo().setStato(b);
    }

    /**
     * restituisce lo stato del todo
     *
     * @return lo stato del todo booleano
     */
    public boolean getCompletoToDo(){
        return getToDo().getStato();
    }

    /**
     * restituisce la data di scadenza del ToDo
     *
     * @return la data di scadenza
     */
    public String getDataScadToDo(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate datascad = getToDo().getDataScadenza();
        if (datascad!=null)
            return datascad.format(formatter);
        else
            return "";
    }

    /**
     * restituisce la descrizione del ToDo.
     *
     * @return una string ovvero la descrizione del ToDo
     */
    public String getDescrizioneToDo(){
        String s = getToDo().getDescrizione();
        if (s!=null)
            return s;
        else
            return "";
    }

    /**
     * restituisce l'URL.
     *
     * @return una string ovvero l'URL del ToDo
     */
    public String getUrlToDo(){
        String s = getToDo().getUrl();
        if (s!=null)
            return s;
        else
            return "";
    }

    /**
     * restituisce il colore del ToDo
     *
     * @return un color ovvero il colore del ToDo
     */
    public Color getBGColorToDo(){
        return getToDo().getColoreSfondo();
    }

    /**
     * Modifica il titolo della bacheca.
     *
     * @param titoloBacheca il titolo della bacheca
     */
    public void setTitoloBacheca(String titoloBacheca) {
        this.titoloBachecaCorrente = titoloBacheca;
    }

    /**
     * Modifica l'immagine del ToDo.
     *
     * @param img l'immagine
     */
    public void setIMGToDo(Image img, String path){
        getToDo().setImmagine(img);
        getToDo().setImgPath(path);
    }

    /**
     * restituisce l'immagine del ToDo.
     *
     * @return l'immagine del ToDo
     */
    public Image getIMGToDo(){
        return getToDo().getImmagine();
    }

    public String getImgPath(){
        return getToDo().getImgPath();
    }

    /**
     * Controlla se la bacheca esiste.
     *
     * @param nomeBacheca il nome della bacheca
     * @return un booleano
     */
    public boolean checkBacheca (String nomeBacheca){
        if (utenteCorrente.getBacheca(nomeBacheca) == null)
            return false;
        else
            return true;
    }

    public boolean eliminaBacheca (TitoloBacheca titolo){
        boolean b = utenteCorrente.eliminaBachecaGUI(titolo.toString());

        try {
            BachecaDAO bachecaDAO = new BachecaImplementazionePostgresDAO();
            int r = bachecaDAO.eliminaBacheca(titolo, utenteCorrente.getUsername());
            boolean b1 = r==0;
            return (b && b1);
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean cambiaDescrizioneBacheca (TitoloBacheca titolo, String descrizione){
        utenteCorrente.getBacheca(titolo.toString()).setDescrizione(descrizione);

        try {
            BachecaDAO bachecaDAO = new BachecaImplementazionePostgresDAO();
            int r = bachecaDAO.aggiornaDescrizioneBacheca(titolo, descrizione, utenteCorrente.getUsername());
            return r==0;
        }
        catch (Exception e){
            return false;
        }
    }

    /**
     * restituisce la descrizione della bacheca.
     *
     * @param t il titolo della bacheca
     * @return una string
     */
    public String getDescrizioneBacheca(String t){
        return utenteCorrente.getBacheca(t).getDescrizione();
    }

    /**
     * Cerca un todo in un array list.
     *
     * @param titoloToDo il titolo del ToDo da cercare
     * @return l'arrayList formato da tutti i ToDo che hanno quel nome;
     */
    public ArrayList<ToDo> cercaToDo(String titoloToDo){
        return utenteCorrente.getBacheca(titoloBachecaCorrente).cercaToDo(titoloToDo);
    }

    /**
     * restituisce true se nessuna bacheca esiste
     *
     * @return un booleaeno
     */
    public boolean noBacheca(){
        if (utenteCorrente.getBacheca(TitoloBacheca.LAVORO.toString())==null && utenteCorrente.getBacheca(TitoloBacheca.TEMPO_LIBERO.toString())==null && utenteCorrente.getBacheca(TitoloBacheca.UNIVERSITA.toString())==null )
            return true;
        else
            return false;
    }

    /**
     * Crea attivita.
     *
     * @param titoloAttivita il titolo dell'attivita
     */
    public int creaAttivita(String titoloAttivita)
    {
        try{
            AttivitaDAO attivitaDAO = new AttivitaImplementazionePostgresDAO();
            int r = attivitaDAO.creaAttivita(idToDoCorrente, titoloAttivita);
            if (r==0){
                getToDo().aggiuntiAttivita(titoloAttivita);
                return 0;
            }
            else
                return -1;
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }

    }

    public int eliminaAttivita(String nome){
        try{
            AttivitaDAO attivitaDAO = new AttivitaImplementazionePostgresDAO();
            return attivitaDAO.eliminaAttivita(nome, idToDoCorrente);
        }
        catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * restituisce la lista attivita.
     *
     * @return l'arraylist
     */
    public ArrayList<Attivita> getListaAttivita(){
        try{
            AttivitaDAO attivitaDAO = new AttivitaImplementazionePostgresDAO();
            return attivitaDAO.getAttivita(idToDoCorrente);
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    public ArrayList<Attivita> getListaAttivitaLocale(){
        return getToDo().getChecklistAttivita();
    }

    public void setListaAttivitaLocale(ArrayList<Attivita> lista){
        getToDo().setChecklistAttivita(lista);
    }

    public void setStatoLocale(String nome){
        getToDo().setStatoAttivita(nome);
    }

    /**
     * restituisce true se tutti i ToDo sono completati.
     *
     * @return un boolean
     */
    public boolean checkChecklist(){
        return getToDo().checkChecklist();
    }

    public void aggiornaChecklist(ArrayList<Attivita> checklist) throws SQLException{
        AttivitaDAO attivitaDAO = new AttivitaImplementazionePostgresDAO();
        for (Attivita a : checklist)
            attivitaDAO.setStato(a.isStato(), a.getNome(), idToDoCorrente);

    }

    public Attivita getAttivita(String nome){
        return getToDo().cercaAttivita(nome);
    }

    public int condividiToDo(String utenteDest){
        try{
            CondividiToDoDAO condividiToDoDAO = new CondividiToDoImplementazionePostgresDAO();
            return condividiToDoDAO.aggiungiCondivisione(utenteCorrente.getUsername(), utenteDest, idToDoCorrente);
        }
        catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public int rimuoviCondivisione(String utenteDest){
        try{
            CondividiToDoDAO condividiToDoDAO = new CondividiToDoImplementazionePostgresDAO();
            return condividiToDoDAO.rimuoviCondivisione(utenteCorrente.getUsername(), utenteDest, idToDoCorrente);
        }
        catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }

    public ArrayList<String> getListaCondivisioni (){
        try{
            CondividiToDoDAO condividiToDoDAO = new CondividiToDoImplementazionePostgresDAO();
            return condividiToDoDAO.getListaCondivisioni(idToDoCorrente);
        }
        catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean checkUtente(String utenteDest) {
        try{
            UtenteDAO utenteDAO = new UtenteImplementazionePostgresDAO();
            int r = utenteDAO.getUtente(utenteDest);
            return r == 0;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
