package Controller;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Controller {
    private ToDo todo;
    private Bacheca bacheca;
    private Utente utente;
    private Attivita attivita;
    private String titoloBacheca;
    private String titoloToDoCorrente;
    private String descrizioneBacheca;


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
        utente.getBacheca(getTitoloBacheca()).eliminaToDoGUI(t);
    }

    public void cambiaTitoloToDo(String t)
    {
        utente.getBacheca(getTitoloBacheca()).getToDoTitolo(getTitoloToDoCorrente()).setTitolo(t);
    }

    public void cambiaDescToDo(String s){
        utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setDescrizione(s);
    }

    public void cambiaURLToDo(String s){
        utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setUrl(s);
    }

    public boolean cambiaDataScadToDo(String s){
        try{
            utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setDataScadenza(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void cambiaBgColorToDo(Color c){
        utente.getBacheca(titoloBacheca).getToDoTitolo(titoloToDoCorrente).setColoreSfondo(c);
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

    /*public String getDescrizioneBacheca() {
        return descrizioneBacheca;
    }*/

    public void setTitoloBacheca(String titoloBacheca) {
        this.titoloBacheca = titoloBacheca;
    }

    public boolean checkBacheca (String nomeBacheca){
        if (utente.getBacheca(nomeBacheca) == null)
            return false;
        else
            return true;
    }

    public String getDescrizioneBacheca(String t){
        return utente.getBacheca(t).getDescrizione();
    }

    public boolean noBacheca(){
        if (utente.getBacheca(TitoloBacheca.LAVORO.toString())==null && utente.getBacheca(TitoloBacheca.TEMPO_LIBERO.toString())==null && utente.getBacheca(TitoloBacheca.UNIVERSITA.toString())==null )
            return true;
        else
            return false;
    }


}
