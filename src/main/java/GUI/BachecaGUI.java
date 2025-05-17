package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BachecaGUI {
    private JFrame frame;

    private JPanel mainpanel;
    private JButton descrizioneButton;
    private JButton creaToDoButton;
    private JScrollPane todoScrollPanel;
    private JPanel buttonpanel;
    private JPanel todopanel;
    private JButton tornaIndietroButton;
    private Controller controller;
    String titolo;

    public BachecaGUI(JFrame frame, Controller controller) {
        this.frame = new JFrame(controller.getTitoloBacheca());
        this.controller = controller;
        this.frame.setContentPane(mainpanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        this.initListeners();
        this.frame.setVisible(true);

        todopanel.setLayout(new BoxLayout(todopanel, BoxLayout.Y_AXIS));

    }

    public void initListeners (){
        creaToDoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                titolo = JOptionPane.showInputDialog(frame, "Inserisci il titolo del ToDo");
                //modificare immagine nel JOptionPane
                try{
                    if (titolo == null)
                        throw new Exception();
                    if (titolo.trim().isEmpty()) {
                        //stringa vuota
                        throw new NullPointerException();
                    }
                    else
                    {
                        JButton btn = new JButton(titolo);
                        btn.setAlignmentX(Component.LEFT_ALIGNMENT); // per l'allineamento corretto

                        btn.addActionListener(e -> {
                            JOptionPane.showMessageDialog(frame, "Hai cliccato su: todo");
                            // oppure apri una nuova finestra
                        });

                        todopanel.add(btn);           // aggiunta al pannello scrollabile
                        todopanel.revalidate();       // aggiorna il layout
                        todopanel.repaint();
                    }
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(frame, "TITOLO OBBLIGATORIO!");
                } catch (Exception e){

                }




            }
        });

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home home = new Home(frame, controller);
                frame.setVisible(false);
                home.getFrame().setVisible(true);
                frame.dispose();
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }
}
