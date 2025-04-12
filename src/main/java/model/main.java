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

        /*ToDO Cucina = new ToDO("Pasta e patate", "Devo cucinare pasta e patate", "www.pastepatan.potatos", false, 11, 12, "Giallo");
        System.out.println(Cucina.toString());

        //modifica stato
        Cucina.ModificaStato();

        System.out.println(Cucina.Stato);*/

    }

}
