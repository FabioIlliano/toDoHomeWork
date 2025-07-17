package dao;

import model.ToDo;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Interfaccia DAO per la gestione delle operazioni CRUD sui ToDo.
 */
public interface ToDoDAO {

    /**
     * Definizione costanti utilizzate nella classe di implementazione.
     */
    String ID = "idtodo";
    String DESC = "descrizione";
    String TITOLO = "titolo";
    String BGCOLOR = "coloresfondo";
    String STATO = "stato";
    String DATASCAD = "datascadenza";
    String IMG = "immagine";
    String URL = "url";


    /**
     * Carica la lista dei ToDo associati a una specifica bacheca e utente proprietario.
     *
     * @param bacheca il titolo della bacheca di appartenenza dei ToDo.
     * @param username il nome utente proprietario dei ToDo.
     * @return una lista di oggetti ToDo relativi alla bacheca e all’utente specificati.
     */
    ArrayList<ToDo> caricaDatiToDo(String bacheca, String username);

    /**
     * Carica la lista dei ToDo condivisi con l’utente specificato in una determinata bacheca.
     *
     * @param bacheca il titolo della bacheca di appartenenza dei ToDo condivisi.
     * @param username il nome utente destinatario delle condivisioni.
     * @return una lista di oggetti ToDo condivisi con l’utente nella bacheca indicata.
     */
    ArrayList<ToDo> caricaDatiToDoCondivisi(String bacheca, String username);

    /**
     * Crea un nuovo ToDo con titolo
     *
     * @param titolo il titolo del nuovo ToDo.
     * @param bacheca il titolo della bacheca a cui associare il ToDo.
     * @param username il nome utente proprietario del ToDo.
     * @return un intero che indica l'id generato o che vale -1 in caso di errore.
     */
    int creaToDo(String titolo, String bacheca, String username);

    /**
     * Aggiorna i dati di un ToDo esistente.
     *
     * @param t l’oggetto ToDo con i dati aggiornati.
     * @throws SQLException se si verifica un errore durante l’aggiornamento nel database.
     */
    void aggiornaToDo(ToDo t) throws SQLException;

    /**
     * Elimina un ToDo dato il suo identificativo.
     *
     * @param idToDo l’identificativo del ToDo da eliminare.
     */
    void eliminaToDo(int idToDo);

    /**
     * Sposta un ToDo da una bacheca a un’altra per un dato utente.
     *
     * @param idToDo l’identificativo del ToDo da spostare.
     * @param bacheca la nuova bacheca di destinazione.
     * @param username il nome utente proprietario del ToDo.
     * @return un intero indicante l'esito dell'operazione (0 buon fine, -1 errore)
     */
    int spostaToDo(int idToDo, String bacheca, String username);

    /**
     * Recupera un ToDo dal database dato il suo identificativo.
     *
     * @param idToDo l’identificativo del ToDo da recuperare.
     * @return l’oggetto ToDo corrispondente all’id fornito, oppure null se non trovato.
     */
    ToDo getToDo(int idToDo);
}

