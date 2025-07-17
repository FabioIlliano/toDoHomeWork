package model;

/**
 * Rappresenta un'attività con un nome e uno stato booleano che indica
 * se l'attività è completata o meno.
 */
public class Attivita {
    private String nome;
    private boolean stato;

    /**
     * Costruisce una nuova attività con il nome specificato.
     * Lo stato iniziale è impostato a false (non completata).
     *
     * @param nome il nome dell'attività
     */
    public Attivita (String nome){
        this.nome = nome;
        stato = false;
    }

    /**
     * Restituisce lo stato corrente dell'attività.
     *
     * @return true se l'attività è completata, false altrimenti
     */
    public boolean isStato() {
        return stato;
    }

    /**
     * Modifica lo stato dell'attività.
     *
     * @param b il nuovo stato dell'attività (true per completata, false per non completata)
     */
    public void modificaStato(boolean b)
    {
        this.stato = b;
    }

    /**
     * Restituisce il nome dell'attività.
     *
     * @return il nome dell'attività
     */
    public String getNome() {
        return nome;
    }

}
