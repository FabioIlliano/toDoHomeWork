package GUI;

import Controller.Controller;

import javax.swing.*;

public class CreaToDo {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel titoloJL;
    private JTextField textField1;
    private JLabel descrizioneJL;
    private JTextField textField2;
    private JLabel ScadenzaJL;
    private JTextField textField3;
    private JLabel URLJL;
    private JTextField textField4;
    private JPanel urlPanel;
    private JPanel titoloPanel;
    private JPanel dataScadPanel;
    private JPanel descPanel;
    private JTextField cTextField;
    private Controller controller;

    public CreaToDo(JFrame frame, Controller controller) {
        this.frame = new JFrame(controller.getTitoloBacheca());
        this.controller = controller;
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        this.initListeners();
        this.frame.setVisible(true);

    }

    public void initListeners() {

    }
}
