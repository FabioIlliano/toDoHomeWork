import model.Utente;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {
        //nel main abbiamo provato i vari metodi, ci sono alcuni rimasugli delle prove effettuate


        //AGGIUNGERE LA LISTA DEGLI UTENTI A CUI E' STATO CONDIVISO UN TODO
        Utente Giuseppe = new Utente("Giuseppe", "1234");
        Utente admin = new Utente("admin","1234");
        final String ROSSO = "\u001B[31m";
        final String VERDE = "\033[32m";
        final String RESET = "\u001B[0m";
        Scanner in = new Scanner(System.in);
        String r;
        String s;

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
        //admin.creaBacheca();
        do
        {
            System.out.println("In quale bacheca vuoi creare il ToDo?");
            r = in.nextLine();
            admin.getBacheca(r).creaToDo();
            System.out.println("Vuoi creare un altro ToDo? Y/N:");
            r = in.nextLine();

        }while(r.equalsIgnoreCase("Y"));

        //admin.getBacheca("universita").getToDo("Dimagrire").eliminaCondivisione(admin, "universita");
        //admin.getBacheca("universita").mostraTutti();

        System.out.println("Ora tocca Giuseppe: ");
        Giuseppe.creaBacheca();
        //Giuseppe.creaBacheca();

        System.out.println("admin da quale bacheca vuoi prendere il ToDo da condividere?");
        r = in.nextLine();
        System.out.println("Quale ToDo vuoi condividere?");
        admin.getBacheca(r).mostraTutti();
        s = in.nextLine();

        System.out.println("Ok, questa è la bacheca di Giuseppe prima della condivisione");
        Giuseppe.getBacheca(r).stampabacheca();

      /* admin.getBacheca(r).getToDoTitolo(s).condividiToDo(Giuseppe, r);
        System.out.println("Questa invece è la bacheca di Giuseppe dopo la condivisione");
        Giuseppe.getBacheca(r).stampabacheca();*/

        //System.out.println("Ecco lo stato per entrambi gli utenti senza che nessuno lo completasse:\n(admin) " + admin.getBacheca(r).getToDo(s).getStato() + "\n(Giuseppe)" + Giuseppe.getBacheca(r).getToDo(s).getStato());
        System.out.println("ToDo in Giuseppe prima della modifica");
        Giuseppe.getBacheca(r).mostraTutti();
        System.out.println("ToDo in Admin prima della modifica");
        admin.getBacheca(r).mostraTutti();

        System.out.println("Adesso Giuseppe ha modificato il ToDo");
        Giuseppe.getBacheca(r).getToDoTitolo(s).ModificaStato();
        Giuseppe.getBacheca(r).getToDoTitolo(s).setDescrizione("lo fa giuseppe");
        //System.out.println("STATO DEL TODO IN ADMIN: " + admin.getBacheca(r).getToDo(s).getStato() + "\nSTATO DEL TODO IN GIUSEPPE: " + Giuseppe.getBacheca(r).getToDo(s).getStato());

        System.out.println("ToDo in Giuseppe dopo la modifica");
        Giuseppe.getBacheca(r).mostraTutti();
        String stringa = Giuseppe.getBacheca(r).getToDoTitolo(s).toString();
        System.out.println(stringa);
        System.out.println("ToDo in Admin dopo la modifica");
        admin.getBacheca(r).mostraTutti();
        String stringa1 = admin.getBacheca(r).getToDoTitolo(s).toString();
        System.out.println(stringa1);




       /* System.out.println("adesso leviamo la condivisione a giuseppe");
        admin.getBacheca(r).getToDoTitolo(s).eliminaCondivisione(Giuseppe, r);*/

        System.out.println("ToDo in Giuseppe dopo tolto condivisione");
        Giuseppe.getBacheca(r).mostraTutti();
        //String stringa2 = Giuseppe.getBacheca(r).getToDo(s).toString();
        //System.out.println(stringa2);
        System.out.println("ToDo in Admin dopo tolto condivisione");
        admin.getBacheca(r).mostraTutti();
        String stringa3 = admin.getBacheca(r).getToDoTitolo(s).toString();
        System.out.println(stringa3);



        /*admin.spostaToDo(admin.getBacheca("lavoro"), admin.getBacheca("universita"), "dimagrire");

        System.out.println("Su quale bacheca vuoi lavorare?");
        admin.mostraBacheche();

        r = System.console().readLine();
        try {
            admin.getBacheca(r).stampabacheca();
        }catch (NullPointerException e)
        {
            System.out.println(ROSSO + "QUESTA BACHECA NON ESISTE" + RESET);
        }*/

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
