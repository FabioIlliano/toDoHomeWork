package GUI;

import Controller.Controller;
import model.TitoloBacheca;
import model.Attivita;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
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
    private JButton attivitaButton;
    private JPanel attivitaPanel;
    private JComboBox spostaJCB;
    private JPanel panelSposta;
    private Color c;

    private Controller controller;

    /**
     * instanzia una nuova schermata di gestione dei ToDo.
     *
     * @param frame      il frame
     * @param controller il controller
     */
    public GestisciToDo(JFrame frame, Controller controller) {
        this.frame = new JFrame(controller.getTitoloToDoCorrente());
        this.controller = controller;
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);
        this.initListeners();
        this.initText();
        this.frame.setVisible(true);
        this.titoloTextField.setText(controller.getTitoloToDoCorrente());
        this.attivitaPanel.setLayout(new BoxLayout(attivitaPanel, BoxLayout.Y_AXIS));
        //spostaJCB.setSelectedItem(TitoloBacheca.valueOf(controller.getTitoloBacheca()));
        caricaAttivitaChecklist(); // <-- aggiorna la lista visivamente
    }

    /**
     * Questo metodo contiene tutti i listener degli oggetti grafici presenti in GestisciToDo
     */
    public void initListeners() {
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (titoloTextField.getText().trim().isEmpty())
                    JOptionPane.showMessageDialog(frame, "TITOLO OBBLIGATORIO!");
                else {
                    controller.cambiaTitoloToDo(titoloTextField.getText());
                    controller.cambiaDescToDo(descTextField.getText());
                    if (!dataScadTextField.getText().isEmpty()){
                        boolean b = controller.cambiaDataScadToDo(dataScadTextField.getText());
                        if (!b)
                            JOptionPane.showMessageDialog(frame, "FORMATO DATA NON VALIDO, FORMATO CORRETTO: dd-MM-yyyy");
                        else if (!controller.checkData(dataScadTextField.getText()))
                            JOptionPane.showMessageDialog(frame, "DATA PRECEDENTE AD OGGI!!");
                    }
                    controller.cambiaBgColorToDo(c);
                    controller.cambiaURLToDo(urlTextField.getText());
                    initText();
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
                        controller.setIMGToDo(img);
                    }else
                        JOptionPane.showMessageDialog(frame, "SELEZIONE UN FILE IMMAGINE (PNG, JPG, JPEG, GIF)");
                }
            }
        });

        imgJL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //fare metodo che apre una nuova interfaccia e fa visualizzare la foto nella sua interezza
            }
        });

        eliminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                controller.cambiaTitoloToDo(titoloTextField.getText());
                controller.eliminaToDo(titoloTextField.getText());
                GestisciBacheca gestisciBacheca = new GestisciBacheca(frame, controller);
                frame.dispose();
                frame.setVisible(false);
                gestisciBacheca.getFrame().setVisible(true);
            }
        });

        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestisciBacheca b = new GestisciBacheca(frame, controller);
                frame.setVisible(false);
                b.getFrame().setVisible(true);
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
                        GestisciBacheca gestisciBacheca = new GestisciBacheca(frame, controller);
                        gestisciBacheca.getFrame().setVisible(true);
                        frame.dispose();
                    }
                    else
                        JOptionPane.showMessageDialog(frame, "TODO NON SPOSTATO CORRETTAMENTE");

                }
            }
        });

        attivitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeAttivita = JOptionPane.showInputDialog(frame, "Inserisci il nome dell'attività:");
                if (nomeAttivita != null && !nomeAttivita.trim().isEmpty()) {
                    controller.creaAttivita(nomeAttivita);

                    caricaAttivitaChecklist(); // <-- aggiorna la lista visivamente
                }
            }
        });

    }

    /**
     * inizializza tutti i campi del ToDo.
     */
    public void initText(){
        checklistJP.setLayout(new BoxLayout(checklistJP, BoxLayout.Y_AXIS)); // <-- AGGIUNGI QUESTO

        c = controller.getBGColorToDo();
        this.titoloTextField.setText(controller.getTitoloToDoCorrente());
        this.descTextField.setText(controller.getDescrizioneToDo());
        this.dataScadTextField.setText(controller.getDataScadToDo());
        this.urlTextField.setText(controller.getUrlToDo());

        if (controller.getCompletoToDo())
            imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource("/completed64.png")));
        else
            imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource("/notCompleted64.png")));

        if (controller.getIMGToDo()!=null)
            imgJL.setIcon(new ImageIcon(controller.getIMGToDo()));
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
        attivitaPanel.removeAll();

        if(a==null || a.isEmpty())
        {
            return;
        }

        for (Attivita attivita : a) {

            JCheckBox checkBox = new JCheckBox(attivita.getNome());
            checkBox.addItemListener(new ItemListener() {
            @Override

            public void itemStateChanged(ItemEvent e) {
                if (checkBox.isSelected()) {
                    controller.setStato(checkBox.getText());
                    if (controller.checkChecklist())
                        controller.setCompletoToDo(true);
                } else {
                    controller.setStato(checkBox.getText());
                    if (!controller.checkChecklist())
                        controller.setCompletoToDo(false);
                }
            }

            });
            //checkBox.setAlignmentX(Component.LEFT_ALIGNMENT);

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
