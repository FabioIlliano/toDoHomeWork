package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home {
    public JFrame frame; //va fatto private
    private JPanel HomePanel;
    private JPanel panel1;
    private JPanel panel3;
    private JPanel panel2;
    private JButton buttonL;
    private JButton buttonTL;
    private JButton buttonU;
    private JButton buttonNuovaBacheca;
    private Controller controller;



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
        this.frame.pack();
        this.frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        this.initListeners();
        this.frame.setVisible(true);


    }

    public void initListeners (){
        buttonNuovaBacheca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.checkBacheche()){
                    CreaBacheca creaBacheca = new CreaBacheca(frame, controller);
                    frame.setVisible(false);
                    creaBacheca.frame.setVisible(true);
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
                    bachecaGUI.frame.setVisible(true);
                }
                else
                    JOptionPane.showMessageDialog(frame, "BACHECA INESISTENTE!!");

            }
        };

        buttonL.addActionListener(bachecaListener);
        buttonTL.addActionListener(bachecaListener);
        buttonU.addActionListener(bachecaListener);
    }
}
