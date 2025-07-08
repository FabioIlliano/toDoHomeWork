package dao;

import model.TitoloBacheca;

import java.util.ArrayList;

public interface BachecaDAO {
    public ArrayList<String> getTitoliUtente (String username);

    public int creaBacheca (TitoloBacheca titolo, String descrizione, String username);

    public int eliminaBacheca (TitoloBacheca titolo, String username);

    public int aggiornaDescrizioneBacheca (TitoloBacheca titolo, String descrizione, String username);
}
