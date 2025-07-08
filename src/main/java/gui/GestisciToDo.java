package gui;

import controller.Controller;
import model.TitoloBacheca;
import model.Attivita;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Gestisci ToDo permette di inserire e modificare tutti i vari dati presenti in un ToDo.
 */
public class GestisciToDo {
    private JFrame frame;
    private JPanel mainPanel;

    private JLabel titoloJL;
    private JTextField titoloTextField;
    private JLabel descrizioneJL;
    private JTextField descTextField;
    private JLabel ScadenzaJL;
    private JTextField dataScadTextField;
    private JLabel urlJL;
    private JTextField urlTextField;

    private JPanel urlPanel;
    private JPanel titoloPanel;
    private JPanel dataScadPanel;
    private JPanel descPanel;
    private JPanel buttonpanel;
    private JPanel btnPanel2;

    private JButton eliminaButton;
    private JButton confermaButton;
    private JButton indietroButton;
    private JPanel imgPanel;
    private JLabel imgJLtext;
    private JPanel bgColorPanel;
    private JLabel bgColorJL;
    private JButton setCompletoButton;
    private JPanel completatoPanel;
    private JLabel imgCompletatoJL;
    private JLabel imgJL;
    private JButton bgColorButton;
    private JPanel btnPanel1;
    private JButton spostaToDobutton;
    private JButton scegliIMGButton;
    private JScrollPane checklistJSP;
    private JLabel checklistJL;
    private JPanel checklistJP;
    private JButton nuovaAttivitaButton;
    private JPanel attivitaPanel;
    private JPanel attibtnPanel;
    private JButton eliminaAttivitaButton;
    private JPanel condividiPanel;
    private JButton condividiBtn;
    private JButton eliminaCondivisioneBtn;
    private JButton mostraCondivisioneBtn;
    private JButton resetImgBtn;
    private JComboBox spostaJCB; //si può fare anche dinamica
    private JPanel panelSposta;
    private Color c;

    private JComboBox<String> condividiJCB;
    private JPanel panelCondivisione;

    private Controller controller;

    /**
     * instanzia una nuova schermata di gestione dei ToDo.
     *
     * @param controller il controller
     */
    public GestisciToDo(Controller controller) {
        this.frame = new JFrame(controller.getTitoloToDoCorrente());
        this.controller = controller;
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 800);
        this.frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        this.initListeners();
        this.initText();
        this.titoloTextField.setText(controller.getTitoloToDoCorrente());
        this.attivitaPanel.setLayout(new BoxLayout(attivitaPanel, BoxLayout.Y_AXIS));
        caricaAttivitaChecklist(); // <-- aggiorna la lista visivamente
    }

    /**
     * Questo metodo contiene tutti i listener degli oggetti grafici presenti in GestisciToDo
     */
    public void initListeners() {
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean stato;
                if (titoloTextField.getText().trim().isEmpty())
                    JOptionPane.showMessageDialog(frame, "TITOLO OBBLIGATORIO!");
                else {
                    controller.cambiaTitoloToDo(titoloTextField.getText());
                    controller.cambiaDescToDo(descTextField.getText());
                    if (!dataScadTextField.getText().isEmpty()){
                        boolean b = controller.cambiaDataScadToDo(dataScadTextField.getText());
                        if (!b)
                            JOptionPane.showMessageDialog(frame, "FORMATO DATA NON VALIDO, FORMATO CORRETTO: dd-MM-yyyy");
                        else if (!controller.checkData(dataScadTextField.getText())) {
                            JOptionPane.showMessageDialog(frame, "DATA PRECEDENTE AD OGGI!!");
                            controller.cambiaDataScadToDo("");
                        }
                    }
                    else
                        controller.cambiaDataScadToDo("");

                    controller.cambiaBgColorToDo(c);
                    controller.cambiaURLToDo(urlTextField.getText());
                    try{
                        controller.aggiornaChecklist(controller.getListaAttivitaLocale());
                        if (controller.checkChecklist())
                            controller.getToDo().setStato(true);
                        controller.aggiornaToDo();
                        //funziona tutto correttamente ma si deve inserire un JOptionPane che avvisa che il todo viene completato in automatico.
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        if (e1.getSQLState().equals("P0001")){
                            JOptionPane.showMessageDialog(frame, "BISOGNA COMPLETARE TUTTE LE ATTIVITA PRIMA DI COMPLETARE IL TODO", "ERRORE", JOptionPane.ERROR_MESSAGE);
                            imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource("/notCompleted64.png")));
                        }
                    }

                }
            }
        });


        scegliIMGButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(true);
                int r = fileChooser.showOpenDialog(frame);
                if (r == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String nomeFile = file.getName();
                    if (nomeFile.endsWith(".png") || nomeFile.endsWith(".jpg") || nomeFile.endsWith(".jpeg") || nomeFile.endsWith(".gif")){
                        ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                        Image img = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                        imgJL.setIcon(new ImageIcon(img));
                        controller.setIMGToDo(img, file.getAbsolutePath());
                    }else
                        JOptionPane.showMessageDialog(frame, "SELEZIONE UN FILE IMMAGINE (PNG, JPG, JPEG, GIF)");
                }
            }
        });

        resetImgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.getIMGToDo()==null || controller.getImgPath()==null || controller.getImgPath().isEmpty())
                    JOptionPane.showMessageDialog(frame, "IMMAGINE GIÀ INESISTENTE ");
                else{
                    controller.setIMGToDo(null, "");
                    imgJL.setIcon(new ImageIcon(getClass().getResource("/no-image64.png")));
                    JOptionPane.showMessageDialog(frame, "IMMAGINE RESETTATA");
                }
            }
        });

        imgJL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller.getImgPath() == null || controller.getImgPath().isEmpty() || controller.getIMGToDo() == null){
                    JOptionPane.showMessageDialog(frame, "NESSUN IMMAGINE NON ASSOCIATA A QUESTO TODO", "ERRORE", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                VisualizzaImg visualizzaImg = new VisualizzaImg(controller);
                visualizzaImg.getFrame().setVisible(true);
                frame.dispose();
            }
        });

        eliminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(frame,"SEI SICURO DI VOLER ELIMINARE IL TODO?", "ELIMINA TODO", JOptionPane.OK_CANCEL_OPTION);
                if (result==JOptionPane.OK_OPTION){
                    controller.eliminaToDo();
                    GestisciBacheca gestisciBacheca = new GestisciBacheca(controller);
                    gestisciBacheca.getFrame().setVisible(true);
                    frame.dispose();
                }

            }
        });

        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestisciBacheca b = new GestisciBacheca(controller);
                b.getFrame().setVisible(true);
                frame.dispose();
            }
        });

        bgColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(frame, "Scegli un colore", bgColorPanel.getBackground());
                if (color!=null)
                    c = color;
                else
                    c = controller.getBGColorToDo();
            }
        });

        setCompletoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean b = controller.getCompletoToDo();
                controller.setCompletoToDo(!b);
                if (b)
                    imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource("/notCompleted64.png")));
                else
                    imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource("/completed64.png")));
            }
        });

        spostaToDobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelSposta = new JPanel();
                panelSposta.removeAll();
                panelSposta.add(new JLabel("Seleziona la bacheca in cui spostare il TODO"));
                panelSposta.add(spostaJCB);
                int result = JOptionPane.showConfirmDialog(frame, panelSposta, "Scegli la bacheca", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION){
                    if (!controller.checkBacheca(spostaJCB.getSelectedItem().toString())){
                        JOptionPane.showMessageDialog(frame, "BACHECA INESISTENTE!");
                        return;
                    }
                    TitoloBacheca t = (TitoloBacheca) spostaJCB.getSelectedItem();
                    boolean b = controller.spostaToDo(t);
                    if (b){
                        JOptionPane.showMessageDialog(frame, "TODO SPOSTATO");
                        GestisciBacheca gestisciBacheca = new GestisciBacheca(controller);
                        gestisciBacheca.getFrame().setVisible(true);
                        frame.dispose();
                    }
                    else
                        JOptionPane.showMessageDialog(frame, "TODO NON SPOSTATO CORRETTAMENTE");

                }
            }
        });

        nuovaAttivitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeAttivita = JOptionPane.showInputDialog(frame, "Inserisci il nome dell'attività:");
                if (nomeAttivita != null && !nomeAttivita.trim().isEmpty()) {
                    if (controller.creaAttivita(nomeAttivita) == -1){
                        JOptionPane.showMessageDialog(frame, "CREAZIONE NON ANDATA A BUON FINE", "ERRORE", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    caricaAttivitaChecklist();
                }
            }
        });

        eliminaAttivitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeAttivita = JOptionPane.showInputDialog(frame, "Inserisci il nome dell'attività da eliminare, se esiste verrà eliminata direttamente.", "ELIMINA", JOptionPane.QUESTION_MESSAGE);
                if (nomeAttivita != null && !nomeAttivita.trim().isEmpty()) {
                    if (controller.getAttivita(nomeAttivita)!=null){
                        if (controller.eliminaAttivita(nomeAttivita) == 0) {
                            JOptionPane.showMessageDialog(frame, "Attività eliminata correttamente", "ATTIVITA' ELIMINATA", JOptionPane.INFORMATION_MESSAGE);
                            caricaAttivitaChecklist();
                        }
                        else
                            JOptionPane.showMessageDialog(frame, "Attività non eliminata correttamente", "ATTIVITA' NON ELIMINATA", JOptionPane.INFORMATION_MESSAGE);

                    }
                    else
                        JOptionPane.showMessageDialog(frame, "ATTIVITA INESISTENTE", "", JOptionPane.ERROR_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(frame, "INSERIRE UN TITOLO", "", JOptionPane.ERROR_MESSAGE);
            }
        });

        condividiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String utenteDest = JOptionPane.showInputDialog(frame, "INSERISCI L'USERNAME DELL'UTENTE CON CUI CONDIVIDERE IL TODO", "CONDIVIDI TODO", JOptionPane.QUESTION_MESSAGE);
                if (controller.checkUtente(utenteDest)){
                    int r = controller.condividiToDo(utenteDest);
                    if (r==0)
                        JOptionPane.showMessageDialog(frame, "TODO CONDIVISO CORRETTAMENTE", "CONDIVISIONE", JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(frame, "TODO NON CONDIVISO CORRETTAMENTE", "CONDIVISIONE", JOptionPane.ERROR_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(frame, "UTENTE INESISTENTE!", "CONDIVISIONE", JOptionPane.ERROR_MESSAGE);
            }
        });

        eliminaCondivisioneBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCondivisione = new JPanel();
                panelCondivisione.removeAll();
                panelCondivisione.add(new JLabel("SELEZIONA L'UTENTE CON CUI RIMUOVERE LA CONDIVISIONE"));
                ArrayList<String> listaUtenti = controller.getListaCondivisioni();
                condividiJCB = new JComboBox<>();

                for (String s : listaUtenti)
                    condividiJCB.addItem(s);

                if (listaUtenti.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "TODO NON CONDIVISO CON NESSUNO", "ERRORE", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (listaUtenti.contains(controller.getUtente().getUsername())){
                    JOptionPane.showMessageDialog(frame, "QUESTO TODO È STATO CONDIVISO CON TE, NON PUOI RIMUOVERLO!", "ERRORE", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                panelCondivisione.add(condividiJCB);
                int result = JOptionPane.showConfirmDialog(frame, panelCondivisione, "Scegli l'utente", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION){
                    int r = controller.rimuoviCondivisione(condividiJCB.getSelectedItem().toString());
                    if (r==0)
                        JOptionPane.showMessageDialog(frame, "CONDIVISIONE RIMOSSA!");
                    else
                        JOptionPane.showMessageDialog(frame, "CONDIVISIONE NON RIMOSSA", "ERRORE", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        mostraCondivisioneBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCondivisione = new JPanel();
                panelCondivisione.removeAll();
                panelCondivisione.add(new JLabel("ECCO GLI UTENTI CHE HANNO ACCESSO AL TODO: "));
                ArrayList<String> listaUtenti = controller.getListaCondivisioni();

                if (listaUtenti.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "IL TODO NON È CONDIVISO CON UTENTI", "UTENTI", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                StringBuilder utenti = new StringBuilder("|--");

                for (String s : listaUtenti)
                    utenti.append(s).append("-");
                utenti.append("-|");

                panelCondivisione.add(new JLabel(utenti.toString()));
                JOptionPane.showMessageDialog(frame, panelCondivisione, "UTENTI", JOptionPane.INFORMATION_MESSAGE);
            }
        });

    }

    /**
     * inizializza tutti i campi del ToDo.
     */
    public void initText(){
        checklistJP.setLayout(new BoxLayout(checklistJP, BoxLayout.Y_AXIS));


        this.titoloTextField.setText(controller.getTitoloToDoCorrente());
        this.descTextField.setText(controller.getDescrizioneToDo());
        this.dataScadTextField.setText(controller.getDataScadToDo());
        this.urlTextField.setText(controller.getUrlToDo());

        this.c = controller.getColorBG();

        if (controller.getCompletoToDo())
            imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource("/completed64.png")));
        else
            imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource("/notCompleted64.png")));

        Image imgRidimensionata;

        if (controller.getIMGToDo()!=null){
            imgRidimensionata = controller.getIMGToDo().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            imgJL.setIcon(new ImageIcon(imgRidimensionata));
        }
        else
            imgJL.setIcon(new ImageIcon(getClass().getResource("/no-image64.png")));

        spostaJCB = new JComboBox();
        if (controller.getTitoloBacheca().equals(TitoloBacheca.UNIVERSITA.toString())){
            spostaJCB.addItem(TitoloBacheca.LAVORO);
            spostaJCB.addItem(TitoloBacheca.TEMPO_LIBERO);
        }
        else if (controller.getTitoloBacheca().equals(TitoloBacheca.LAVORO.toString())){
            spostaJCB.addItem(TitoloBacheca.UNIVERSITA);
            spostaJCB.addItem(TitoloBacheca.TEMPO_LIBERO);
        } else {
            spostaJCB.addItem(TitoloBacheca.UNIVERSITA);
            spostaJCB.addItem(TitoloBacheca.LAVORO);
        }
    }

    /**
     * Carica tutte le attivita presenti in una Checklist.
     */
    private void caricaAttivitaChecklist() {

        ArrayList<Attivita> a = controller.getListaAttivita();
        controller.setListaAttivitaLocale(a);
        attivitaPanel.removeAll();

        if(a==null || a.isEmpty())
            return;

        for (Attivita attivita : a) {

            JCheckBox checkBox = new JCheckBox(attivita.getNome());
            checkBox.setSelected(attivita.isStato());
            checkBox.addItemListener(new ItemListener() {
            @Override

            public void itemStateChanged(ItemEvent e) {
                controller.setStatoLocale(attivita.getNome());
            } });

            attivitaPanel.add(checkBox);
        }

        attivitaPanel.revalidate();
        attivitaPanel.repaint();
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
