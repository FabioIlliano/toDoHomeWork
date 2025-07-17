package implementazioniPostgresDAO;

import dao.CondividiToDoDAO;
import database.ConnessioneDataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementazione DAO per la gestione delle condivisioni dei ToDo su database PostgresSQL.
 */
public class CondividiToDoImplementazionePostgresDAO implements CondividiToDoDAO {

    private final Connection connection;

    /**
     * Inizializza la connessione al database.
     * @throws SQLException se la connessione fallisce
     */
    public CondividiToDoImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDataBase.getInstance().getConnection();
    }

    /**
     * Aggiunge una condivisione di un ToDo tra due utenti.
     * @param utenteMitt utente mittente
     * @param utenteDest utente destinatario
     * @param idToDo id del ToDo condiviso
     * @return 0 se successo, -1 altrimenti
     */
    @Override
    public int aggiungiCondivisione(String utenteMitt, String utenteDest, int idToDo){
        String query = "INSERT INTO condividitodo values (?, ?, ?)";

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, utenteMitt);
            stmt.setString(2, utenteDest);
            stmt.setInt(3, idToDo);

            int r = stmt.executeUpdate();
            if (r != 0)
                return 0;
            else
                return -1;
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Rimuove la condivisione di un ToDo tra due utenti.
     * @param utenteMitt utente mittente
     * @param utenteDest utente destinatario
     * @param idToDo id del ToDo condiviso
     * @return 0 se successo, -1 altrimenti
     */
    @Override
    public int rimuoviCondivisione(String utenteMitt, String utenteDest, int idToDo) {
        String query = "DELETE FROM condividitodo WHERE utentemittente = ? AND utentedest = ? AND todocondiviso = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, utenteMitt);
            stmt.setString(2, utenteDest);
            stmt.setInt(3, idToDo);

            int r = stmt.executeUpdate();
            if (r!=0)
                return 0;
            else
                return -1;
        }
        catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * Restituisce la lista degli utenti con cui è condiviso un ToDo
     * @param idToDo id del ToDo
     * @return lista di username destinatari
     */
    public ArrayList<String> getListaCondivisioni(int idToDo){
        String query = "SELECT utentedest FROM condividitodo WHERE todocondiviso = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, idToDo);

            ArrayList<String> listaUtenti = new ArrayList<>();
            ResultSet r = stmt.executeQuery();
            while (r.next()){
                String username = r.getString("utentedest");
                listaUtenti.add(username);
            }
            return listaUtenti;

        }
        catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
