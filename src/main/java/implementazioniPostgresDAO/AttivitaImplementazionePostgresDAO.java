package implementazioniPostgresDAO;

import dao.AttivitaDAO;
import database.ConnessioneDataBase;
import model.Attivita;

import java.sql.*;
import java.util.ArrayList;

/**
 * Implementazione DAO per la gestione delle attività su database PostgresSQL.
 */
public class AttivitaImplementazionePostgresDAO implements AttivitaDAO {
    private final Connection connection;

    /**
     * Crea una nuova istanza con connessione al database.
     * @throws SQLException se la connessione fallisce
     */
    public AttivitaImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDataBase.getInstance().getConnection();
    }

    /**
     * Inserisce una nuova attività nel database.
     * @param idToDo id del ToDo associato
     * @param nome nome dell'attività
     * @return 0 se successo, -1 altrimenti
     */
    @Override
    public int creaAttivita(int idToDo, String nome) {
        String query = "INSERT INTO attivita (nome, todo) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setInt(2, idToDo);
            return creaEliminaAttivita(stmt);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Recupera le attività associate a un ToDo.
     * @param idToDo ID del ToDo
     * @return lista di attività
     */
    @Override
    public ArrayList<Attivita> getAttivita(int idToDo) {
        String query = "SELECT nome, stato FROM attivita WHERE todo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idToDo);

            ResultSet r = stmt.executeQuery();
            ArrayList<Attivita> lista = new ArrayList<>();
            while (r.next()){
                Attivita a = new Attivita(r.getString("nome"));
                a.modificaStato(r.getBoolean("stato"));
                lista.add(a);
            }
            return lista;
        }
        catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Aggiorna lo stato di un'attività.
     * @param stato nuovo stato
     * @param nome nome dell'attività
     * @param idToDo ID del ToDo associato
     */
    @Override
    public void setStato(boolean stato, String nome, int idToDo) {
        String query = "UPDATE attivita SET stato = ? WHERE nome = ? AND todo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBoolean(1, stato);
            stmt.setString(2, nome);
            stmt.setInt(3, idToDo);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Elimina un'attività dal database.
     * @param nome nome dell'attività
     * @param idToDo ID del ToDo associato
     * @return 0 se successo, -1 altrimenti
     */
    @Override
    public int eliminaAttivita(String nome, int idToDo) {
        String query = "DELETE FROM attivita WHERE nome = ?  AND todo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setInt(2, idToDo);
            return creaEliminaAttivita(stmt);
        }
        catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * Esegue l'inserimento o l'eliminazione dell'attività.
     * @param stmt statement SQL già preparato
     * @return 0 se successo, -1 altrimenti
     * @throws SQLException se la query fallisce
     */
    public int creaEliminaAttivita(PreparedStatement stmt) throws SQLException {
        int r = stmt.executeUpdate();
        if (r!=0)
            return 0;
        else
            return -1;
    }
}
