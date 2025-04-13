package model;

public class main {
    public static void main(String[] args) {
        Utente admin = new Utente("Admin", "1234");

        if (!admin.login())
        {
            System.out.println("Credenziali errate!");
            return;
        }
        admin.creaBacheca();
        admin.creaBacheca();
        admin.creaBacheca();
        //si possono migliorare questi metodi facendo inserire dall'utente ma funzionano
        admin.printBacheca(TitoloBacheca.TEMPO_LIBERO.toString());
        admin.eliminaBacheca(TitoloBacheca.TEMPO_LIBERO.toString());
        admin.printBacheca(TitoloBacheca.TEMPO_LIBERO.toString());


        admin.modificaDescrizioneBacheca(TitoloBacheca.UNIVERSITA.toString());
        admin.modificaDescrizioneBacheca("titolo errato");

    }

}
