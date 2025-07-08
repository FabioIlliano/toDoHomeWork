package implementazioniPostgresDAO;

import dao.CondividiToDoDAO;
import database.ConnessioneDataBase;
import model.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CondividiToDoImplementazionePostgresDAO implements CondividiToDoDAO {
    private Connection connection;

    public CondividiToDoImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDataBase.getInstance().getConnection();
    }

    @Override
    public int aggiungiCondivisione(String utenteMitt, String utenteDest, int idToDo) throws SQLException{
        String query = "INSERT INTO condividitodo values (?, ?, ?)";

        PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, utenteMitt);
            stmt.setString(2, utenteDest);
            stmt.setInt(3, idToDo);

            int r = stmt.executeUpdate();
            if (r!=0)
                return 0;
            else
                return -1;
    }

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
