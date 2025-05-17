package GUI;

import Controller.Controller;
import model.TitoloBacheca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    private JFrame frame; //va fatto private
    private JPanel HomePanel;
    private JPanel panel1;
    private JPanel panel3;
    private JPanel panel2;
    private JButton buttonL;
    private JButton buttonTL;
    private JButton buttonU;
    private JButton buttonNuovaBacheca;
    private JButton buttonEliminaBacheca;
    private JPanel buttonPanel;
    private Controller controller;
    private JComboBox<TitoloBacheca> comboBox;
    private JPanel panelElimina;



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
    }

    public void initListeners (){
        this.initButtonNuovaBacheca();
        this.initELiminaBacheca();
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
                        boolean b = controller.getUtente().eliminaBachecaGUI(comboBox.getSelectedItem().toString());
                        if (b)
                            JOptionPane.showMessageDialog(frame, "BACHECA ELIMINATA!");
                        else
                            JOptionPane.showMessageDialog(frame, "BACHECA NON ELIMINATA CORRETTAMENTE!");
                    }
                }
            }
        });

    }

    public JFrame getFrame() {
        return frame;
    }
}
