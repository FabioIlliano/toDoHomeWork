package model;

public class main {
    public static void main(String[] args) {
        Utente admin = new Utente("Admin", "1234");

        /*
        if (!admin.login())
        {
            System.out.println("Credenziali errate!");
            return;
        }
        */

        admin.creaBacheca();
        //admin.creaBacheca();
        //admin.creaBacheca();

        //si possono migliorare questi metodi facendo inserire dall'utente ma funzionano

        /*
        admin.printBacheca(TitoloBacheca.TEMPO_LIBERO.toString());
        admin.eliminaBacheca(TitoloBacheca.TEMPO_LIBERO.toString());
        admin.printBacheca(TitoloBacheca.TEMPO_LIBERO.toString());
        */
        String string = admin.getBacheca("universiTA").toString();
        System.out.println(string);

        admin.getBacheca("Universita").creaToDo();
        String s = admin.getBacheca("Universita").getToDo("Emanuele").toString();
        System.out.println(s);

        System.out.println("prova prova prova");

        admin.getBacheca("Universita").mostraTutti();

        /*
        Bacheca b = admin.getBacheca(TitoloBacheca.TEMPO_LIBERO.toString());
        if (b!=null)
            b.modificaDescrizione(TitoloBacheca.TEMPO_LIBERO.toString());
        else{
            final String ROSSO = "\u001B[31m";
            final String RESET = "\u001B[0m";
            System.out.println(ROSSO + "Bacheca non trovata!" + RESET);
        }
         */
        //admin.printBacheca(TitoloBacheca.TEMPO_LIBERO.toString());

    }

}
