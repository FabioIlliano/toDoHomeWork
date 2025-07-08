package implementazioniPostgresDAO;

import dao.ToDoDAO;
import database.ConnessioneDataBase;
import model.ToDo;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ToDoImplementazionePostgresDAO implements ToDoDAO {
    private Connection connection;
    public ToDoImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDataBase.getInstance().getConnection();
    }

    @Override
    public ArrayList<ToDo> caricaDatiToDo(String bacheca, String username) {
        String query = "SELECT * FROM todo WHERE titolobacheca = ? AND proprietario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, bacheca);
            stmt.setString(2, username);

            ResultSet r = stmt.executeQuery();

            ArrayList<ToDo> list = new ArrayList<>();
            while (r.next()){
                ToDo t = new ToDo();
                t.setIdToDo(r.getInt("idtodo"));
                t.setTitolo(r.getString("titolo"));
                t.setDescrizione(r.getString("descrizione"));
                if (r.getDate("datascadenza")==null)
                    t.setDataScadenza((LocalDate) null);
                else
                    t.setDataScadenza(r.getDate("datascadenza").toLocalDate());
                t.setUrl(r.getString("url"));

                String coloreStr = r.getString("coloresfondo");
                Color colore;
                if (coloreStr != null && !coloreStr.isEmpty())
                    colore = Color.decode(coloreStr);
                else
                    colore = new Color(200, 200, 200);
                t.setColoreSfondo(colore);
                t.setStato(r.getBoolean("stato"));
                String img = r.getString("immagine");
                if (img == null)
                    t.setImmagine(null);
                else{
                    ImageIcon image = new ImageIcon(img);
                    t.setImmagine(image.getImage());
                    t.setImgPath(img);
                }
                list.add(t);
            }
            return list;
        }
        catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public ArrayList<ToDo> caricaDatiToDoCondivisi(String bacheca, String username) {
        String query = "SELECT t.* FROM todo t " +
                "JOIN condividitodo c ON t.idtodo = c.todocondiviso " +
                "WHERE c.utentedest = ? AND t.titolobacheca = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setString(1, username);
            stmt.setString(2, bacheca);
            ResultSet r = stmt.executeQuery();
            ArrayList<ToDo> list = new ArrayList<>();
            while (r.next()){
                ToDo t = new ToDo();
                t.setIdToDo(r.getInt("idtodo"));
                t.setTitolo(r.getString("titolo"));
                t.setDescrizione(r.getString("descrizione"));
                if (r.getDate("datascadenza")==null)
                    t.setDataScadenza((LocalDate) null);
                else
                    t.setDataScadenza(r.getDate("datascadenza").toLocalDate());
                t.setUrl(r.getString("url"));

                String coloreStr = r.getString("coloresfondo");
                Color colore;
                if (coloreStr != null && !coloreStr.isEmpty())
                    colore = Color.decode(coloreStr);
                else
                    colore = new Color(200, 200, 200);
                t.setColoreSfondo(colore);
                t.setStato(r.getBoolean("stato"));
                String img = r.getString("immagine");
                if (img == null)
                    t.setImmagine(null);
                else{
                    ImageIcon image = new ImageIcon(img);
                    t.setImmagine(image.getImage());
                    t.setImgPath(img);
                }
                list.add(t);
            }
            return list;
        }
        catch (SQLException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

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

    @Override
    public void aggiornaToDo(ToDo t) throws SQLException{
        String query = "UPDATE todo SET titolo = ?, descrizione = ?, datascadenza = ?, url = ?, coloresfondo = ?, stato = ?, immagine = ? WHERE idtodo = ?";

        PreparedStatement stmt = connection.prepareStatement(query);

        stmt.setString(1, t.getTitolo());

        if (t.getDescrizione().trim().isEmpty() || t.getDescrizione() == null)
            stmt.setNull(2, Types.VARCHAR);
        else
            stmt.setString(2, t.getDescrizione());

        if (t.getDataScadenza() != null)
            stmt.setDate(3, Date.valueOf(t.getDataScadenza()));
        else
            stmt.setNull(3, java.sql.Types.DATE);

        if (t.getUrl()==null || t.getUrl().trim().isEmpty())
            stmt.setNull(4, Types.VARCHAR);
        else
            stmt.setString(4, t.getUrl());

        Color colore = t.getColoreSfondo();
        String coloreString = null;
        if (colore!=null)
            coloreString = String.format("#%02X%02X%02X", colore.getRed(), colore.getGreen(), colore.getBlue());
        stmt.setString(5, coloreString);
        stmt.setBoolean(6, t.getStato());

        if (t.getImgPath()==null || t.getImgPath().trim().isEmpty())
            stmt.setNull(7, Types.VARCHAR);
        else
            stmt.setString(7, t.getImgPath());

        stmt.setInt(8, t.getIdToDo());

        stmt.executeUpdate();
    }


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
}
