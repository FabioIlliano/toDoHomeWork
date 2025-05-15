package GUI;

import Controller.Controller;

import javax.swing.*;
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
        this.frame = new JFrame("Home");
        this.controller = controller;
        this.frame.setContentPane(HomePanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setVisible(true);
        this.frame.setSize(800, 600);
        this.initListeners();
    }

    public void initListeners (){
        buttonNuovaBacheca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.checkBacheca()){
                    CreaBacheca creaBacheca = new CreaBacheca(frame, controller);
                    frame.setVisible(false);
                    creaBacheca.frame.setVisible(true);
                }
                else
                    JOptionPane.showMessageDialog(frame, "BACHECHE GIA CREATE!!");
            }
        });
    }

}
