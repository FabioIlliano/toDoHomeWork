package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JPanel buttonpanel;
    private JPanel eliminapanel;
    private JPanel confermapanel;
    private JButton eliminaButton;
    private JButton confermaButton;
    private Controller controller;

    public CreaToDo(JFrame frame, Controller controller) {
        this.frame = new JFrame(controller.getTitoloBacheca());
        this.controller = controller;
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);
        this.initListeners();
        this.frame.setVisible(true);
        this.textField1.setText(controller.getTitoloToDoCorrente());
    }

    public void initListeners() {
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText().trim().isEmpty())
                {
                    JOptionPane.showMessageDialog(frame, "TITOLO OBBLIGATORIO!");
                }
                else {
                    controller.cambiaTitoloToDo(textField1.getText());
                    BachecaGUI bachecagui = new BachecaGUI(frame, controller);
                    frame.dispose();
                    frame.setVisible(false);
                    bachecagui.getFrame().setVisible(true);
                }
            }
        });

        eliminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                controller.cambiaTitoloToDo(textField1.getText());
                controller.eliminaToDo(textField1.getText());
                BachecaGUI bachecagui = new BachecaGUI(frame, controller);
                frame.dispose();
                frame.setVisible(false);
                bachecagui.getFrame().setVisible(true);
            }
        });
    }


public JFrame getFrame() {
        return frame;
    }
}
