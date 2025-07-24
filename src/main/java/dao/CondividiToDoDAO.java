package dao;

import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione delle condivisioni dei ToDo tra utenti.
 */
public interface CondividiToDoDAO {

    /**
     * Aggiunge una condivisione di un ToDo da un utente mittente a un utente destinatario.
     *
     * @param utenteMitt il nome utente mittente (proprietario del ToDo).
     * @param utenteDest il nome utente destinatario con cui condividere il ToDo.
     * @param idToDo l'identificativo del ToDo da condividere.
     * @return un intero indicante l'esito dell'operazione (0 buon fine, -1 errore).
     */
    int aggiungiCondivisione(String utenteMitt, String utenteDest, int idToDo);

    /**
     * Rimuove una condivisione di un ToDo tra un utente mittente e un utente destinatario.
     *
     * @param utenteMitt il nome utente mittente (proprietario del ToDo).
     * @param utenteDest il nome utente destinatario da cui rimuovere la condivisione.
     * @param idToDo l'identificativo del ToDo per cui rimuovere la condivisione.
     * @return un intero indicante l'esito dell'operazione (0 buon fine, -1 errore).
     */
    int rimuoviCondivisione(String utenteMitt, String utenteDest, int idToDo);

    /**
     * Restituisce la lista degli utenti destinatari con cui un ToDo è condiviso.
     *
     * @param idToDo l'identificativo del ToDo di cui si vuole conoscere la lista di condivisione.
     * @return una lista di stringhe contenente i nomi utente destinatari del ToDo condiviso.
     */
    ArrayList<String> getListaCondivisioni(int idToDo);
}
