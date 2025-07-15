package dao;

import model.Bacheca;

import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione degli Utenti
 */
public interface UtenteDAO {

    /**
     * Inserisce un nuovo utente nel database con username e password.
     *
     * @param username il nome utente da inserire.
     * @param password la password associata all’utente.
     * @return un intero indicante l'esito dell'operazione (0 buon fine, -1 errore).
     */
    int inserisciUtenteDB(String username, String password);

    /**
     * Effettua il login controllando username e password nel database.
     *
     * @param username il nome utente per il login.
     * @param password la password associata.
     * @return un intero indicante l'esito dell'operazione (0 buon fine, -1 errore).
     */
    int loginUtenteDB(String username, String password);

    /**
     * Recupera la lista delle bacheche associate a un utente.
     *
     * @param username il nome utente di cui ottenere le bacheche.
     * @return una lista di oggetti Bacheca appartenenti all’utente.
     */
    ArrayList<Bacheca> getBachecheUtenteDB(String username);

    /**
     * Verifica l’esistenza di un utente nel database.
     *
     * @param username il nome utente da controllare.
     * @return 0 se l’utente esiste, -1 se non esiste.
     */
    int getUtente(String username);
}

