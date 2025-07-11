package model;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;

/**
 * The type Bacheca.
 */
public class Bacheca {
    private TitoloBacheca titolo;
    private String descrizione;
    private ArrayList<ToDo> listaToDo;

    /**
     * Instantiates a new Bacheca.
     *
     * @param titolo      the titolo
     * @param descrizione the descrizione
     */
    public Bacheca(TitoloBacheca titolo, String descrizione)
    {
        this.titolo = titolo;
        this.descrizione = descrizione;
        listaToDo = new ArrayList<ToDo>();
    }

    /**
     * Modifica descrizione.
     */
    public void modificaDescrizione()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Inserisci la nuova descrizione");
        String NuovaDescrizione;

        NuovaDescrizione = in.nextLine();
        this.descrizione = NuovaDescrizione;
    }

    /**
     * Crea to do.
     */
    public void creaToDo()
    {
        Scanner in = new Scanner(System.in);
        String titolo;
        String descrizione;
        String url;
        String dataScadenza;
        int immagine;
        Color coloreSfondo;

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
        //coloreSfondo = (Color) in.nextLine();
        //ToDo nuovoToDo = new ToDo(titolo, descrizione, url, dataScadenza, immagine);
        //listaToDo.add(nuovoToDo);
    }

    /**
     * Crea to do gui.
     *
     * @param titoloToDo the titolo to do
     */
    public void creaToDoGUI(String titoloToDo)
    {
        ToDo nuovoToDo = new ToDo(titoloToDo);
        listaToDo.add(nuovoToDo);
    }

    /**
     * Gets lista titoli to do.
     *
     * @return the lista titoli to do
     */
//funzione per la GUI
    public ArrayList<String> getListaTitoliToDo() {
        ArrayList<String> titoli = new ArrayList<>();
        for (ToDo t : listaToDo) {
            titoli.add(t.getTitolo());
        }
        return titoli;
    }

    /**
     * Elimina to do gui.
     *
     */
//funzione per la GUI
    public void eliminaToDoGUI(int idToDo) {
        ToDo todoDaEliminare = null;

        for (ToDo t : listaToDo) {
            if (t.getIdToDo() == (idToDo)) {
                todoDaEliminare = t;
                break; // Interrompi appena trovato
            }
        }

        if (todoDaEliminare != null) {
            listaToDo.remove(todoDaEliminare);
        }
    }


    /**
     * Mostra tutti.
     */
    public void mostraTutti()
    {
        for (ToDo t : listaToDo) {
            System.out.println(t.getTitolo());
        }
    }

    /**
     * Get to do titolo to do.
     *
     * @param titolo the titolo
     * @return the to do
     */
    public ToDo getToDoTitolo(String titolo){
        for(ToDo todo : listaToDo)
            if(todo.getTitolo().equalsIgnoreCase(titolo))
                return todo;
        return null;
    }

    public ToDo getToDoId(int idToDo){
        for(ToDo todo : listaToDo)
            if(todo.getIdToDo() == idToDo)
                return todo;
        return null;
    }

    /**
     * Get to do scadenza oggi array list.
     *
     * @return the array list
     */
    public ArrayList<ToDo> getToDoScadenzaOggi(){
        LocalDate dataoggi = LocalDate.now();
        ArrayList<ToDo> listaScad = new ArrayList<>();
        if (listaToDo.isEmpty())
            return null;
        for(ToDo todo : listaToDo)
            if (todo.getDataScadenza()==null)
                continue;
            else if(todo.getDataScadenza().isEqual(dataoggi))
                listaScad.add(todo);
        return listaScad;
    }

    /**
     * Get to do scadenza fissata array list.
     *
     * @param date the date
     * @return the array list
     */
    public ArrayList<ToDo> getToDoScadenzaFissata(LocalDate date){
        ArrayList<ToDo> listaScad = new ArrayList<>();
        if (listaToDo.isEmpty())
            return null;
        for(ToDo todo : listaToDo)
            if(todo.getDataScadenza()==null)
                continue;
            else if(todo.getDataScadenza().isBefore(date))
                listaScad.add(todo);
        return listaScad;
    }

    /**
     * Cerca to do array list.
     *
     * @param titoloToDo the titolo to do
     * @return the array list
     */
    public ArrayList<ToDo> cercaToDo(String titoloToDo){
        ArrayList<ToDo> lista = new ArrayList<>();
        if (listaToDo.isEmpty())
            return null;
        for(ToDo todo : listaToDo)
            if(todo.getTitolo().equalsIgnoreCase(titoloToDo))
                lista.add(todo);
        return lista;
    }


    /**
     * Get titolo titolo bacheca.
     *
     * @return the titolo bacheca
     */
    public TitoloBacheca getTitolo(){
        return titolo;
    }

    /**
     * Get descrizione string.
     *
     * @return the string
     */
    public String getDescrizione(){
        return descrizione;
    }

    /**
     * Set descrizione.
     *
     * @param descrizione the descrizione
     */
    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    /**
     * Gets lista to do.
     *
     * @return the lista to do
     */
    public ArrayList<ToDo> getListaToDo() {
        return listaToDo;
    }

    /**
     * Aggiungi to do.
     *
     * @param todo the todo
     */
    public void aggiungiToDo(ToDo todo){
        listaToDo.add(todo);
    }

    /**
     * Rimuovi to do.
     *
     * @param todo the todo
     */
    public void rimuoviToDo(ToDo todo){
        listaToDo.remove(todo);
    }

    /**
     * Elimina to do.
     *
     * @param titolo the titolo
     */
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

    /**
     * Ordina to do titolo.
     */
//metodo che ordina i todo in base al titolo
    public void ordinaToDoTitolo()
    {
        listaToDo.sort(Comparator.comparing(todo -> todo.getTitolo().toLowerCase()));
    }

    /**
     * Ordina to do data scad.
     */
//metodo che ordina i todo per la data di scadenza
    public void ordinaToDoDataScad()
    {
        listaToDo.sort(Comparator.comparing(ToDo::getDataScadenza, Comparator.nullsLast(Comparator.naturalOrder())));
    }


    //metodo utile per debug per stampa
    @Override
    public String toString()
    {
        return "Il titolo della bacheca è: " + titolo + "\n" +
                "Descrizione: " + descrizione + "\n";
    }

    /**
     * Stampabacheca.
     */
//metodo per stampa in fase di debug
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
