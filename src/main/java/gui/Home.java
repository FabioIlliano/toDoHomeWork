package gui;

import controller.Controller;
import dao.BachecaDAO;
import implementazioniPostgresDAO.BachecaImplementazionePostgresDAO;
import model.Bacheca;
import model.TitoloBacheca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Schermata principale, permette di visualizzare, creare ed eliminare le bacheche.
 */
public class Home {
    private JFrame frame;

    private JPanel HomePanel;
    private JPanel panel1;
    private JPanel panel3;
    private JPanel panel2;
    private JPanel buttonPanel;
    private JPanel panelElimina;

    private JButton buttonU;
    private JScrollPane scrollU;
    private JButton buttonEditU;

    private JButton buttonL;
    private JScrollPane scrollL;
    private JButton buttonEditL;

    private JButton buttonTL;
    private JScrollPane scrollTL;
    private JButton buttonEditTL;

    private JButton buttonNuovaBacheca;
    private JButton buttonEliminaBacheca;

    private JTextArea descrizioneU;
    private JTextArea descrizioneL;
    private JTextArea descrizioneTL;
    private JButton logoutbtn;

    private Controller controller;

    private JComboBox<TitoloBacheca> comboBox;


    /**
     * instanzia una nuova home
     *
     * @param controller il controller
     */
    public Home(Controller controller) {

        this.frame = new JFrame("Home");
        this.controller = controller;
        this.frame.setContentPane(HomePanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.pack();
        this.frame.setSize(800, 800);
        this.frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        panelElimina = new JPanel();
        this.initListeners();
        this.initDatiUtente();
        this.initDescrizioni();

        if (controller.checkBacheche())
        {
            panel1.setBackground(Color.red);
            panel2.setBackground(Color.red);
            panel3.setBackground(Color.red);
        }
    }

    /**
     * Init listeners.
     */
    public void initListeners (){
        this.initButtonNuovaBacheca();
        this.initELiminaBacheca();
        this.initEditButtonsListeners();
        this.initLogOutbutton();
    }

    /**
     * Init button nuova bacheca.
     */
    public void initButtonNuovaBacheca(){
        buttonNuovaBacheca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.checkBacheche()){
                    CreaBacheca creaBacheca = new CreaBacheca(controller);
                    frame.setVisible(false);
                    creaBacheca.getFrame().setVisible(true);
                }
                else
                    JOptionPane.showMessageDialog(frame, "BACHECHE GIA CREATE!!");
            }
        });

        ActionListener bachecaListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton pulsante = (JButton) e.getSource();
                if (controller.checkBacheca(pulsante.getText())){
                    controller.setTitoloBacheca(pulsante.getText());
                    GestisciBacheca gestisciBacheca = new GestisciBacheca(controller);
                    gestisciBacheca.getFrame().setVisible(true);
                    frame.dispose();
                }
                else
                    JOptionPane.showMessageDialog(frame, "BACHECA INESISTENTE!!");

            }
        };

        buttonL.addActionListener(bachecaListener);
        buttonTL.addActionListener(bachecaListener);
        buttonU.addActionListener(bachecaListener);
    }

    /**
     * Init elimina bacheca.
     */
    public void initELiminaBacheca(){
        buttonEliminaBacheca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!controller.noBacheca()){
                    panelElimina.removeAll();
                    panelElimina.add(new JLabel("Seleziona la bacheca da eliminare"));
                    initComboBox();
                    panelElimina.add(comboBox);
                    int result = JOptionPane.showConfirmDialog(frame, panelElimina, "Scegli la bacheca", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION){
                        if (!controller.checkBacheca(comboBox.getSelectedItem().toString())){
                            JOptionPane.showMessageDialog(frame, "BACHECA INESISTENTE!");
                            return;
                        }
                        int r = JOptionPane.showConfirmDialog(frame, "Sei sicuro di voler eliminare la bacheca\n"+
                                "Eliminare una bacheca comporta l'eliminazione di tutti i suoi ToDo e questa azione non è reversibile.", "ELIMINA BACHECA", JOptionPane.OK_CANCEL_OPTION);
                        if (r == JOptionPane.OK_OPTION){
                            String s = comboBox.getSelectedItem().toString();
                            boolean b = controller.eliminaBacheca(TitoloBacheca.valueOf(s));
                            if (b){
                                JOptionPane.showMessageDialog(frame, "BACHECA ELIMINATA!");
                                if (s.equals(TitoloBacheca.UNIVERSITA.toString()))
                                    descrizioneU.setText("");
                                if(s.equals(TitoloBacheca.LAVORO.toString()))
                                    descrizioneL.setText("");
                                if(s.equals((TitoloBacheca.TEMPO_LIBERO.toString())))
                                    descrizioneTL.setText("");
                            }
                            else
                                JOptionPane.showMessageDialog(frame, "BACHECA NON ELIMINATA CORRETTAMENTE!");
                        }
                    }
                }
                else
                    JOptionPane.showMessageDialog(frame, "BACHECHE INESISTENTI!!");

            }
        });

    }

    /**
     * Init descrizioni.
     */
    public void initDescrizioni (){
        if (controller.checkBacheca(TitoloBacheca.UNIVERSITA.toString())){
            String s = controller.getDescrizioneBacheca(TitoloBacheca.UNIVERSITA.toString());
            if (s == null || s.trim().isEmpty())
                descrizioneU.setText(null);
            else
                descrizioneU.setText(s);
        }
        if (controller.checkBacheca(TitoloBacheca.TEMPO_LIBERO.toString())){
            String s = controller.getDescrizioneBacheca(TitoloBacheca.TEMPO_LIBERO.toString());
            if (s == null || s.trim().isEmpty())
                descrizioneTL.setText(null);
            else
                descrizioneTL.setText(s);
        }
        if (controller.checkBacheca(TitoloBacheca.LAVORO.toString())){
            String s = controller.getDescrizioneBacheca(TitoloBacheca.LAVORO.toString());
            if (s == null || s.trim().isEmpty())
                descrizioneL.setText(null);
            else
                descrizioneL.setText(s);
        }
    }

    /**
     * Init edit buttons listeners.
     */
    public void initEditButtonsListeners(){
        ActionListener editListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton pulsante = (JButton) e.getSource();
                String tipo = pulsante.getToolTipText();

                switch (tipo){
                    case "UNIVERSITA":
                        if (controller.checkBacheca(TitoloBacheca.UNIVERSITA.toString())){
                            if (descrizioneU.isEditable()){
                                pulsante.setIcon(new ImageIcon(getClass().getResource("/edit64.png")));
                                descrizioneU.setEditable(false);
                                String s = descrizioneU.getText();
                                if (s.trim().isEmpty())
                                    s = null;
                                controller.cambiaDescrizioneBacheca(TitoloBacheca.UNIVERSITA, s);
                            }
                            else{
                                pulsante.setIcon(new ImageIcon(getClass().getResource("/save-icon-64.png")));
                                descrizioneU.setEditable(true);
                                descrizioneU.requestFocus();
                            }
                        }
                        else
                            JOptionPane.showMessageDialog(frame, "BACHECA INESISTENTE!!");
                        break;
                    case "LAVORO":
                        if (controller.checkBacheca(TitoloBacheca.LAVORO.toString())){
                            if (descrizioneL.isEditable()){
                                pulsante.setIcon(new ImageIcon(getClass().getResource("/edit64.png")));
                                descrizioneL.setEditable(false);
                                String s = descrizioneL.getText();
                                if (s.trim().isEmpty())
                                    s = null;
                                controller.cambiaDescrizioneBacheca(TitoloBacheca.LAVORO, s);
                            }
                            else{
                                pulsante.setIcon(new ImageIcon(getClass().getResource("/save-icon-64.png")));
                                descrizioneL.setEditable(true);
                                descrizioneL.requestFocus();
                            }
                        }
                        else
                            JOptionPane.showMessageDialog(frame, "BACHECA INESISTENTE!!");
                        break;
                    case "TEMPO_LIBERO":
                        if (controller.checkBacheca(TitoloBacheca.TEMPO_LIBERO.toString())){
                            if (descrizioneTL.isEditable()){
                                pulsante.setIcon(new ImageIcon(getClass().getResource("/edit64.png")));
                                descrizioneTL.setEditable(false);
                                String s = descrizioneTL.getText();
                                if (s.trim().isEmpty())
                                    s = null;
                                controller.cambiaDescrizioneBacheca(TitoloBacheca.TEMPO_LIBERO, s);
                            }
                            else{
                                pulsante.setIcon(new ImageIcon(getClass().getResource("/save-icon-64.png")));
                                descrizioneTL.setEditable(true);
                                descrizioneTL.requestFocus();
                            }
                        }
                        else
                            JOptionPane.showMessageDialog(frame, "BACHECA INESISTENTE!!");
                        break;

                    default:
                        JOptionPane.showMessageDialog(frame, "BACHECA INESISTENTE!!");
                }

            }
        };

        buttonEditU.addActionListener(editListener);
        buttonEditL.addActionListener(editListener);
        buttonEditTL.addActionListener(editListener);
    }

    public void initLogOutbutton(){
        logoutbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.logout();
                StartPage start = new StartPage();
                frame.setVisible(false);
                start.getFrame().setVisible(true);
            }
        });
    }

    public void initDatiUtente(){
        try{
            controller.caricaBacheche();
        }catch (Exception e){
            JOptionPane.showMessageDialog(frame, "ERRORE NEL CARICAMENTO DEI DATI", "ERRORE", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initComboBox(){
        comboBox = new JComboBox<>();
        try{
            BachecaDAO b = new BachecaImplementazionePostgresDAO();
            ArrayList<String> a = b.getTitoliUtente(controller.getUtente().getUsername());
            for (String s : a)
                comboBox.addItem(TitoloBacheca.valueOf(s));
        }
        catch (Exception e){
            comboBox.addItem(TitoloBacheca.UNIVERSITA);
            comboBox.addItem(TitoloBacheca.LAVORO);
            comboBox.addItem(TitoloBacheca.TEMPO_LIBERO);
        }
    }


    /**
     * restituisce il frame.
     *
     * @return il frame
     */
    public JFrame getFrame() {
        return frame;
    }
}
