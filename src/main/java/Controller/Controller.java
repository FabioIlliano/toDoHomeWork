package Controller;

import model.*;

import javax.swing.*;

public class Controller {
    private ToDo todo;
    private Bacheca bacheca;
    private Utente utente;
    private Attivita attivita;

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

    public boolean checkBacheca (){
        return utente.contaBacheche();
    }

    public void creaBacheca (TitoloBacheca t, String d) throws Exception{
        if (!checkBacheca())

            return; //andrebbe gestito
        utente.creaBachechaGUI(t, d);
    }

}
