package dao;

import model.TitoloBacheca;

import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione delle bacheche associate a un utente.
 */
public interface BachecaDAO {

    /**
     * Recupera la lista dei titoli delle bacheche di un utente specifico.
     *
     * @param username il nome dell'utente di cui si vogliono ottenere i titoli delle bacheche.
     * @return una lista di stringhe contenente i titoli delle bacheche dell'utente.
     */
    ArrayList<String> getTitoliUtente(String username);

    /**
     * Crea una nuova bacheca per un utente.
     *
     * @param titolo il titolo della bacheca da creare (di tipo {@link TitoloBacheca}).
     * @param descrizione la descrizione associata alla nuova bacheca.
     * @param username il nome utente proprietario della bacheca.
     * @return un intero indicante l'esito dell'operazione (0 buon fine, -1 errore)
     */
    int creaBacheca(TitoloBacheca titolo, String descrizione, String username);

    /**
     * Elimina una bacheca esistente di un utente.
     *
     * @param titolo il titolo della bacheca da eliminare (di tipo {@link TitoloBacheca}).
     * @param username il nome utente proprietario della bacheca.
     * @return un intero indicante l'esito dell'operazione (0 buon fine, -1 errore)
     */
    int eliminaBacheca(TitoloBacheca titolo, String username);

    /**
     * Aggiorna la descrizione di una bacheca esistente per un utente.
     *
     * @param titolo il titolo della bacheca da aggiornare (di tipo {@link TitoloBacheca}).
     * @param descrizione la nuova descrizione da assegnare alla bacheca.
     * @param username il nome utente proprietario della bacheca.
     * @return un intero indicante l'esito dell'operazione (0 buon fine, -1 errore)
     */
    int aggiornaDescrizioneBacheca(TitoloBacheca titolo, String descrizione, String username);
}

