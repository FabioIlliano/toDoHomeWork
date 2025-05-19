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
    private JPanel todopanel; //è sbagliato, andava fatto nel panel scrollabile non in un altro panel
    private JButton tornaIndietroButton;
    private JButton ordinaButton;
    private JPanel ordinalabel;
    private JTextField descrizionetext;
    private Controller controller;
    String titolo;
    private boolean ordinamentoAlfabetico;

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
        ordinamentoAlfabetico = true;
        caricaToDoEsistenti();

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
                        controller.creaToDo(titolo);
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

        ordinaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*controller.ordinaToDoAlfabeticamente();
                caricaToDoEsistenti();*/ // ricarica la lista aggiornata

                if (ordinamentoAlfabetico) {
                    controller.ordinaToDoAlfabeticamente();
                    ordinaButton.setText("scadenza");
                } else {
                    //controller.ordinaToDoPerScadenza();
                    JOptionPane.showMessageDialog(frame, "darebbe errore perche non hanno le date!");
                    ordinaButton.setText("titolo");
                }
                ordinamentoAlfabetico = !ordinamentoAlfabetico;
                caricaToDoEsistenti();
            }

        });

    }

    public void caricaToDoEsistenti() {
        ArrayList<String> titoli = controller.getListaToDo();
        todopanel.removeAll();  // pulisce il pannello prima di aggiungere

        for (String titolo : titoli) {
            //righe duplicate si può creare un metodo
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
