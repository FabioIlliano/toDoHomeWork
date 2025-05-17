package Controller;

import model.*;

import java.util.ArrayList;

public class Controller {
    private ToDo todo;
    private Bacheca bacheca;
    private Utente utente;
    private Attivita attivita;
    private String titoloBacheca;
    private String titoloToDoCorrente;

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

    public void creaToDo(String titoloToDo)
    {
        utente.getBacheca(titoloBacheca).creaToDoGUI(titoloToDo);
    }

    public void eliminaToDo(String t)
    {
        getUtente().getBacheca(getTitoloBacheca()).eliminaToDoGUI(t);
    }

    public void cambiaTitoloToDo(String t)
    {
        getUtente().getBacheca(getTitoloBacheca()).getToDoTitolo(getTitoloToDoCorrente()).setTitolo(t);
    }

    public void ordinaToDoAlfabeticamente() {
        if (utente != null && titoloBacheca != null) {
            Bacheca b = utente.getBacheca(titoloBacheca);
            if (b != null) {
                b.ordinaToDoTitolo();
            }
        }
    }

    public void ordinaToDoPerScadenza() {
        if (utente != null && titoloBacheca != null) {
            Bacheca b = utente.getBacheca(titoloBacheca);
            if (b != null) {
                b.ordinaToDoDataScad();
            }
        }
    }



    public ArrayList<String> getListaToDo() {
        if (utente != null && titoloBacheca != null) {
            Bacheca b = utente.getBacheca(titoloBacheca);
            if (b != null) {
                return b.getListaTitoliToDo();
            }
        }
        return new ArrayList<>();
    }

    public String getTitoloToDoCorrente() {
        return titoloToDoCorrente;
    }

    public void setTitoloToDoCorrente(String t) {
        this.titoloToDoCorrente = t;
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
