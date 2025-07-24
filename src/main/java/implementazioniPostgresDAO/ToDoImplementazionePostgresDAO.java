package implementazioniPostgresDAO;

import dao.ToDoDAO;
import database.ConnessioneDataBase;
import model.ToDo;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implementazione DAO per la gestione dei ToDo su database PostgresSQL
 */
public class ToDoImplementazionePostgresDAO implements ToDoDAO {
    private final Connection connection;

    /**
     * Inizializza la connessione al database.
     *
     * @throws SQLException se la connessione fallisce.
     */
    public ToDoImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDataBase.getInstance().getConnection();
    }


    /**
     * Carica la lista di ToDo appartenenti a un utente e una bacheca specifici.
     *
     * @param bacheca il titolo della bacheca.
     * @param username il nome utente proprietario dei ToDo.
     * @return lista di ToDo trovati, lista vuota in caso di errore.
     */
    @Override
    public ArrayList<ToDo> caricaDatiToDo(String bacheca, String username) {
        String query = "SELECT * " +
                       "FROM todo WHERE titolobacheca = ? AND proprietario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, bacheca);
            stmt.setString(2, username);
            ResultSet r = stmt.executeQuery();
            return (costruisciListaToDo(r));
        }
        catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Carica la lista di ToDo condivisi con un utente in una bacheca specifica.
     *
     * @param bacheca il titolo della bacheca.
     * @param username il nome utente destinatario della condivisione.
     * @return lista di ToDo condivisi, lista vuota in caso di errore.
     */
    @Override
    public ArrayList<ToDo> caricaDatiToDoCondivisi(String bacheca, String username) {
        String query = "SELECT t.* FROM todo t " +
                       "JOIN condividitodo c ON t.idtodo = c.todocondiviso " +
                       "WHERE c.utentedest = ? AND t.titolobacheca = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, username);
            stmt.setString(2, bacheca);
            ResultSet r = stmt.executeQuery();
            return (costruisciListaToDo(r));
        }
        catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Crea un nuovo ToDo con titolo, bacheca e proprietario specificati.
     *
     * @param titolo titolo del ToDo.
     * @param bacheca titolo della bacheca di appartenenza.
     * @param username proprietario del ToDo.
     * @return id generato del ToDo se inserito correttamente, -1 altrimenti.
     */
    @Override
    public int creaToDo(String titolo, String bacheca, String username) {
        String query = "INSERT INTO todo (titolo, titolobacheca, proprietario) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, titolo);
            stmt.setString(2, bacheca);
            stmt.setString(3, username);

            int r = stmt.executeUpdate();
            if (r > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); //id generato
                    }
                }
            }
            return -1; // Inserimento fallito
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Aggiorna i dati di un ToDo esistente.
     *
     * @param t oggetto ToDo contenente i dati aggiornati.
     *
     * @return 0 se aggiornamento riuscito, -1 altrimenti.
     */
    @Override
    public int aggiornaToDo(ToDo t){
        String query = "UPDATE todo SET titolo = ?, descrizione = ?, datascadenza = ?, url = ?, coloresfondo = ?, stato = ?, immagine = ? WHERE idtodo = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, t.getTitolo());

            if (t.getDescrizione().trim().isEmpty() || t.getDescrizione() == null)
                stmt.setNull(2, Types.VARCHAR);
            else
                stmt.setString(2, t.getDescrizione());

            if (t.getDataScadenza() != null)
                stmt.setDate(3, Date.valueOf(t.getDataScadenza()));
            else
                stmt.setNull(3, java.sql.Types.DATE);

            if (t.getUrl() == null || t.getUrl().trim().isEmpty())
                stmt.setNull(4, Types.VARCHAR);
            else
                stmt.setString(4, t.getUrl());

            Color colore = t.getColoreSfondo();
            String coloreString = null;
            if (colore != null)
                coloreString = String.format("#%02X%02X%02X", colore.getRed(), colore.getGreen(), colore.getBlue());
            stmt.setString(5, coloreString);
            stmt.setBoolean(6, t.getStato());

            byte[] imgBytes = t.getImmagine();
            if (imgBytes == null)
                stmt.setNull(7, Types.BINARY);
            else
                stmt.setBytes(7, imgBytes);

            stmt.setInt(8, t.getIdToDo());

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
     * Elimina un ToDo dato il suo id.
     *
     * @param idToDo id del ToDo da eliminare.
     */
    @Override
    public void eliminaToDo(int idToDo) {
        String query = "DELETE FROM todo WHERE idtodo = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, idToDo);
            stmt.executeUpdate();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Sposta un ToDo in un'altra bacheca, verificando che l'utente sia proprietario.
     *
     * @param idToDo identificatore del ToDo da spostare.
     * @param bacheca nuova bacheca di destinazione.
     * @param username proprietario del ToDo.
     * @return 0 se spostamento riuscito, -1 altrimenti.
     */
    @Override
    public int spostaToDo(int idToDo, String bacheca, String username) {
        String query = "UPDATE todo SET titolobacheca = ? WHERE idtodo = ? AND proprietario = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, bacheca);
            stmt.setInt(2, idToDo);
            stmt.setString(3, username);
            int r = stmt.executeUpdate();
            if (r!=0)
                return 0;
            else
                return-1;
        }
        catch (Exception e){
            e.printStackTrace();
            return-1;
        }
    }

    /**
     * Recupera un ToDo dal database dato il suo id.
     *
     * @param idToDo identificatore del ToDo.
     * @return ToDo corrispondente se trovato, null altrimenti.
     */
    public ToDo getToDo(int idToDo) {
        String query = "SELECT * " +
                       "FROM todo WHERE idToDo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idToDo);
            ResultSet r = stmt.executeQuery();
            if (r.next()) {
                return costruisciToDo(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Costruisce un oggetto ToDo da un ResultSet.
     *
     * @param r ResultSet da cui estrarre i dati.
     * @return ToDo costruito.
     * @throws SQLException in caso di errore nell'accesso ai dati.
     */
    public ToDo costruisciToDo(ResultSet r) throws SQLException {
        ToDo t = new ToDo();
        t.setIdToDo(r.getInt(ID));
        t.setTitolo(r.getString(TITOLO));
        t.setDescrizione(r.getString(DESC));

        Date dataScadenza = r.getDate(DATASCAD);
        if (dataScadenza == null)
            t.setDataScadenza((LocalDate) null);
        else
            t.setDataScadenza(dataScadenza.toLocalDate());

        t.setUrl(r.getString(URL));

        String coloreStr = r.getString(BGCOLOR);
        Color colore = (coloreStr != null && !coloreStr.isEmpty())
                ? Color.decode(coloreStr)
                : new Color(102, 102, 102);
        t.setColoreSfondo(colore);

        t.setStato(r.getBoolean(STATO));

        byte[] imgBytes = r.getBytes(IMG);
        t.setImmagine(imgBytes);

        return t;
    }

    /**
     * Costruisce una lista di ToDo dati un ResultSet.
     *
     * @param r ResultSet da cui leggere i ToDo.
     * @return lista di ToDo costruiti.
     * @throws SQLException in caso di errore di lettura.
     */
    public ArrayList<ToDo> costruisciListaToDo(ResultSet r) throws SQLException{
        ArrayList<ToDo> list = new ArrayList<>();
        while (r.next()) {
            list.add(costruisciToDo(r));
        }
        return list;
    }
}
