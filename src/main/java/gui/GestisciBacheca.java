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
 * Gestisci bacheca permette di creare e ordinare ToDo
 */
public class GestisciBacheca {
    private JFrame frame;

    private JPanel mainpanel;
    private JButton descrizioneButton;
    private JButton creaToDoButton;
    private JScrollPane todoScrollPanel;
    private JPanel buttonpanel;
    private JPanel todopanel;
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
     * @param controller il controller
     */
    public GestisciBacheca(Controller controller) {
        this.frame = new JFrame(controller.getTitoloBacheca());
        this.controller = controller;
        this.frame.setContentPane(mainpanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.initListeners();
        todopanel.setLayout(new BoxLayout(todopanel, BoxLayout.Y_AXIS));
        caricaToDo(true);
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
                        int id = controller.creaToDo(titolo);
                        if (id!=-1){
                            caricaToDo(false);
                        }
                        else
                            JOptionPane.showMessageDialog(frame, "Errore nella creazione del ToDo.");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "TITOLO OBBLIGATORIO!");
                }
            }
        });


        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home home = new Home(controller);
                frame.setVisible(false);
                home.getFrame().setVisible(true);
                frame.dispose();
            }
        });

        ordinaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (ordinaButton.getText().equals("ORDINA PER: TITOLO")) {
                    controller.ordinaToDoAlfabeticamente();
                    ordinaButton.setText("ORDINA PER: SCADENZA");
                } else {
                    controller.ordinaToDoPerScadenza();
                    ordinaButton.setText("ORDINA PER: TITOLO");
                }
                aggiornaUI(new ArrayList<>());
            }

        });

        scadOggiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    ArrayList<ToDo> listaToDoScadOggi = controller.getToDoScadenzaOggi();
                    if (listaToDoScadOggi!=null && !listaToDoScadOggi.isEmpty()){
                        ArrayList<Integer> idEvidenziati = new ArrayList<>();
                        for(ToDo t : listaToDoScadOggi)
                            idEvidenziati.add(t.getIdToDo());
                        aggiornaUI(idEvidenziati);
                        JOptionPane.showMessageDialog(frame, "I TODO IN SCADENZA OGGI SONO STATI EVIDENZIATI");
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "Nessun ToDo in scadenza oggi");
                        aggiornaUI(new ArrayList<>());
                    }
                }catch (Exception e1){
                    JOptionPane.showMessageDialog(frame, "Non sono presenti ToDo nel sistema!");
                    aggiornaUI(new ArrayList<>());
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
                            ArrayList<Integer> idEvidenziati = new ArrayList<>();
                            for(ToDo t : listaToDoScadFissa)
                                idEvidenziati.add(t.getIdToDo());
                            aggiornaUI(idEvidenziati);
                            JOptionPane.showMessageDialog(frame, "I TODO IN SCADENZA entro "+sData +" SONO STATI EVIDENZIATI");
                        }
                        else{
                            JOptionPane.showMessageDialog(frame, "Nessun ToDo in scadenza entro "+sData);
                            aggiornaUI(new ArrayList<>());
                        }
                    }catch (Exception exception){
                        JOptionPane.showMessageDialog(frame, "FORMATO DATA NON VALIDO, FORMATO CORRETTO: dd-MM-yyyy");
                        aggiornaUI(new ArrayList<>());
                    }
                }


            }
        });

        cercaToDoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog(frame, "INSERISCI IL TITOLO DEL TODO DA CERCARE");
                if (s!=null){
                    ArrayList<ToDo> lista = controller.cercaToDo(s);
                    if (lista != null && !lista.isEmpty()){
                        ArrayList<Integer> idEvidenziati = new ArrayList<>();
                        for(ToDo t : lista)
                            idEvidenziati.add(t.getIdToDo());

                        aggiornaUI(idEvidenziati);

                        if(lista.size()==1){
                            JOptionPane.showMessageDialog(frame, "IL TODO è STATO EVIDENZIATO!");
                        }
                        else{
                            JOptionPane.showMessageDialog(frame, "I TODO SONO STATI EVIDENZIATI!");
                        }

                    }
                    else
                        JOptionPane.showMessageDialog(frame, "IL TODO NON È PRESENTE NEL SISTEMA");
                }
            }
        });

    }

    public ArrayList<ToDo> aggiornaUI(ArrayList<Integer> idEvidenziati){
        ArrayList<ToDo> listaToDo = controller.getListaToDoLocale();
        return initBottoni(listaToDo, idEvidenziati);

    }

    /**
     * Carica i ToDo.
     */
    public void caricaToDo(boolean b){
        controller.pulisciListaToDo();
        ArrayList<ToDo> listaToDo = controller.getListaToDo();
        for (ToDo t : listaToDo)
            controller.setListToDo(t);

        ArrayList<ToDo> lista = aggiornaUI(new ArrayList<>());
        if (b){
            if (lista != null && !lista.isEmpty()){
                String s = "|--";
                for (ToDo t : lista)
                    s = s + t.getTitolo() + "-";
                s = s+ "-|";
                JOptionPane.showMessageDialog(frame, "BENTORNATO, I SEGUENTI TODO, MOSTRATI IN ROSSO ED EVIDENZIATI, SONO SCADUTI: \n"+s, "TODO SCADUTI\n", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public ArrayList<ToDo> initBottoni(ArrayList<ToDo> lista, ArrayList<Integer> idEvidenziati){
        todopanel.removeAll();
        ArrayList<ToDo> list = new ArrayList<>();
        for (ToDo t : lista) {
            boolean evidenziato;
            evidenziato = idEvidenziati.contains(t.getIdToDo());
            JButton btn = creaBottoneToDo(t, evidenziato);
            if (btn.getClientProperty("scaduto")!=null && btn.getClientProperty("scaduto").equals(true))
                list.add(t);
            todopanel.add(btn);
        }
        todopanel.revalidate();
        todopanel.repaint();
        return list;
    }

    private JButton creaBottoneToDo(ToDo t, boolean evidenziato){
        String titolo = t.getTitolo();
        Color colore = t.getColoreSfondo();
        int id = t.getIdToDo();

        JButton btn = new JButton(titolo);
        Dimension dim = new Dimension(200, 50);
        btn.setMinimumSize(dim);
        btn.setPreferredSize(dim);
        btn.setMaximumSize(dim);
        if (evidenziato){
            btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            btn.setForeground(Color.YELLOW);
        }
        else
            btn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMargin(new Insets(10, 20, 10, 20));

        if (t.getDataScadenza()!=null && t.getDataScadenza().isBefore(LocalDate.now())){
            btn.setBackground(new Color(255, 0, 0));
            btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            btn.setForeground(Color.YELLOW);
            btn.putClientProperty("scaduto", true);
        }
        else
            btn.setBackground(colore);
        btn.putClientProperty("idtodo", id);
        btn.addActionListener(e -> {
            controller.setIdToDoCorrente(id);
            controller.setTitoloToDoCorrente(titolo);
            frame.dispose();
            GestisciToDo gestisciToDo = new GestisciToDo(controller);
            frame.setVisible(false);
            gestisciToDo.getFrame().setVisible(true);
        });

        return btn;
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
