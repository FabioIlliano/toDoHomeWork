package GUI;

import Controller.Controller;
import model.TitoloBacheca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private Controller controller;

    private JComboBox<TitoloBacheca> comboBox;







    public Home(JFrame frame, Controller controller) {

        if (controller.checkBacheche())
        {
            panel1.setBackground(Color.red);
            panel2.setBackground(Color.red);
            panel3.setBackground(Color.red);
        }

        this.frame = new JFrame("Home");
        this.controller = controller;
        this.frame.setContentPane(HomePanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.pack();
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        comboBox = new JComboBox<>();
        comboBox.addItem(TitoloBacheca.UNIVERSITA);
        comboBox.addItem(TitoloBacheca.LAVORO);
        comboBox.addItem(TitoloBacheca.TEMPO_LIBERO);
        panelElimina = new JPanel();
        this.initListeners();
        this.initDescrizioni();
    }

    public void initListeners (){
        this.initButtonNuovaBacheca();
        this.initELiminaBacheca();
        this.initEditButtonsListeners();
    }

    public void initButtonNuovaBacheca(){
        buttonNuovaBacheca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.checkBacheche()){
                    CreaBacheca creaBacheca = new CreaBacheca(frame, controller);
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
                    BachecaGUI bachecaGUI = new BachecaGUI(frame, controller);
                    frame.setVisible(false);
                    bachecaGUI.getFrame().setVisible(true);
                }
                else
                    JOptionPane.showMessageDialog(frame, "BACHECA INESISTENTE!!");

            }
        };

        buttonL.addActionListener(bachecaListener);
        buttonTL.addActionListener(bachecaListener);
        buttonU.addActionListener(bachecaListener);
    }

    public void initELiminaBacheca(){
        buttonEliminaBacheca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!controller.noBacheca()){
                    panelElimina.removeAll();
                    panelElimina.add(new JLabel("Seleziona la bacheca da eliminare"));
                    panelElimina.add(comboBox);
                    int result = JOptionPane.showConfirmDialog(frame, panelElimina, "Scegli la bacheca", JOptionPane.OK_CANCEL_OPTION);
                    if (result == JOptionPane.OK_OPTION){
                        if (!controller.checkBacheca(comboBox.getSelectedItem().toString())){
                            JOptionPane.showMessageDialog(frame, "BACHECA INESISTENTE!");
                            return;
                        }
                        int r = JOptionPane.showConfirmDialog(frame, "Sei sicuro di voler eliminare la bacheca",
                                "Eliminare una bacheca comporta l'eliminazione di tutti i suoi ToDo e questa azione non è reversibile.", JOptionPane.OK_CANCEL_OPTION);
                        if (r == JOptionPane.OK_OPTION){
                            String s = comboBox.getSelectedItem().toString();
                            boolean b = controller.getUtente().eliminaBachecaGUI(s);
                            if (b){
                                JOptionPane.showMessageDialog(frame, "BACHECA ELIMINATA!");
                                if (s.equals("UNIVERSITA"))
                                    descrizioneU.setText("");
                                if(s.equals("LAVORO"))
                                    descrizioneL.setText("");
                                if(s.equals(("TEMPO_LIBERO")))
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

    public void initDescrizioni (){
        if (controller.checkBacheca(TitoloBacheca.UNIVERSITA.toString())){
            String s = controller.getDescrizioneBacheca(TitoloBacheca.UNIVERSITA.toString());
            descrizioneU.setText(s);
        }
        if (controller.checkBacheca(TitoloBacheca.TEMPO_LIBERO.toString())){
            String s = controller.getDescrizioneBacheca(TitoloBacheca.TEMPO_LIBERO.toString());
            descrizioneTL.setText(s);
        }
        if (controller.checkBacheca(TitoloBacheca.LAVORO.toString())){
            String s = controller.getDescrizioneBacheca(TitoloBacheca.LAVORO.toString());
            descrizioneL.setText(s);
        }
    }

    public void initEditButtonsListeners(){
        ActionListener editListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton pulsante = (JButton) e.getSource();
                String tipo = pulsante.getToolTipText();

                switch (tipo){
                    case "UNIVERSITA":
                        if (controller.checkBacheca("UNIVERSITA")){
                            if (descrizioneU.isEditable()){
                                pulsante.setIcon(new ImageIcon(getClass().getResource("/edit64.png")));
                                descrizioneU.setEditable(false);
                                String s = descrizioneU.getText();
                                controller.getUtente().getBacheca("UNIVERSITA").setDescrizione(s);
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
                        if (controller.checkBacheca("LAVORO")){
                            if (descrizioneL.isEditable()){
                                pulsante.setIcon(new ImageIcon(getClass().getResource("/edit64.png")));
                                descrizioneL.setEditable(false);
                                String s = descrizioneL.getText();
                                controller.getUtente().getBacheca("LAVORO").setDescrizione(s);
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
                        if (controller.checkBacheca("TEMPO_LIBERO")){
                            if (descrizioneTL.isEditable()){
                                pulsante.setIcon(new ImageIcon(getClass().getResource("/edit64.png")));
                                descrizioneTL.setEditable(false);
                                String s = descrizioneTL.getText();
                                controller.getUtente().getBacheca("TEMPO_LIBERO").setDescrizione(s);
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



    public JFrame getFrame() {
        return frame;
    }
}
