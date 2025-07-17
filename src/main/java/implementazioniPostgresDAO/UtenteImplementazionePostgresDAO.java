package implementazioniPostgresDAO;

import dao.UtenteDAO;
import database.ConnessioneDataBase;
import model.Bacheca;
import model.TitoloBacheca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implementazione DAO per la gestione degli utenti nel database PostgresSQL.
 */
public class UtenteImplementazionePostgresDAO implements UtenteDAO {
    private final Connection connection;

    /**
     * Inizializza la connessione al database.
     * @throws SQLException se la connessione fallisce
     */
    public UtenteImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDataBase.getInstance().getConnection();
    }

    /**
     * Inserisce un nuovo utente nel database.
     * @param username nome utente
     * @param password password dell'utente
     * @return 0 se inserimento riuscito, 1 se username già esistente, -1 in caso di errore
     */
    @Override
    public int inserisciUtenteDB (String username, String password){
        String query = "INSERT INTO utente VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            int r = stmt.executeUpdate();
            if (r>0)
                return 0;
            else
                return -1;
        }
        catch (SQLException e){
            if ("23505".equals(e.getSQLState())) {
                // errore di chiave primaria non rispettata
                e.printStackTrace();
                return 1;
            } else {
                e.printStackTrace();
                return -1;
            }
        }

    }

    /**
     * Effettua il login controllando username e password nel database.
     * @param username nome utente
     * @param password password dell'utente
     * @return 0 se login corretto, -1 se login fallito o errore
     */
    @Override
    public int loginUtenteDB (String username, String password){
        String query = "SELECT username FROM utente WHERE username = ? AND password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet r = stmt.executeQuery();
            if (r.next())
                return 0;
            else
                return -1;
        }
        catch (SQLException _){
            return -1;
        }
    }

    /**
     * Restituisce la lista delle bacheche associate a un utente.
     * @param username nome utente
     * @return lista di oggetti Bacheca, o lista vuota in caso di errore
     */
    @Override
    public ArrayList<Bacheca> getBachecheUtenteDB(String username) {
        String query = "SELECT titolo, descrizione FROM bacheca WHERE utente = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)){
            ArrayList<Bacheca> b = new ArrayList<>();
            stmt.setString(1, username);
            ResultSet r = stmt.executeQuery();
            while (r.next()){
                Bacheca bacheca = new Bacheca( TitoloBacheca.valueOf (r.getString("titolo")), (r.getString("descrizione")));
                b.add(bacheca);
            }
            return b;
        }
        catch (Exception _){
            return new ArrayList<>();
        }
    }

    /**
     * Controlla se un utente esiste nel database.
     * @param username nome utente da cercare
     * @return 0 se l'utente esiste, -1 se non esiste o errore
     */
    @Override
    public int getUtente(String username) {
        String query = "SELECT 1 FROM utente WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, username);
            ResultSet r = stmt.executeQuery();
            if (r.next())
                return 0;
            else
                return -1;
        }
        catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
}
