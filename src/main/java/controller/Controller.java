package controller;

import dao.*;
import implementazioniPostgresDAO.*;
import model.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
    private ArrayList<Attivita> listaAttIns;
    private ArrayList<Attivita> listaAttDel;
    boolean usatoIns;
    boolean usatoDel;


    /**
     * Costruttore vuoto della classe Controller.
     * Istanzia un controller.
     */
    public Controller(){
        //il costruttore non ha bisogno di inizializzare nulla.
    }

    /**
     * Effettua un logout logico nel controller, resettando tutti i valori dell'utente.
     */
    public void logout(){
        utenteCorrente = null;
        titoloBachecaCorrente = null;
        idToDoCorrente = -1;
    }

    /**
     * Crea un nuovo utente nel database con le credenziali specificate.
     *
     * @param username il nome utente da registrare
     * @param password la password associata al nuovo utente
     * @return l'ID dell'utente appena creato se l'inserimento ha successo,
     *         altrimenti -1 in caso di errore
     */
    public int creaUtente(String username, String password){
        try{
            UtenteDAO u = new UtenteImplementazionePostgresDAO();
            return u.inserisciUtenteDB(username, password);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Restituisce la lista locale dei ToDo della bacheca corrente dell'utente.
     *
     * @return una lista di oggetti ToDo associati alla bacheca corrente
     */
    public ArrayList<ToDo> getListaToDoLocale() {
        return utenteCorrente.getBacheca(titoloBachecaCorrente).getListaToDo();
    }

    /**
     * Imposta l'ID del ToDo corrente selezionato.
     *
     * @param idToDoCorrente l'identificatore intero da assegnare al ToDo corrente
     */
    public void setIdToDoCorrente(int idToDoCorrente) {
        this.idToDoCorrente = idToDoCorrente;
    }


    /**
     * Esegue il login dell'utente con le credenziali fornite.
     *
     * @param username la username dell'utente
     * @param password la password associata all'utente
     * @return true se il login ha successo, false altrimenti
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
        }catch (Exception _){
            return false;
        }
    }

    /**
     * Controlla se tutte le bacheche esistono.
     *
     * @return vero se si possono creare altre bacheche, falso altrimenti.
     */
    public boolean checkBacheche(){
        return utenteCorrente.puoAggiungereBacheca();
    }

    /**
     * Crea bacheca una nuova bacheca.
     *
     * @param t il titolo della nuova bacheca, unico per l'utente
     * @param d la descrizione della bacheca
     * @throws Exception nel caso in cui la creazione non vada a buon fine.
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

    /**
     * Carica tutte le bacheche associate all'utente corrente dal database
     * e le inserisce nella struttura dati locale dell'utente.
     *
     * @throws Exception se si verifica un errore durante l'accesso al database
     */
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
     * Crea un nuovo ToDo nella bacheca corrente sia nel database che nella struttura dati locale.
     *
     * @param titoloToDo il titolo del ToDo da creare
     * @return l'ID del ToDo appena creato se l'operazione va a buon fine, -1 in caso di errore
     */
    public int creaToDo(String titoloToDo)
    {
        try{
            ToDoDAO toDoDAO = new ToDoImplementazionePostgresDAO();
            int r = toDoDAO.creaToDo(titoloToDo, titoloBachecaCorrente, utenteCorrente.getUsername());
            if (r!=-1)
                utenteCorrente.getBacheca(titoloBachecaCorrente).creaToDoGUI(titoloToDo);
            return r;
        }catch (Exception _){
            return -1;
        }
    }

    /**
     * Aggiorna nel database lo stato del ToDo corrente con le informazioni presenti localmente.
     *
     * @throws SQLException se si verifica un errore durante l'aggiornamento nel database
     */
    public void aggiornaToDo() throws SQLException {
        ToDoDAO toDoDAO = new ToDoImplementazionePostgresDAO();
        toDoDAO.aggiornaToDo(getToDo());
    }

    /**
     * Recupera dal database l'istanza aggiornata del ToDo corrente.
     *
     * @return l'oggetto {@code ToDo} corrispondente all'ID corrente, oppure {@code null} in caso di errore durante l'accesso al database.
     */
    public ToDo getToDoDB() {
        try{
            ToDoDAO toDoDAO = new ToDoImplementazionePostgresDAO();
            return toDoDAO.getToDo(idToDoCorrente);
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Aggiorna nella lista locale il ToDo corrente con la versione aggiornata dal database.
     */
    public void rimpiazzaToDoLocale(){
        ArrayList<ToDo> lista = getListaToDoLocale();
        for (int i = 0; i < lista.size(); i++){
            ToDo t = lista.get(i);
            if (t.getIdToDo()==idToDoCorrente){
                ToDo todo = getToDoDB();
                lista.set(i, todo);
                ArrayList<Attivita> listaAtt = getListaAttivita();
                setListaAttivitaLocale(listaAtt);
                return;
            }
        }
    }

    /**
     * Restituisce il ToDo corrente selezionato nella bacheca corrente.
     *
     * @return l'oggetto {@code ToDo} corrispondente all'ID selezionato
     */
    public ToDo getToDo (){
        return utenteCorrente.getBacheca(titoloBachecaCorrente).getToDoId(idToDoCorrente);
    }

    /**
     * Elimina il todo sia dal database che dalla struttura locale.
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
     * Cambia il titolo del todo selezionato.
     *
     * @param t il nuovo titolo.
     */
    public void cambiaTitoloToDo(String t)
    {
        getToDo().setTitolo(t);
    }

    /**
     * Cambia la descrizione del todo selezionato.
     *
     * @param s la nuova descrizione.
     */
    public void cambiaDescToDo(String s){
        getToDo().setDescrizione(s);
    }

    /**
     * Cambia l'URL del todo selezionato.
     *
     * @param s il nuovo URL.
     */
    public void cambiaURLToDo(String s){
        getToDo().setUrl(s);
    }

    /**
     * Cambia la data di scadenza del todo selezionato.
     *
     * @param s la nuova data di scadenza.
     * @return {@code true} se l'operazione ha avuto successo, {@code false} in caso di eccezione.
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
     * Verifica se la data fornita non è antecedente a quella odierna.
     *
     * @param s la data da controllare.
     * @return {@code true} se la data è oggi o nel futuro, {@code false} se è nel passato
     */
    public boolean checkData(String s){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dataScadenza = LocalDate.parse(s, formatter);
        LocalDate dataOggi = LocalDate.now();
        return !dataScadenza.isBefore(dataOggi);
    }

    /**
     * Cambia il colore di sfondo del todo selezionato.
     *
     * @param c il nuovo colore di sfondo del todo corrente.
     */
    public void cambiaBgColorToDo(Color c){
        getToDo().setColoreSfondo(c);
    }

    /**
     * Restituisce il colore di sfondo del ToDo selezionato.
     *
     * @return un oggetto {@code Color} che rappresenta il colore di sfondo del ToDo corrente.
     */
    public Color getColorBG(){
        return this.getToDo().getColoreSfondo();
    }

    /**
     * Sposta il ToDo corrente in un'altra bacheca dell'utente.
     * Questo metodo aggiorna la posizione del ToDo nel database e nella struttura dati locale.
     *
     * @param nuova il titolo della bacheca di destinazione
     * @return {@code true} se lo spostamento è avvenuto con successo, {@code false} altrimenti.
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
     * Ordina i todo alfabeticamente solo nella struttura dati locale.
     */
    public void ordinaToDoAlfabeticamente() {
        Bacheca b = utenteCorrente.getBacheca(titoloBachecaCorrente);
        b.ordinaToDoTitolo();
    }

    /**
     * Ordina i ToDo della bacheca corrente in base alla data di scadenza,
     * dal più prossimo al più lontano, solo nella struttura dati locale.
     */
    public void ordinaToDoPerScadenza() {
        Bacheca b = utenteCorrente.getBacheca(titoloBachecaCorrente);
        b.ordinaToDoDataScad();
    }

    /**
     * Restituisce la lista dei ToDo della bacheca corrente che scadono oggi.
     *
     * @return una lista di oggetti {@code ToDo} con data di scadenza uguale a quella odierna.
     * Se non ci sono ToDo, restituisce una lista vuota.
     */
    public ArrayList<ToDo> getToDoScadenzaOggi(){
        if (utenteCorrente.getBacheca(titoloBachecaCorrente).getListaToDo()!=null){
            Bacheca b = utenteCorrente.getBacheca(titoloBachecaCorrente);
            return b.getToDoScadenzaOggi();
        }
        return new ArrayList<>();
    }

    /**
     * Restituisce la lista di ToDo con scadenza fissata alla data specificata.
     *
     * @param data la data di scadenza per filtrare i ToDo
     * @return una lista di ToDo con scadenza fissata alla data specificata
     */
    public ArrayList<ToDo> getToDoScadenzaFissa(LocalDate data){
        Bacheca b = utenteCorrente.getBacheca(titoloBachecaCorrente);
        return b.getToDoScadenzaFissata(data);
    }

    /**
     * Carica dal db e restituisce la lista di ToDo della bacheca corrente, inclusi quelli condivisi.
     *
     * @return una lista di ToDo della bacheca corrente, o una lista vuota in caso di errore.
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

    /**
     * Aggiunge un ToDo alla lista dei ToDo della bacheca corrente.
     *
     * @param t il ToDo da aggiungere.
     */
    public void setListToDo(ToDo t){
        utenteCorrente.getBacheca(titoloBachecaCorrente).aggiungiToDo(t);
    }

    /**
     * Pulisce la lista dei ToDo della bacheca corrente, rimuovendo tutti i ToDo.
     */
    public void pulisciListaToDo(){
        utenteCorrente.getBacheca(titoloBachecaCorrente).getListaToDo().clear();
    }

    /**
     * Restituisce il titolo del todo selezionato
     *
     * @return il titolo del todo corrente
     */
    public String getTitoloToDoCorrente() {
        return titoloToDoCorrente;
    }

    /**
     * Modifica il titolo del todo selezionato.
     *
     * @param t il nuovo titolo
     */
    public void setTitoloToDoCorrente(String t) {
        this.titoloToDoCorrente = t;
    }

    /**
     * Restituisce l'utente corrente, ovvero l'utente collegato al sistema.
     *
     * @return l'oggetto utente.
     */
    public Utente getUtente() {
        return utenteCorrente;
    }

    /**
     * Restituisce il titolo della bacheca corrente.
     *
     * @return il titolo della bacheca
     */
    public String getTitoloBacheca() {
        return titoloBachecaCorrente;
    }

    /**
     * Modifica lo stato del todo
     *
     * @param b lo stato nuovo.
     */
    public void setCompletoToDo(boolean b){
        getToDo().setStato(b);
    }

    /**
     * Restituisce lo stato del todo selezionato
     *
     * @return lo stato del todo corrente
     */
    public boolean getCompletoToDo(){
        return getToDo().getStato();
    }

    /**
     * Restituisce la data di scadenza del ToDo selezionato
     *
     * @return la data di scadenza formattata, se assente restituisce stringa vuota.
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
     * Restituisce la descrizione del ToDo selezionato.
     *
     * @return la descrizione del ToDo corrente.
     */
    public String getDescrizioneToDo(){
        String s = getToDo().getDescrizione();
        if (s!=null)
            return s;
        else
            return "";
    }

    /**
     * Restituisce l'URL del ToDo selezionato.
     *
     * @return 'URL del ToDo corrente.
     */
    public String getUrlToDo(){
        String s = getToDo().getUrl();
        if (s!=null)
            return s;
        else
            return "";
    }

    /**
     * Restituisce il colore del ToDo selezionato.
     *
     * @return Il colore del ToDo corrente.
     */
    public Color getBGColorToDo(){
        return getToDo().getColoreSfondo();
    }

    /**
     * Modifica il titolo della bacheca corrente.
     *
     * @param titoloBacheca il titolo della bacheca.
     */
    public void setTitoloBacheca(String titoloBacheca) {
        this.titoloBachecaCorrente = titoloBacheca;
    }

    /**
     * Modifica l'immagine associata al ToDo corrente.
     *
     * @param imageBytes array di byte che rappresenta l'immagine da assegnare al ToDo.
     */
    public void setIMGToDo(byte[] imageBytes) {
        getToDo().setImmagine(imageBytes);
    }

    /**
     * Restituisce l'immagine del ToDo corrente come oggetto {@link Image}.
     *
     * @return l'immagine convertita da byte array a {@code Image}, oppure {@code null} se l'immagine non è presente
     *         o se si verifica un errore durante la conversione.
     */
    public Image getIMGToDo() {
        byte[] imgBytes = getToDo().getImmagine();
        if (imgBytes == null)
            return null;
        try {
            InputStream is = new ByteArrayInputStream(imgBytes);
            return ImageIO.read(is);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Controlla se esiste una bacheca con il nome specificato per l'utente corrente.
     *
     * @param nomeBacheca il nome della bacheca da verificare.
     * @return true se la bacheca esiste, false altrimenti.
     */
    public boolean checkBacheca (String nomeBacheca){
        return utenteCorrente.getBacheca(nomeBacheca) != null;
    }

    /**
     * Elimina una bacheca sia dal database che in locale per l'utente corrente.
     *
     * @param titolo il titolo della bacheca da eliminare
     * @return true se l'eliminazione è avvenuta con successo, false altrimenti
     */
    public boolean eliminaBacheca (TitoloBacheca titolo){
        boolean b = utenteCorrente.eliminaBachecaGUI(titolo.toString());

        try {
            BachecaDAO bachecaDAO = new BachecaImplementazionePostgresDAO();
            int r = bachecaDAO.eliminaBacheca(titolo, utenteCorrente.getUsername());
            boolean b1 = r==0;
            return (b && b1);
        }
        catch (Exception _){
            return false;
        }
    }

    /**
     * Cambia la descrizione di una bacheca dell'utente corrente e aggiorna il database.
     *
     * @param titolo il titolo della bacheca da modificare
     * @param descrizione la nuova descrizione da impostare
     * @return true se l'aggiornamento nel database è andato a buon fine, false altrimenti
     */
    public boolean cambiaDescrizioneBacheca (TitoloBacheca titolo, String descrizione){
        utenteCorrente.getBacheca(titolo.toString()).setDescrizione(descrizione);
        try {
            BachecaDAO bachecaDAO = new BachecaImplementazionePostgresDAO();
            int r = bachecaDAO.aggiornaDescrizioneBacheca(titolo, descrizione, utenteCorrente.getUsername());
            return r==0;
        }
        catch (Exception _){
            return false;
        }
    }

    /**
     * Restituisce la descrizione della bacheca.
     *
     * @param t Il titolo della bacheca
     * @return La descrizione
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
     * Controlla se l'utente corrente non ha nessuna delle bacheche predefinite: LAVORO, TEMPO_LIBERO, UNIVERSITA.
     *
     * @return true se nessuna delle bacheche predefinite è presente, false altrimenti
     */
    public boolean noBacheca(){
        return utenteCorrente.getBacheca(TitoloBacheca.LAVORO.toString()) == null
                && utenteCorrente.getBacheca(TitoloBacheca.TEMPO_LIBERO.toString()) == null
                && utenteCorrente.getBacheca(TitoloBacheca.UNIVERSITA.toString()) == null;
    }

    /**
     * Crea una nuova attività in locale associata al ToDo corrente.
     *
     * @param titoloAttivita il titolo della nuova attività da creare
     */
    public void creaAttivitaLocale(String titoloAttivita) {
        getToDo().aggiuntiAttivita(titoloAttivita);
    }

    /**
     * Crea nuove attività nel db associate al ToDo corrente.
     *
     * @return 0 se l'inserimento ha avuto successo, -1 in caso di errore
     */
    public int creaAttivitaDB(){
        int rs = -1;

        if (listaAttIns==null || !usatoIns)
            return 0;

        for (Attivita a : listaAttIns) {
            try {
                AttivitaDAO attivitaDAO = new AttivitaImplementazionePostgresDAO();
                rs = attivitaDAO.creaAttivita(idToDoCorrente, a.getNome());
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        usatoIns = false;
        listaAttIns = null;
        return rs;
    }

    /**
     * Elimina un'attività dal ToDo corrente in locale.
     *
     * @param nome il nome dell'attività da eliminare
     */
    public void eliminaAttivitaLocale(String nome){
        getToDo().rimuoviAttivita(nome);
    }

    /**
     * Elimina le attività dal ToDo corrente dal database.
     *
     * @return 0 se l'eliminazione ha avuto successo, -1 in caso di errore
     */
    public int eliminaAttivitaDB(){
        int rs = -1;

        if (listaAttDel==null || !usatoDel)
            return 0;

        for (Attivita a : listaAttDel) {
            try {
                AttivitaDAO attivitaDAO = new AttivitaImplementazionePostgresDAO();
                rs = attivitaDAO.eliminaAttivita(a.getNome(), idToDoCorrente);
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }
        }
        usatoDel = false;
        listaAttDel = null;
        return rs;
    }

    /**
     * Aggiunge un'attività all'array locale listaAttIns con le attività da creare che verrà poi sincronizzato col db.
     * @param nome nome dell'attività da creare
     */
    public void aggiungiAttIns(String nome){
        usatoIns = true;
        if (listaAttIns == null)
            listaAttIns = new ArrayList<>();
        Attivita a = new Attivita(nome);
        listaAttIns.add(a);
    }

    /**
     * Aggiunge un'attività all'array locale listaAttDel con le attività da eliminare che verrà poi sincronizzato col db.
     * @param nome nome dell'attività da eliminare
     */
    public void aggiungiAttDel(String nome){
        usatoDel = true;
        if (listaAttDel == null)
            listaAttDel = new ArrayList<>();
        Attivita a = new Attivita(nome);
        listaAttDel.add(a);
    }

    /**
     * Recupera la lista delle attività associate al ToDo corrente dal database.
     *
     * @return lista di oggetti Attività; lista vuota in caso di errore
     */
    public ArrayList<Attivita> getListaAttivita(){
        try{
            AttivitaDAO attivitaDAO = new AttivitaImplementazionePostgresDAO();
            return attivitaDAO.getAttivita(idToDoCorrente);
        }
        catch (Exception _){
            return new ArrayList<>();
        }
    }

    /**
     * Recupera la lista delle attività memorizzate in locale nel ToDo corrente.
     *
     * @return lista locale di oggetti Attività.
     */
    public ArrayList<Attivita> getListaAttivitaLocale(){
        return getToDo().getChecklistAttivita();
    }

    /**
     * Imposta la lista locale delle attività nel ToDo corrente.
     *
     * @param lista lista di attività da impostare localmente
     */
    public void setListaAttivitaLocale(ArrayList<Attivita> lista){
        getToDo().setChecklistAttivita(lista);
    }

    /**
     * Imposta lo stato di una specifica attività nel ToDo corrente.
     *
     * @param nome nome dell'attività di cui modificare lo stato
     */
    public void setStatoLocale(String nome, boolean b){
        getToDo().setStatoAttivita(nome, b);
    }

    /**
     * Aggiorna lo stato delle attività nella checklist nel database.
     *
     * @param checklist lista di attività con gli stati aggiornati
     * @throws SQLException se si verifica un errore durante l'accesso al database
     */
    public void aggiornaChecklist(ArrayList<Attivita> checklist) throws SQLException{
        AttivitaDAO attivitaDAO = new AttivitaImplementazionePostgresDAO();
        for (Attivita a : checklist)
            attivitaDAO.setStato(a.isStato(), a.getNome(), idToDoCorrente);

    }

    /**
     * Recupera un'attività dal ToDo corrente in base al nome.
     *
     * @param nome nome dell'attività da cercare
     * @return oggetto Attività se trovato, null altrimenti
     */
    public Attivita getAttivita(String nome){
        return getToDo().cercaAttivita(nome);
    }

    /**
     * Condivide il ToDo corrente con un altro utente.
     *
     * @param utenteDest nome dell'utente destinatario della condivisione
     * @return 0 se la condivisione è avvenuta con successo, -1 in caso di errore
     */
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

    /**
     * Rimuove la condivisione del ToDo corrente con un utente.
     *
     * @param utenteDest nome dell'utente destinatario da rimuovere dalla condivisione
     * @return 0 se la rimozione ha avuto successo, -1 in caso di errore
     */
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

    /**
     * Restituisce la lista degli utenti con cui è condiviso il ToDo corrente.
     *
     * @return lista di nomi utenti destinatari della condivisione; lista vuota in caso di errore
     */
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

    /**
     * Verifica se un utente esiste nel sistema.
     *
     * @param utenteDest nome dell'utente da verificare
     * @return true se l'utente esiste, false altrimenti
     */
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
