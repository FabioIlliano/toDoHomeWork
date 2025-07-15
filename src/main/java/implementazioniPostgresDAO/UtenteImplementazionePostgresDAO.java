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

public class UtenteImplementazionePostgresDAO implements UtenteDAO {
    private Connection connection;

    public UtenteImplementazionePostgresDAO() throws SQLException {
        connection = ConnessioneDataBase.getInstance().getConnection();
    }

    //rivedere TODO
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
                return -1; //errore generico
        }
        catch (SQLException e){
            if ("23505".equals(e.getSQLState())) {
                // errore di chiave primaria non rispettata
                e.printStackTrace();
                return 1;
            } else if ("08001".equals(e.getSQLState()) || "08006".equals(e.getSQLState())) {
                // errore di connessione al DB
                e.printStackTrace();
                return 2;
            } else {
                e.printStackTrace();
                return -1; // errore generico
            }
        }

    }

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
        catch (SQLException e){
            return -1;
        }
    }

    //metodo uguale a uno in bacheca
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
        catch (Exception e){
            return new ArrayList<Bacheca>();
        }
    }

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
