package dao;

import model.Attivita;

import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione delle operazioni su Attività associate a un ToDo.
 */
public interface AttivitaDAO {

    /**
     * Crea una nuova attività associata a un ToDo specificato.
     *
     * @param idToDo l'identificatore del ToDo a cui associare l'attività.
     * @param nome il nome della nuova attività da creare.
     * @return un intero indicante l'esito dell'operazione (0 buon fine, -1 errore)
     */
    int creaAttivita(int idToDo, String nome);

    /**
     * Recupera tutte le attività associate a un determinato ToDo.
     *
     * @param idToDo l'identificatore del ToDo di cui si vogliono ottenere le attività.
     * @return una lista di oggetti {@link Attivita} associati al ToDo specificato.
     */
    ArrayList<Attivita> getAttivita(int idToDo);

    /**
     * Modifica lo stato di una specifica attività associata a un ToDo.
     *
     * @param stato il nuovo stato dell'attività (true se completata, false altrimenti).
     * @param nome il nome dell'attività da aggiornare.
     * @param idToDo l'identificatore del ToDo a cui appartiene l'attività.
     */
    void setStato(boolean stato, String nome, int idToDo);

    /**
     * Elimina una specifica attività associata a un ToDo.
     *
     * @param nome il nome dell'attività da eliminare.
     * @param idToDo l'identificatore del ToDo a cui appartiene l'attività.
     * @return un intero indicante l'esito dell'operazione (0 buon fine, -1 errore)
     */
    int eliminaAttivita(String nome, int idToDo);
}
