package gui;

import controller.Controller;
import model.ToDo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Gestisci bacheca permette di creare e ordinare ToDo e modificare la descrizione
 */
public class GestisciBacheca {
    private JFrame frame;

    private JPanel mainpanel;
    private JButton descrizioneButton;
    private JButton creaToDoButton;
    private JScrollPane todoScrollPanel;
    private JPanel buttonpanel;
    private JPanel todopanel; //è sbagliato, andava fatto nel panel scrollabile non in un altro panel
    private JButton tornaIndietroButton;
    private JButton ordinaButton;
    private JPanel ordinaPanel;
    private JButton scadOggiButton;
    private JLabel JL1;
    private JButton scadFissaButton;
    private JButton cercaToDoButton;
    private JTextField descrizionetext;
    private Controller controller;

    /**
     * instanzia una nuova schermata della gestione della bacheca.
     *
     * @param frame      il frame
     * @param controller il controller
     */
    public GestisciBacheca(JFrame frame, Controller controller) {
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
        caricaToDo();
    }

    /**
     * Questo metodo contiene tutti i listener degli oggetti grafici presenti in GestisciBacheca
     */
    public void initListeners (){
        creaToDoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String titolo = JOptionPane.showInputDialog(frame, "Inserisci il titolo del ToDo");

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
                            controller.setTitoloToDoCorrente(btn.getText()); // salva il titolo
                            frame.dispose(); // chiude la finestra attuale
                            GestisciToDo gestisciToDo = new GestisciToDo(frame, controller);
                            frame.setVisible(false);
                            gestisciToDo.getFrame().setVisible(true);

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

                if (ordinaButton.getText().equals("ORDINA PER: TITOLO")) {
                    controller.ordinaToDoAlfabeticamente();
                    ordinaButton.setText("ORDINA PER: SCADENZA");
                } else {
                    controller.ordinaToDoPerScadenza();
                    ordinaButton.setText("ORDINA PER: TITOLO");
                }
                caricaToDo();
            }

        });

        scadOggiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    ArrayList<ToDo> listaToDoScadOggi = controller.getToDoScadenzaOggi();
                    if (listaToDoScadOggi!=null && !listaToDoScadOggi.isEmpty()){
                        StringBuilder msg = new StringBuilder("ToDo in scadenza oggi: \n");
                        for (ToDo todo : listaToDoScadOggi)
                            msg.append(todo.getTitolo()).append("\n");
                        JOptionPane.showMessageDialog(frame, msg);
                    }
                    else
                        JOptionPane.showMessageDialog(frame, "Nessun ToDo in scadenza oggi");
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(frame, "Non sono presenti ToDo nel sistema!");
                }
            }
        });

        scadFissaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog(frame, "INSERISCI LA DATA ENTRO CUI DEVONO SCADERE I TODO. FORMATO dd-MM-yyyy");
                if (s!=null){
                    try{
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate data = LocalDate.parse(s, formatter);
                        String sData = data.format(formatter);
                        ArrayList<ToDo> listaToDoScadFissa = controller.getToDoScadenzaFissa(data);
                        if (listaToDoScadFissa!=null && !listaToDoScadFissa.isEmpty()){
                            StringBuilder msg = new StringBuilder("ToDo in scadenza entro il "+sData+": \n");
                            for (ToDo todo : listaToDoScadFissa)
                                msg.append(todo.getTitolo()).append("\n");
                            JOptionPane.showMessageDialog(frame, msg);
                        }
                        else
                            JOptionPane.showMessageDialog(frame, "Nessun ToDo in scadenza entro "+sData);
                    }catch (Exception exception){
                        JOptionPane.showMessageDialog(frame, "FORMATO DATA NON VALIDO, FORMATO CORRETTO: dd-MM-yyyy");
                    }
                }


            }
        });

        cercaToDoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog(frame, "INSERISCI IL TITOLO DEL TODO DA CERCARE");
                if (s!=null){
                    if (controller.cercaToDo(s)!=null && !controller.cercaToDo(s).isEmpty())
                        JOptionPane.showMessageDialog(frame, "IL TODO È PRESENTE NEL SISTEMA");
                    else
                        JOptionPane.showMessageDialog(frame, "IL TODO NON È PRESENTE NEL SISTEMA");
                }
            }
        });

    }

    /**
     * Carica i ToDo.
     */
    public void caricaToDo(){
        ArrayList<ToDo> listaToDo = controller.getListaToDo();
        todopanel.removeAll();


        for (ToDo todo : listaToDo) {
            String titolo = todo.getTitolo();

            JButton btn = new JButton(titolo);
            btn.setAlignmentX(Component.LEFT_ALIGNMENT);

            btn.setBackground(todo.getColoreSfondo());

            btn.addActionListener(e -> {
                controller.setTitoloToDoCorrente(titolo); // salva il titolo

                frame.dispose();
                GestisciToDo gestisciToDo = new GestisciToDo(frame, controller);
                frame.setVisible(false);
                gestisciToDo.getFrame().setVisible(true);
            });

            todopanel.add(btn);
        }

        todopanel.revalidate();
        todopanel.repaint();
    }


    /**
     * restituisce il frame.
     *
     * @return il frame
     */
    public JFrame getFrame() {
        return frame;
    }
}
