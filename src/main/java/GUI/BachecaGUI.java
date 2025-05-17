package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        this.frame.setLocationRelativeTo(null);
        this.initListeners();
        this.frame.setVisible(true);
        todopanel.setLayout(new BoxLayout(todopanel, BoxLayout.Y_AXIS));
        caricaToDoEsistenti();

    }

    public void initListeners (){
        creaToDoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                titolo = JOptionPane.showInputDialog(frame, "Inserisci il titolo del ToDo");
                controller.creaToDo(titolo);
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
                            controller.setTitoloToDoCorrente(titolo); // salva il titolo
                            frame.dispose(); // chiude la finestra attuale
                            CreaToDo creatodo = new CreaToDo(frame, controller);
                            frame.setVisible(false);
                            creatodo.getFrame().setVisible(true);

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

    public void caricaToDoEsistenti() {
        ArrayList<String> titoli = controller.getListaToDo();
        todopanel.removeAll();  // pulisce il pannello prima di aggiungere

        for (String titolo : titoli) {
            JButton btn = new JButton(titolo);
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);

            btn.addActionListener(e -> {
                controller.setTitoloToDoCorrente(titolo); // salva il titolo
                frame.dispose();
                CreaToDo creaToDo = new CreaToDo(frame, controller);
                frame.setVisible(false);
                creaToDo.getFrame().setVisible(true);
            });

            todopanel.add(btn);
        }

        todopanel.revalidate();
        todopanel.repaint();
    }



    public JFrame getFrame() {
        return frame;
    }
}
