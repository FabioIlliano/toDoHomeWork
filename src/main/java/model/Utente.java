package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Utente
{
    String username;
    String password;
    ArrayList<Bacheca> bachecheUtente= new ArrayList<Bacheca>();

    public Utente(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public boolean login()
    {
        String username;
        String password;
        Scanner in = new Scanner(System.in);

        System.out.print("Username: ");
        username = in.nextLine();
        System.out.print("Password: ");
        password = in.nextLine();

        return username.equals(this.username) && password.equals(this.password);
    }

    public boolean creaBacheca()
    {
        if (bachecheUtente.size() >= 3)
        {
            System.out.println("Hai già creato le tue bacheche!");
            return false;
        }

        Scanner in = new Scanner(System.in);
        TitoloBacheca titolo = null; //conterrà il titolo scelto dall'utente preso da una classe enumerativa
        String descrizione;

        boolean cond0, cond1, cond2, cond3;
        do {
            int i = 1;
            System.out.println("Scegli il titolo della bacheca tra:");
            //Questo sotto non è nient'altro che un ciclo for scritto in maniera un pò più elegante
            for (TitoloBacheca titoli : TitoloBacheca.values()) {
                System.out.println(i + ")" + titoli.toString());
                i++;
            }
            int scelta = in.nextInt();
            switch (scelta) {
                case 1:
                    titolo = titolo.UNIVERSITA;
                    break;
                case 2:
                    titolo = titolo.LAVORO;
                    break;
                case 3:
                    titolo = titolo.TEMPO_LIBERO;
                    break;
            }
            in.nextLine(); /*questo perche in.nextInt() legge il numero ma non consuma anche il carattere "a capo" che
            viene letto dal successivo nextline (che sarebbe stato quello per inserire la descrizione, e in parole povere
            la descrizione risultava vuota.*/

            cond0 = bachecheUtente.size() > 0 && titolo!=null && titolo.equals(bachecheUtente.get(0).getTitolo());
            cond1 = bachecheUtente.size() > 1 && titolo!=null && titolo.equals(bachecheUtente.get(1).getTitolo());
            cond3 = scelta <1 || scelta >3;
            if (cond3)
                System.out.println("Inserire un numero valido");
            else if (cond0 || cond1)
                System.out.println("Questa bacheca già esiste!");

        } while ( cond3|| cond0 || cond1);
        //dovrebbe controllare sia che il titolo sia corretto sia che non esista una bacheca con quel titolo

        System.out.print("Inserisci descrizione della bacheca:\n");
        descrizione = in.nextLine();
        Bacheca bacheca = new Bacheca(titolo, descrizione);

        bachecheUtente.add(bacheca);

        return true;
    }

    /*a
    //metodo che stampa la bacheca in base al nome
    public void printBacheca (String titolo){
        for (int i=0;i< bachecheUtente.size();i++){
            if (titolo.equals(bachecheUtente.get(i).getTitolo().toString())){
                System.out.println(bachecheUtente.get(i).getTitolo());
                System.out.println(bachecheUtente.get(i).getDescrizione());
                return;
            }
        }
        System.out.println("La bacheca "+titolo+" non è presente nel sistema");
    }
     */

    public void eliminaBacheca (String titolo){
        Bacheca bachecaDaEliminare = null;
        int i;
        for (i=0;i<bachecheUtente.size();i++)
            if (titolo.equals(bachecheUtente.get(i).getTitolo().toString())){
                bachecaDaEliminare = bachecheUtente.get(i);
        }
        if (bachecaDaEliminare==null)
            return;

        Scanner in = new Scanner(System.in);
        int scelta;
        System.out.println("Sei sicuro di voler eliminare la bacheca "+bachecaDaEliminare.getTitolo()+"?\n" +
                "Eliminare una bacheca comporta l'eliminazione di tutti i suoi ToDo e questa azione non è reversibile.\n" +
                "Inserire 1 per confermare l'eliminazione, 0 per annullare l'eliminazione della bacheca "+bachecaDaEliminare.getTitolo());
        do{
            scelta = in.nextInt();
            if (scelta==1){
                boolean b = bachecheUtente.remove(bachecaDaEliminare);
                if (b)
                    System.out.println("La bacheca è stata eliminata correttamente");
                else
                    System.out.println("La bacheca non è stata eliminata correttamente");
            } else if (scelta==0)
                System.out.println("Operazione annullata, la bacheca non è stata eliminato");
            else
                System.out.println("Effettuare una scelta");
        }while (scelta!=0 && scelta!= 1);

    }


    public Bacheca getBacheca(String titolo){
        //titolo = titolo.toUpperCase();
        for(Bacheca b : bachecheUtente)
        {
            if(b.getTitolo().toString().equalsIgnoreCase(titolo))
            {
                return b;
            }
        }
        return null;
    }

    //bisogna verificare se funziona e magari renderla più efficiente
    public void spostaToDo (Bacheca bachecaNuova, Bacheca bachecaVecchia, String nomeToDo){
        ToDo toDoNuovo = bachecaVecchia.getToDo(nomeToDo);
        bachecaVecchia.getListaToDo().remove(toDoNuovo);
        bachecaNuova.aggiungiToDo(toDoNuovo);
    }

    public void mostraBacheche()
    {
        System.out.println("le tue bacheche sono: ");
        for (Bacheca B : bachecheUtente) {
            System.out.println(B.getTitolo());
        }
    }
}
