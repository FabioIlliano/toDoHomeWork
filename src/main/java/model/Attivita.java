package model;

public class Attivita {
    private String nome;
    private boolean stato;


    public Attivita (String nome){
        this.nome = nome;
        stato = false;
    }

    public void ModificaStato()
    {
        this.stato = !stato;
    }
}
