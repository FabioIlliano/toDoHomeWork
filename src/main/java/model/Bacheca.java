package model;

import java.time.LocalDate;
import java.util.*;

public class Bacheca {
    private TitoloBacheca titolo;
    private String descrizione;
    private ArrayList<ToDo> listaToDo;

    public Bacheca(TitoloBacheca titolo, String descrizione)
    {
        this.titolo = titolo;
        this.descrizione = descrizione;
        listaToDo = new ArrayList<ToDo>();
    }

    public void modificaDescrizione()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Inserisci la nuova descrizione");
        String NuovaDescrizione;

        NuovaDescrizione = in.nextLine();
        this.descrizione = NuovaDescrizione;
    }

    public void creaToDo()
    {
        Scanner in = new Scanner(System.in);
        String titolo;
        String descrizione;
        String url;
        String dataScadenza;
        int immagine;
        String coloreSfondo;

        System.out.print("Inserisci il titolo del nuovo ToDo: ");
        titolo = in.nextLine();
        System.out.print("Inserisci la descrizione: ");
        descrizione = in.nextLine();
        System.out.print("Inserisci un URL: ");
        url = in.nextLine();
        System.out.print("Inserisci la data di scadenza (YYYY-MM-DD): ");
        dataScadenza = in.nextLine();
        System.out.print("Inserisci un immagine (per il momento degli int a caso): ");
        immagine = in.nextInt();
        in.nextLine();
        System.out.print("Inserisci il colore sfondo: ");
        coloreSfondo = in.nextLine();
        ToDo nuovoToDo = new ToDo(titolo, descrizione, url, dataScadenza, immagine, coloreSfondo);
        listaToDo.add(nuovoToDo);
    }

   /* private void inserisciODatiToDo(ToDo todo) {
        Scanner in = new Scanner(System.in);

        System.out.print("Inserisci il titolo: ");
        String titolo = in.nextLine();
        System.out.print("Inserisci la descrizione: ");
        String descrizione = in.nextLine();
        System.out.print("Inserisci un URL: ");
        String url = in.nextLine();
        System.out.print("Inserisci la data di scadenza (YYYY-MM-DD): ");
        String dataScadenza = in.nextLine();
        System.out.print("Inserisci un'immagine (int): ");
        int immagine = in.nextInt();
        in.nextLine(); // consuma newline
        System.out.print("Inserisci il colore sfondo: ");
        String coloreSfondo = in.nextLine();

        // Se è un nuovo ToDo
        if (todo == null) {
            ToDo nuovoToDo = new ToDo(titolo, descrizione, url, dataScadenza, immagine, coloreSfondo);
            listaToDo.add(nuovoToDo);
        } else {
            // Modifica
            todo.setTitolo(titolo);
            todo.setDescrizione(descrizione);
            todo.setUrl(url);
            todo.setDataScadenza(dataScadenza);
            todo.setImmagine(immagine);
            todo.setColoreSfondo(coloreSfondo);
        }
    }

    public void modificaToDo(String titolo) {
        ToDo t = getToDo(titolo);
        if (t != null) {
            inserisciODatiToDo(t);
            System.out.println("ToDo modificato.");
        } else {
            System.out.println("ToDo non trovato.");
        }
    }*/

    public void mostraTutti()
    {
        for (ToDo t : listaToDo) {
            System.out.println(t.getTitolo());
        }
    }

    public ToDo getToDo(String titolo){
        for(ToDo todo : listaToDo)
        {
            if(todo.getTitolo().equalsIgnoreCase(titolo))
            {
                return todo;
            }
        }
        return null;
    }

    public TitoloBacheca getTitolo(){
        return titolo;
    }

    public String getDescrizione(){
        return descrizione;
    }

    public ArrayList<ToDo> getListaToDo() {
        return listaToDo;
    }

    public void aggiungiToDo(ToDo todo){
        listaToDo.add(todo);
    }

    public void rimuoviToDo(ToDo todo){
        listaToDo.remove(todo);
    }

    public void eliminaToDo(String titolo)
    {
        ToDo todoDaEliminare = null;
        int i;
        for (i=0;i<listaToDo.size();i++)
            if (titolo.equals(listaToDo.get(i).getTitolo())){
                todoDaEliminare = listaToDo.get(i);
            }
        if (todoDaEliminare == null)
            return;

        Scanner in = new Scanner(System.in);
        int scelta;
        System.out.println("Sei sicuro di voler eliminare il ToDo "+ todoDaEliminare.getTitolo()+"?\n" +
                "Eliminare un ToDo comporta l'eliminazione di tutti i suoi dati e eventuali attività corrispondenti.\nQuesta azione non è reversibile.\n" +
                "Inserire 1 per confermare l'eliminazione, 0 per annullare l'eliminazione del ToDo " + todoDaEliminare.getTitolo());
        do{
            scelta = in.nextInt();
            if (scelta==1){
                boolean b = listaToDo.remove(todoDaEliminare);
                if (b)
                    System.out.println("Il ToDo è stato eliminato correttamente");
                else
                    System.out.println("Il ToDo non è stato eliminato correttamente");
            } else if (scelta==0)
                System.out.println("Operazione annullata, il ToDo non è stata eliminato");
            else
                System.out.println("Effettuare una scelta");
        }while (scelta!=0 && scelta!= 1);

    }

    public void ordinaToDoTitolo()
    {
        listaToDo.sort(Comparator.comparing(todo -> todo.getTitolo().toLowerCase()));
    }

    public void ordinaToDoDataScad()
    {
        listaToDo.sort(Comparator.comparing(toDo -> toDo.getDataScadenza()));
    }



    @Override
    public String toString()
    {
        return "Il titolo della bacheca è: " + titolo + "\n" +
                "Descrizione: " + descrizione + "\n";
    }

    //non ci pensate veramente :P
    public void stampabacheca()
    {
        int larghezza = ("Descrizione: " + descrizione).length();

        for (ToDo todo : listaToDo) {
            String riga = String.format("%d. %s", listaToDo.indexOf(todo) + 1, todo.getTitolo());
            if (riga.length() > larghezza) larghezza = riga.length();
        }

        larghezza += 4; // padding

        String bordoSuperiore = "╔" + "═".repeat(larghezza) + "╗";
        String bordoInferiore = "╚" + "═".repeat(larghezza) + "╝";
        String separatore = "╠" + "═".repeat(larghezza) + "╣";

        System.out.println(bordoSuperiore);
        System.out.printf("║ %-"+larghezza+"s║\n", "BACHECA: " + titolo);
        System.out.printf("║ %-"+larghezza+"s║\n", "Descrizione: " + descrizione);
        System.out.println(separatore);

        if (listaToDo.isEmpty()) {
            System.out.printf("║ %-"+larghezza+"s║\n", "(Nessun ToDo)");
        } else {
            for (int i = 0; i < listaToDo.size(); i++) {
                String riga = String.format("%d. %s", i + 1, listaToDo.get(i).getTitolo());
                System.out.printf("║ %-"+larghezza+"s║\n", riga);
            }
        }

        System.out.println(bordoInferiore);
    }
}
