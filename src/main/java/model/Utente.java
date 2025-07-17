package model;

import java.util.ArrayList;

/**
 * Rappresenta un utente che può effettuare l'accesso e interagire con il sistema
 */
public class Utente {
    String username;
    String password;
    ArrayList<Bacheca> bachecheUtente= new ArrayList<>();

    /**
     * Costruisce un nuovo utente con username e password.
     *
     * @param username nome utente
     * @param password password dell'utente
     */
    public Utente(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Verifica se l'utente può aggiungere una nuova bacheca.
     *
     * @return true se il numero di bacheche è minore di 3, false altrimenti
     */
    public boolean puoAggiungereBacheca(){
        return bachecheUtente.size() < 3;
    }

    /**
     * Crea e aggiunge una nuova bacheca all'elenco delle bacheche dell'utente.
     *
     * @param titolo il titolo della bacheca
     * @param descrizione la descrizione della bacheca
     */
    public void creaBacheca(TitoloBacheca titolo, String descrizione){
        Bacheca bacheca = new Bacheca(titolo, descrizione);
        bachecheUtente.add(bacheca);
    }

    /**
     * Carica una bacheca già esistente nell'elenco delle bacheche dell'utente.
     *
     * @param titolo il titolo della bacheca
     * @param descrizione la descrizione della bacheca
     */
    public void caricaBacheca(TitoloBacheca titolo, String descrizione){
        bachecheUtente.add(new Bacheca(titolo, descrizione));
    }

    /**
     * Rimuove tutte le bacheche dall'elenco.
     */
    public void pulisciBacheche(){
        bachecheUtente.clear();
    }

    /**
     * Elimina una bacheca dall'elenco delle bacheche dell'utente
     *
     * @param titolo il titolo della bacheca da eliminare
     * @return true se la bacheca è stata trovata e rimossa, false altrimenti
     */
    public boolean eliminaBacheca(String titolo){
        Bacheca bachecaDaEliminare = null;
        for (Bacheca b : bachecheUtente)
            if (titolo.equals(b.getTitolo().toString())){
                bachecaDaEliminare = b;
                break;
            }
        if (bachecaDaEliminare==null)
            return false;

        return (bachecheUtente.remove(bachecaDaEliminare));
    }

    /**
     * Restituisce la bacheca con il titolo specificato.
     *
     * @param titolo il titolo della bacheca da cercare
     * @return la bacheca corrispondente o null se non trovata
     */
    public Bacheca getBacheca(String titolo){
        for(Bacheca b : bachecheUtente)
        {
            if(b.getTitolo().toString().equalsIgnoreCase(titolo))
            {
                return b;
            }
        }
        return null;
    }

    /**
     * Sposta un ToDo da una bacheca all'altra.
     *
     * @param vecchia titolo della bacheca di origine
     * @param nuova titolo della bacheca di destinazione
     * @param todo il ToDo da spostare
     */
    public void spostaToDo(String vecchia, String nuova, ToDo todo)
    {
        Bacheca v = getBacheca(vecchia);
        Bacheca n = getBacheca(nuova);
        v.getListaToDo().remove(todo);
        n.aggiungiToDo(todo);
    }

    /**
     * Restituisce lo username dell'utente.
     *
     * @return lo username
     */
    public String getUsername() {
        return username;
    }
}
