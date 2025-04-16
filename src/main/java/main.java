import model.Utente;

public class main {
    public static void main(String[] args)
    {
        Utente admin = new Utente("admin","1234");
        final String ROSSO = "\u001B[31m";
        final String VERDE = "\033[32m";
        final String RESET = "\u001B[0m";
        String r;

        System.out.println(" ____  _____ _   ___     _______ _   _ _   _ _____ ___  \n" +
                "| __ )| ____| \\ | \\ \\   / / ____| \\ | | | | |_   _/ _ \\ \n" +
                "|  _ \\|  _| |  \\| |\\ \\ / /|  _| |  \\| | | | | | || | | |\n" +
                "| |_) | |___| |\\  | \\ V / | |___| |\\  | |_| | | || |_| |\n" +
                "|____/|_____|_| \\_|  \\_/  |_____|_| \\_|\\___/  |_| \\___/ ");

        System.out.println("\033[1mLOGIN:\033[0m");
        if(!admin.login())
        {
            System.out.println(ROSSO + "CREDENZIALI ERRATE" + RESET);
            return;
        }

        System.out.println(VERDE + "Login effettuato con successo!" + RESET);

        admin.creaBacheca();
        admin.creaBacheca();
        do
        {
            admin.getBacheca("universita").creaToDo();
            System.out.println("Vuoi creare un altro ToDo? Y/N:");
            r = System.console().readLine();

        }while(r.equalsIgnoreCase("Y"));

        admin.spostaToDo(admin.getBacheca("lavoro"), admin.getBacheca("universita"), "dimagrire");

        System.out.println("Su quale bacheca vuoi lavorare?");
        admin.mostraBacheche();

        r = System.console().readLine();
        try {
            admin.getBacheca(r).stampabacheca();
        }catch (NullPointerException e)
        {
            System.out.println(ROSSO + "QUESTA BACHECA NON ESISTE" + RESET);
        }

        /*admin.getBacheca("universita").mostraTutti();
        System.out.println("PROVA 1\n\n");
        admin.getBacheca("universita").ordinaToDoDataScad();

        admin.getBacheca("universita").mostraTutti();
        admin.getBacheca("universita").ordinaToDoTitolo();
        System.out.println("PROVA 2\n\n");
        admin.getBacheca("universita").mostraTutti();
        //admin.creaBacheca();
        //admin.creaBacheca();*/
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
