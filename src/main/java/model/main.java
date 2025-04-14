package model;

public class main {
    public static void main(String[] args)
    {
        Utente admin = new Utente("Admin", "1234");

        /*
        if (!admin.login())
        {
            System.out.println("Credenziali errate!");
            return;
        }
        */

        admin.creaBacheca();
        admin.getBacheca("universita").creaToDo();
        admin.getBacheca("universita").creaToDo();
        admin.getBacheca("universita").creaToDo();

        admin.getBacheca("universita").mostraTutti();
        System.out.println("PROVA 1\n\n");
        admin.getBacheca("universita").ordinaToDoDataScad();

        admin.getBacheca("universita").mostraTutti();
        admin.getBacheca("universita").ordinaToDoTitolo();
        System.out.println("PROVA 2\n\n");
        admin.getBacheca("universita").mostraTutti();
        //admin.creaBacheca();
        //admin.creaBacheca();

        //si possono migliorare questi metodi facendo inserire dall'utente ma funzionano

        /*
        admin.printBacheca(TitoloBacheca.TEMPO_LIBERO.toString());
        admin.eliminaBacheca(TitoloBacheca.TEMPO_LIBERO.toString());
        admin.printBacheca(TitoloBacheca.TEMPO_LIBERO.toString());
        */

       /* else{
            final String ROSSO = "\u001B[31m";
            final String RESET = "\u001B[0m";
            System.out.println(ROSSO + "Bacheca non trovata!" + RESET);
        }
         */
        //admin.printBacheca(TitoloBacheca.TEMPO_LIBERO.toString());

    }

}
