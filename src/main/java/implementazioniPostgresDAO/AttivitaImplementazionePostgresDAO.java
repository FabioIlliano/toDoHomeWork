package implementazioniPostgresDAO;

import dao.AttivitaDAO;
import database.ConnessioneDataBase;
import model.Attivita;

import java.sql.*;
import java.util.ArrayList;

public class AttivitaImplementazionePostgresDAO implements AttivitaDAO {
    private Connection connection;

    public AttivitaImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDataBase.getInstance().getConnection();
    }

    @Override
    public int creaAttivita(int idToDo, String nome) {
        String query = "INSERT INTO attivita (nome, todo) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setInt(2, idToDo);

            int r = stmt.executeUpdate();

            if (r != 0)
                return 0;
            else
                return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public ArrayList<Attivita> getAttivita(int idToDo) {
        String query = "SELECT nome, stato FROM attivita WHERE todo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idToDo);

            ResultSet r = stmt.executeQuery();
            ArrayList<Attivita> lista = new ArrayList<>();
            while (r.next()){
                Attivita a = new Attivita(r.getString("nome"));
                a.setStato(r.getBoolean("stato"));
                lista.add(a);
            }
            return lista;
        }
        catch(Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public int setStato(boolean stato, String nome, int idToDo) {
        String query = "UPDATE attivita SET stato = ? WHERE nome = ? AND todo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBoolean(1, stato);
            stmt.setString(2, nome);
            stmt.setInt(3, idToDo);

            int r = stmt.executeUpdate();

            if (r!=0)
                return 0;
            else
                return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int eliminaAttivita(String nome, int idToDo) {
        String query = "DELETE FROM attivita WHERE nome = ?  AND todo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setInt(2, idToDo);

            int r = stmt.executeUpdate();

            if (r!=0)
                return 0;
            else
                return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
