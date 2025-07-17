package implementazioniPostgresDAO;

import dao.BachecaDAO;
import database.ConnessioneDataBase;
import model.TitoloBacheca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 * Implementazione DAO per la gestione delle bacheche su database PostgreSQL.
 */
public class BachecaImplementazionePostgresDAO implements BachecaDAO {
    private final Connection connection;

    /**
     * Crea una nuova istanza con connessione al database.
     * @throws SQLException se la connessione fallisce
     */
    public BachecaImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDataBase.getInstance().getConnection();
    }

    /**
     * Restituisce i titoli delle bacheche associate a un utente.
     * @param username nome utente
     * @return lista di titoli delle bacheche
     */
    @Override
    public ArrayList<String> getTitoliUtente (String username){
        String query = "SELECT titolo FROM bacheca WHERE utente = ?";
        ArrayList<String> a = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                a.add(rs.getString("titolo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return a;
    }

    /**
     * Crea una nuova bacheca per un utente.
     * @param titolo titolo della bacheca
     * @param descrizione descrizione della bacheca
     * @param username nome utente
     * @return 0 se successo, -1 altrimenti
     */
    @Override
    public int creaBacheca(TitoloBacheca titolo, String descrizione, String username) {
        String query = "INSERT INTO bacheca VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, titolo.toString());
            stmt.setString(2, descrizione);
            stmt.setString(3, username);
            int r = stmt.executeUpdate();
            if (r>0)
                return 0;
            else
                return -1;
        }
        catch (SQLException _){
            return -1;
        }
    }

    /**
     * Elimina una bacheca di un utente.
     * @param titolo titolo della bacheca
     * @param username nome utente
     * @return 0 se successo, -1 altrimenti
     */
    @Override
    public int eliminaBacheca(TitoloBacheca titolo, String username) {
        String query = "DELETE FROM bacheca WHERE titolo = ? AND utente = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, titolo.toString());
            stmt.setString(2, username);
            int r = stmt.executeUpdate();
            if (r>0)
                return 0;
            else
                return -1;
        }
        catch (SQLException _){
            return -1;
        }
    }

    /**
     * Aggiorna la descrizione di una bacheca.
     * @param titolo titolo della bacheca
     * @param descrizione nuova descrizione
     * @param username nome utente
     * @return 0 se successo, -1 altrimenti
     */
    @Override
    public int aggiornaDescrizioneBacheca(TitoloBacheca titolo, String descrizione, String username) {
        String query = "UPDATE bacheca SET descrizione = ? WHERE titolo = ? AND utente = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, descrizione);
            stmt.setString(2, titolo.toString());
            stmt.setString(3, username);
            int r = stmt.executeUpdate();
            if (r>0)
                return 0;
            else
                return -1;
        }
        catch (SQLException _){
            return -1;
        }
    }
}
