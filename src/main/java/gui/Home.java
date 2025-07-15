package gui;

import controller.Controller;
import dao.BachecaDAO;
import implementazioniPostgresDAO.BachecaImplementazionePostgresDAO;
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
    private final JFrame frame;

    private JPanel homePanel;
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

    public static final String MSG_BACHECA_INESISTENTE = "BACHECA INESISTENTE!!";


    /**
     * Istanzia una nuova home
     *
     * @param controller il controller
     */
    public Home(Controller controller) {

        this.frame = new JFrame("Home");
        this.controller = controller;
        this.frame.setContentPane(homePanel);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.pack();
        this.frame.setSize(800, 850);
        this.frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        panelElimina = new JPanel();
        this.initListeners();
        this.initDatiUtente();
        this.initDescrizioni();
        this.initColori();
    }

    /**
     * Inizializza tutti i listener dei pulsanti presenti nella schermata.
     */
    public void initListeners (){
        this.initButtonNuovaBacheca();
        this.initELiminaBacheca();
        this.initEditButtonsListeners();
        this.initLogOutButton();
    }

    /**
     * Imposta il colore dei pannelli in base alla presenza delle bacheche.
     * Verde se presente, rosso se assente.
     */
    public void initColori(){
        if (controller.checkBacheca(TitoloBacheca.UNIVERSITA.toString()))
            panel1.setBackground(new Color(168,230,163));
        else
            panel1.setBackground(new Color(255,107,107));
        if(controller.checkBacheca(TitoloBacheca.LAVORO.toString()))
            panel2.setBackground(new Color(168,230,163));
        else
            panel2.setBackground(new Color(255,107,107));
        if(controller.checkBacheca(TitoloBacheca.TEMPO_LIBERO.toString()))
            panel3.setBackground(new Color(168,230,163));
        else
            panel3.setBackground(new Color(255,107,107));
    }

    /**
     * Aggiunge un listener al pulsante "Nuova Bacheca" e ai pulsanti delle bacheche.
     * Mostra un messaggio se tutte le bacheche sono già state create.
     */
    public void initButtonNuovaBacheca(){
        buttonNuovaBacheca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.checkBacheche()){
                    CreaBacheca creaBacheca = new CreaBacheca(frame, controller);
                    creaBacheca.setVisible(true);
                    initColori();
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
                    JOptionPane.showMessageDialog(frame, MSG_BACHECA_INESISTENTE);

            }
        };

        buttonL.addActionListener(bachecaListener);
        buttonTL.addActionListener(bachecaListener);
        buttonU.addActionListener(bachecaListener);
    }

    /**
     * Aggiunge il listener al pulsante per eliminare una bacheca.
     * Mostra un menu di conferma prima dell'eliminazione.
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
                            JOptionPane.showMessageDialog(frame, MSG_BACHECA_INESISTENTE);
                            return;
                        }
                        int r = JOptionPane.showConfirmDialog(frame, "Sei sicuro di voler eliminare la bacheca\n"+
                                "Eliminare una bacheca comporta l'eliminazione di tutti i suoi ToDo e questa azione non è reversibile.", "ELIMINA BACHECA", JOptionPane.OK_CANCEL_OPTION);
                        if (r == JOptionPane.OK_OPTION){
                            String s = comboBox.getSelectedItem().toString();
                            boolean b = controller.eliminaBacheca(TitoloBacheca.valueOf(s));
                            if (b) {
                                JOptionPane.showMessageDialog(frame, "BACHECA ELIMINATA!");
                                switch (TitoloBacheca.valueOf(s)) {
                                    case UNIVERSITA:
                                        descrizioneU.setText("");
                                        break;
                                    case LAVORO:
                                        descrizioneL.setText("");
                                        break;
                                    case TEMPO_LIBERO:
                                        descrizioneTL.setText("");
                                        break;
                                }
                                initColori();
                            } else {
                                JOptionPane.showMessageDialog(frame, "BACHECA NON ELIMINATA CORRETTAMENTE!");
                            }
                        }
                    }
                }
                else
                    JOptionPane.showMessageDialog(frame, "BACHECHE INESISTENTI!!");

            }
        });

    }

    /**
     * Carica e visualizza le descrizioni delle bacheche esistenti.
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
     * Aggiunge i listener ai pulsanti per modificare le descrizioni.
     * Ogni pulsante attiva la modifica della relativa descrizione.
     */
    public void initEditButtonsListeners(){
        ActionListener editListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton pulsante = (JButton) e.getSource();
                String tipo = pulsante.getToolTipText();

                switch (tipo){
                    case "UNIVERSITA":
                        modificaDescrizioneBacheca(pulsante, descrizioneU, TitoloBacheca.UNIVERSITA);
                        break;
                    case "LAVORO":
                        modificaDescrizioneBacheca(pulsante, descrizioneL, TitoloBacheca.LAVORO);
                        break;
                    case "TEMPO_LIBERO":
                        modificaDescrizioneBacheca(pulsante, descrizioneTL, TitoloBacheca.TEMPO_LIBERO);
                        break;

                    default:
                        JOptionPane.showMessageDialog(frame, MSG_BACHECA_INESISTENTE);
                }

            }
        };

        buttonEditU.addActionListener(editListener);
        buttonEditL.addActionListener(editListener);
        buttonEditTL.addActionListener(editListener);
    }

    /**
     * Gestisce la modifica della descrizione della bacheca.
     * Cambia l'icona del pulsante e aggiorna il testo se necessario.
     *
     * @param pulsante    il pulsante premuto
     * @param descrizione l'area di testo della descrizione
     * @param tipo        il tipo di bacheca da modificare
     */
    public void modificaDescrizioneBacheca(JButton pulsante, JTextArea descrizione, TitoloBacheca tipo) {
        if (controller.checkBacheca(tipo.toString())) {
            if (descrizione.isEditable()) {
                pulsante.setIcon(new ImageIcon(getClass().getResource("/edit64.png")));
                descrizione.setEditable(false);
                String s = descrizione.getText();
                if (s.trim().isEmpty())
                    s = null;
                boolean b = controller.cambiaDescrizioneBacheca(tipo, s);
                if (!b)
                    JOptionPane.showMessageDialog(frame, "ERRORE NEL CAMBIAMENTO DELLA BACHECA", "ERRORE", JOptionPane.ERROR_MESSAGE);
            } else {
                pulsante.setIcon(new ImageIcon(getClass().getResource("/save-icon-64.png")));
                descrizione.setEditable(true);
                descrizione.requestFocus();
            }
        } else {
            JOptionPane.showMessageDialog(frame, MSG_BACHECA_INESISTENTE);
        }
    }

    /**
     * Aggiunge il listener al pulsante di logout.
     * Effettua il logout e torna alla schermata iniziale.
     */
    public void initLogOutButton(){
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

    /**
     * Carica le bacheche dell'utente all'avvio della schermata.
     * Mostra un messaggio d'errore in caso di problemi.
     */
    public void initDatiUtente(){
        try{
            controller.caricaBacheche();
        }catch (Exception _){
            JOptionPane.showMessageDialog(frame, "ERRORE NEL CARICAMENTO DEI DATI", "ERRORE", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Inizializza la combo box con le bacheche dell’utente per l’eliminazione.
     * Se fallisce, carica i titoli predefiniti.
     */
    public void initComboBox(){
        comboBox = new JComboBox<>();
        try{
            BachecaDAO b = new BachecaImplementazionePostgresDAO();
            ArrayList<String> a = b.getTitoliUtente(controller.getUtente().getUsername());
            for (String s : a)
                comboBox.addItem(TitoloBacheca.valueOf(s));
        }
        catch (Exception _){
            comboBox.addItem(TitoloBacheca.UNIVERSITA);
            comboBox.addItem(TitoloBacheca.LAVORO);
            comboBox.addItem(TitoloBacheca.TEMPO_LIBERO);
        }
    }

    /**
     * Restituisce il frame principale della schermata iniziale.
     *
     * @return il {@code JFrame} della start page
     */
    public JFrame getFrame() {
        return frame;
    }
}
