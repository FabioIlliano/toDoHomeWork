package Model;

import java.util.Scanner;
;
public class Utente
{
    String Username;
    String Password;

    Utente(String Username, String Password)
    {
        this.Username = Username;
        this.Password = Password;
    }

    public boolean Login()
    {
        String Username;
        String Password;
        Scanner in = new Scanner(System.in);

        System.out.print("Username: ");
        Username = in.nextLine();
        System.out.print("Password: ");
        Password = in.nextLine();

        return Username.equals(this.Username) && Password.equals(this.Password);
    }

    public Bacheca CreaBacheca()
    {
        //input
        Scanner in = new Scanner(System.in);
        int i;
        TitoloBacheca Titolo = null; //conterrà il titolo scelto dall'utente preso da una classe enumerativa
        String Descrizione;

        do{
            i = 1;
            System.out.println("Scegli il titolo della bacheca tra:");
            //Questo sotto non è nient'altro che un ciclo for scritto in maniera un pò più elegante
            for(TitoloBacheca titoli : TitoloBacheca.values())
            {
                System.out.println(i + ")" + titoli.toString());
                i++;
            }

            switch(in.nextInt())
            {
                case 1:
                    Titolo = Titolo.UNIVERSITA;
                case 2:
                    Titolo = Titolo.LAVORO;
                    break;
                case 3:
                    Titolo = Titolo.TEMPO_LIBERO;
                    break;
            }
            in.nextLine(); /*questo perche in.nextInt() legge il numero ma non consuma anche il carattere "a capo" che
            viene letto dal successivo nextline (che sarebbe stato quello per inserire la descrizione, e in parole povere
            la descrizione risultava vuota.*/
        }while (Titolo == null);


        System.out.print("Inserisci descrizione della bacheca:\n");
        Descrizione = in.nextLine();
        Bacheca bacheca1 = new Bacheca(Titolo, Descrizione);

        return bacheca1;
    }
}
