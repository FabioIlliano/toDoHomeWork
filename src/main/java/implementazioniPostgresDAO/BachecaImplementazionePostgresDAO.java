package implementazioniPostgresDAO;

import dao.BachecaDAO;
import database.ConnessioneDataBase;
import model.TitoloBacheca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class BachecaImplementazionePostgresDAO implements BachecaDAO {
    private Connection connection;

    public BachecaImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDataBase.getInstance().getConnection();
    }

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

        }
        return a;
    }

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
        catch (SQLException e){
            return -1;
        }
    }

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
        catch (SQLException e){
            return -1;
        }
    }

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
        catch (SQLException e){
            return -1;
        }
    }
}
