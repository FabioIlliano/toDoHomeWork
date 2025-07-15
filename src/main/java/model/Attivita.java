package model;

/**
 * The type Attivita.
 */
public class Attivita {
    private String nome;
    private boolean stato;


    /**
     * Instantiates a new Attivita.
     *
     * @param nome the nome
     */
    public Attivita (String nome){
        this.nome = nome;
        stato = false;
    }

    public boolean isStato() {
        return stato;
    }

    /**
     * Modifica stato.
     */
    public void modificaStato(boolean b)
    {
        this.stato = b;
    }

    public void setStato(boolean stato){
        this.stato = stato;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
}
