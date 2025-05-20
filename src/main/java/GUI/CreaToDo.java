package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreaToDo {
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
    private JPanel eliminapanel;
    private JPanel confermapanel;
    private JPanel indietropanel;

    private JTextField imgTextField;

    private JButton eliminaButton;
    private JButton confermaButton;
    private JButton indietroButton;
    private JPanel imgPanel;
    private JLabel imgJL;
    private JPanel bgColorPanel;
    private JLabel bgColorJL;
    private JButton setCompletoButton;
    private JPanel completatoPanel;
    private JLabel imgCompletatoJL;
    private JLabel fotoJL;
    private JButton bgColorButton;
    private Color c;

    private Controller controller;

    public CreaToDo(JFrame frame, Controller controller) {
        this.frame = new JFrame(controller.getTitoloToDoCorrente());
        this.controller = controller;
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);
        this.initListeners();
        this.frame.setVisible(true);
        this.titoloTextField.setText(controller.getTitoloToDoCorrente());
    }

    public void initListeners() {
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (titoloTextField.getText().trim().isEmpty())
                    JOptionPane.showMessageDialog(frame, "TITOLO OBBLIGATORIO!");
                else {
                    controller.cambiaTitoloToDo(titoloTextField.getText());
                    controller.cambiaDescToDo(descTextField.getText());
                    controller.cambiaDataScadToDo(dataScadTextField.getText());
                    controller.cambiaBgColorToDo(c);
                    controller.cambiaURLToDo(urlTextField.getText());

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

                controller.cambiaTitoloToDo(titoloTextField.getText());
                controller.eliminaToDo(titoloTextField.getText());
                BachecaGUI bachecagui = new BachecaGUI(frame, controller);
                frame.dispose();
                frame.setVisible(false);
                bachecagui.getFrame().setVisible(true);
            }
        });

        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BachecaGUI b = new BachecaGUI(frame, controller);
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
            }
        });
    }


public JFrame getFrame() {
        return frame;
    }
}
