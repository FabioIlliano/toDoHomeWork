package model;

import java.time.LocalDate;
import java.util.*;

/**
 * Rappresenta una bacheca contenente una lista di ToDo,
 * ciascuna associata a un titolo e una descrizione.
 */
public class Bacheca {
    private final TitoloBacheca titolo;
    private String descrizione;
    private final ArrayList<ToDo> listaToDo;

    /**
     * Costruisce una nuova bacheca con il titolo e la descrizione specificati.
     *
     * @param titolo      il titolo della bacheca
     * @param descrizione la descrizione della bacheca
     */
    public Bacheca(TitoloBacheca titolo, String descrizione) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        listaToDo = new ArrayList<>();
    }

    /**
     * Crea un nuovo ToDo nella bacheca.
     *
     * @param titoloToDo il titolo del nuovo ToDo
     */
    public void creaToDo(String titoloToDo)
    {
        ToDo nuovoToDo = new ToDo(titoloToDo);
        listaToDo.add(nuovoToDo);
    }

    /**
     * Elimina un ToDo dalla bacheca dato il suo ID.
     *
     * @param idToDo l'identificativo del ToDo da eliminare
     */
    public void eliminaToDo(int idToDo) {
        ToDo todoDaEliminare = null;

        for (ToDo t : listaToDo) {
            if (t.getIdToDo() == (idToDo)) {
                todoDaEliminare = t;
                break;
            }
        }
        if (todoDaEliminare != null)
            listaToDo.remove(todoDaEliminare);
    }

    /**
     * Restituisce un ToDo dato il suo ID.
     *
     * @param idToDo l'identificativo del ToDo
     * @return il ToDo corrispondente, null se non trovato
     */
    public ToDo getToDoId(int idToDo){
        for(ToDo todo : listaToDo)
            if(todo.getIdToDo() == idToDo)
                return todo;
        return null;
    }

    /**
     * Restituisce la lista dei ToDo con scadenza odierna.
     *
     * @return una lista di ToDo in scadenza oggi, oppure null se vuota
     */
    public ArrayList<ToDo> getToDoScadenzaOggi(){
        LocalDate dataoggi = LocalDate.now();
        ArrayList<ToDo> listaScad = new ArrayList<>();
        if (listaToDo.isEmpty())
            return new ArrayList<>();
        for(ToDo todo : listaToDo)
            if(todo.getDataScadenza()!=null && todo.getDataScadenza().isEqual(dataoggi))
                listaScad.add(todo);
        return listaScad;
    }

    /**
     * Restituisce la lista dei ToDo con scadenza precedente a una data specificata.
     *
     * @param date la data di riferimento
     * @return la lista di ToDo con scadenza precedente alla data
     */
    public ArrayList<ToDo> getToDoScadenzaFissata(LocalDate date){
        ArrayList<ToDo> listaScad = new ArrayList<>();
        if (listaToDo.isEmpty())
            return new ArrayList<>();
        for(ToDo todo : listaToDo)
            if(todo.getDataScadenza()!=null && todo.getDataScadenza().isBefore(date))
                listaScad.add(todo);
        return listaScad;
    }

    /**
     * Cerca e restituisce i ToDo con titolo corrispondente.
     *
     * @param titoloToDo il titolo da cercare
     * @return la lista dei ToDo con il titolo specificato, oppure null se vuota
     */
    public ArrayList<ToDo> cercaToDo(String titoloToDo){
        ArrayList<ToDo> lista = new ArrayList<>();
        if (listaToDo.isEmpty())
            return new ArrayList<>();
        for(ToDo todo : listaToDo)
            if(todo.getTitolo().equalsIgnoreCase(titoloToDo))
                lista.add(todo);
        return lista;
    }

    /**
     * Restituisce il titolo della bacheca.
     *
     * @return il titolo
     */
    public TitoloBacheca getTitolo(){
        return titolo;
    }

    /**
     * Restituisce la descrizione della bacheca.
     *
     * @return la descrizione
     */
    public String getDescrizione(){
        return descrizione;
    }

    /**
     * Imposta una nuova descrizione per la bacheca.
     *
     * @param descrizione la nuova descrizione
     */
    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    /**
     * Restituisce la lista dei ToDo della bacheca.
     *
     * @return la lista dei ToDo
     */
    public ArrayList<ToDo> getListaToDo() {
        return listaToDo;
    }

    /**
     * Aggiunge un ToDo alla bacheca.
     *
     * @param todo il ToDo da aggiungere
     */
    public void aggiungiToDo(ToDo todo){
        listaToDo.add(todo);
    }

    /**
     * Ordina i ToDo per titolo in ordine alfabetico.
     */
    public void ordinaToDoTitolo()
    {
        listaToDo.sort(Comparator.comparing(todo -> todo.getTitolo().toLowerCase()));
    }

    /**
     * Ordina i ToDo per data di scadenza, considerando i null come ultimi.
     */
    public void ordinaToDoDataScad()
    {
        listaToDo.sort(Comparator.comparing(ToDo::getDataScadenza, Comparator.nullsLast(Comparator.naturalOrder())));
    }

}
