package GUI;

import Controller.Controller;
import model.TitoloBacheca;

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
    private JLabel spostaJL;
    private JComboBox spostaJCB;
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
        spostaJCB.addItem(TitoloBacheca.UNIVERSITA);
        spostaJCB.addItem(TitoloBacheca.LAVORO);
        spostaJCB.addItem(TitoloBacheca.TEMPO_LIBERO);

        spostaJCB.setSelectedItem(TitoloBacheca.valueOf(controller.getTitoloBacheca()));
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

                    if (controller.getTitoloBacheca().equals(spostaJCB.getSelectedItem())) {
                        JOptionPane.showMessageDialog(null, "Il todo e' gia in questa bacheca!");
                    }
                    else
                    {
                        controller.spostaToDo((TitoloBacheca) spostaJCB.getSelectedItem());
                    }
                }
            }

        });

        /*spostaJCB.addActionListener(new ActionListener() {@Override
        public void actionPerformed(ActionEvent e) {
            TitoloBacheca nuovaBacheca = (TitoloBacheca) spostaJCB.getSelectedItem();

            try {
                controller.spostaToDo(nuovaBacheca);

                JOptionPane.showMessageDialog(null, "ToDo spostato in: " + nuovaBacheca);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                spostaJCB.setSelectedItem(controller.getTitoloBacheca()); // ripristina selezione vecchia
            }
        }});*/

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
