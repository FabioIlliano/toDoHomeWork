package Controller;

import model.*;

public class Controller {
    private ToDo todo;
    private Bacheca bacheca;
    private Utente utente;
    private Attivita attivita;
    private String titoloBacheca;

    public Controller(){

    }

    private void creaUtente(String username, String password){
        utente = new Utente(username, password);
    }

    public boolean login(String username, String password){
        if (utente == null){
            creaUtente("admin", "1234");
        }
        return utente.loginGUI(username, password);
    }

    public boolean checkBacheche(){
        return utente.contaBacheche();
    }

    public void creaBacheca (TitoloBacheca t, String d) throws Exception{
        if (!checkBacheche())

            return; //andrebbe gestito
        utente.creaBachechaGUI(t, d);
    }


    public Utente getUtente() {
        return utente;
    }

    public String getTitoloBacheca() {
        return titoloBacheca;
    }

    public void setTitoloBacheca(String titoloBacheca) {
        this.titoloBacheca = titoloBacheca;
    }

    public boolean checkBacheca (String nomeBacheca){
        if (utente.getBacheca(nomeBacheca) == null)
            return false;
        else
            return true;
    }
}
