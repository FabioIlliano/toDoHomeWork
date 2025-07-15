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
    private final JFrame frame;

    private JPanel mainPanel;
    private JButton creaToDoButton;
    private JScrollPane todoScrollPanel;
    private JPanel buttonpanel;
    private JPanel todopanel;
    private JButton tornaIndietroButton;
    private JButton ordinaButton;
    private JPanel ordinaPanel;
    private JButton scadOggiButton;
    private JLabel jl1;
    private JButton scadFissaButton;
    private JButton cercaToDoButton;

    private final Controller controller;

    private static final String SCADUTO = "scaduto";

    /**
     * Instanzia una nuova schermata della gestione della bacheca.
     *
     * @param controller il controller
     */
    public GestisciBacheca(Controller controller) {
        this.frame = new JFrame(controller.getTitoloBacheca());
        this.controller = controller;
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(850, 850);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
        this.frame.setResizable(false);
        this.initListeners();
        todopanel.setLayout(new BoxLayout(todopanel, BoxLayout.Y_AXIS));
        caricaToDo(true);
    }

    /**
     * Questo metodo contiene tutti i listener degli oggetti grafici presenti in GestisciBacheca.
     */
    public void initListeners() {

        initCreaToDoBtn();
        initTornaIndietroBtn();
        initOrdinaBtn();
        initScadOggiBtn();
        initScadFissaBtn();
        initCercaToDoBtn();

    }

    /**
     * Aggiorna l'interfaccia utente caricando la lista locale dei ToDo
     * e inizializzando i bottoni evidenziando quelli con gli ID specificati.
     *
     * @param idEvidenziati lista degli ID dei ToDo da evidenziare nell'interfaccia
     * @return la lista dei ToDo processati dopo l'inizializzazione dei bottoni
     */
    public ArrayList<ToDo> aggiornaUI(ArrayList<Integer> idEvidenziati){
        ArrayList<ToDo> listaToDo = controller.getListaToDoLocale();
        return initBottoni(listaToDo, idEvidenziati);
    }

    /**
     * Inizializza i pulsanti nella UI relativi ai ToDo.
     * Rimuove i pulsanti esistenti dal pannello e crea nuovi pulsanti per ciascun ToDo della lista fornita.
     * I ToDo il cui ID è contenuto in {@code idEvidenziati} verranno evidenziati.
     * Inoltre, restituisce una lista dei ToDo scaduti, identificati tramite una proprietà personalizzata "scaduto" nel JButton.
     *
     * @param lista lista di ToDo da visualizzare come pulsanti
     * @param idEvidenziati lista degli ID dei ToDo da evidenziare
     * @return lista di ToDo scaduti (evidenziati e con sfondo rosso)
     */
    public ArrayList<ToDo> initBottoni(ArrayList<ToDo> lista, ArrayList<Integer> idEvidenziati){
        todopanel.removeAll();
        ArrayList<ToDo> list = new ArrayList<>();
        for (ToDo t : lista) {
            boolean evidenziato;
            evidenziato = idEvidenziati.contains(t.getIdToDo());
            JButton btn = creaBottoneToDo(t, evidenziato);
            if (btn.getClientProperty(SCADUTO)!=null && btn.getClientProperty(SCADUTO).equals(true))
                list.add(t);
            todopanel.add(btn);
        }
        todopanel.revalidate();
        todopanel.repaint();
        return list;
    }

    /**
     * Carica la lista dei ToDo dal database aggiornandola in locale.
     * Aggiorna l'interfaccia utente visualizzando i ToDo attivi.
     * Se il parametro {@code b} è true, mostra un messaggio di avviso
     * con l'elenco dei ToDo scaduti evidenziati.
     *
     * @param b se true, viene mostrato un messaggio di notifica per i ToDo scaduti
     */
    public void caricaToDo(boolean b){
        controller.pulisciListaToDo();
        ArrayList<ToDo> listaToDo = controller.getListaToDo();
        for (ToDo t : listaToDo)
            controller.setListToDo(t);

        ArrayList<ToDo> lista = aggiornaUI(new ArrayList<>());
        if (b && lista != null && !lista.isEmpty()) {
            StringBuilder sb = new StringBuilder("|--");
            for (ToDo t : lista) {
                sb.append(t.getTitolo()).append("-");
            }
            sb.append("-|");
            JOptionPane.showMessageDialog(frame, "BENTORNATO, I SEGUENTI TODO, MOSTRATI IN ROSSO ED EVIDENZIATI, SONO SCADUTI: \n" + sb, "TODO SCADUTI\n", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    /**
     * Crea un JButton rappresentante un ToDo.
     * Imposta il testo, le dimensioni, i colori e i font in base allo stato del ToDo e se deve essere evidenziato.
     * Se la data di scadenza del ToDo è precedente alla data corrente, il bottone viene colorato in rosso.
     * Associa un listener al bottone per gestire la selezione del ToDo e aprire la relativa finestra di gestione.
     *
     * @param todo il ToDo da rappresentare
     * @param evidenziato indica se il bottone deve essere evidenziato (font giallo e grassetto)
     * @return il JButton creato e configurato
     */
    private JButton creaBottoneToDo(ToDo todo, boolean evidenziato){
        String titolo = todo.getTitolo();
        Color colore = todo.getColoreSfondo();
        int id = todo.getIdToDo();

        JButton btn = new JButton(titolo);
        Dimension dim = new Dimension(250, 80);
        btn.setMinimumSize(dim);
        btn.setPreferredSize(dim);
        btn.setMaximumSize(dim);
        if (evidenziato){
            btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            btn.setForeground(Color.YELLOW);
        }
        else {
            btn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
            btn.setForeground(new Color(34, 34, 34));
        }

        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMargin(new Insets(10, 20, 10, 20));

        if (todo.getDataScadenza()!=null && todo.getDataScadenza().isBefore(LocalDate.now())){
            colore = Color.RED;
            btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            btn.setForeground(Color.YELLOW);
            btn.putClientProperty(SCADUTO, true);
        }
        btn.setBackground(colore);


        btn.putClientProperty("idtodo", id);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.setIdToDoCorrente(id);
                controller.setTitoloToDoCorrente(titolo);
                frame.dispose();
                GestisciToDo gestisciToDo = new GestisciToDo(controller);
                frame.setVisible(false);
                gestisciToDo.getFrame().setVisible(true);
            }
        });

        return btn;
    }

    /**
     * Inizializza il listener per il pulsante "Crea ToDo" che richiede all'utente un titolo
     * e crea un nuovo ToDo tramite il controller.
     */
    private void initCreaToDoBtn() {
        creaToDoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String titolo = JOptionPane.showInputDialog(frame, "Inserisci il titolo del ToDo");

                try {
                    if (titolo == null)
                        throw new Exception();
                    if (titolo.trim().isEmpty()) {
                        throw new NullPointerException();
                    } else {
                        int id = controller.creaToDo(titolo);
                        if (id != -1) {
                            caricaToDo(false);
                        } else
                            JOptionPane.showMessageDialog(frame, "Errore nella creazione del ToDo.");
                    }
                } catch (Exception _) {
                    JOptionPane.showMessageDialog(frame, "TITOLO OBBLIGATORIO!");
                }
            }
        });
    }

    /**
     * Inizializza il listener per il pulsante "Torna Indietro" che chiude la finestra corrente
     * e apre la schermata Home.
     */
    private void initTornaIndietroBtn() {
        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home home = new Home(controller);
                frame.setVisible(false);
                home.getFrame().setVisible(true);
                frame.dispose();
            }
        });
    }

    /**
     * Inizializza il listener per il pulsante "Ordina" che alterna l'ordinamento dei ToDo
     * tra titolo e scadenza.
     */
    private void initOrdinaBtn() {
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
    }

    /**
     * Inizializza il listener per il pulsante "Scadenza Odierna" che evidenzia tutti i ToDo
     * con scadenza odierna.
     */
    private void initScadOggiBtn() {
        scadOggiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ArrayList<ToDo> listaToDoScadOggi = controller.getToDoScadenzaOggi();
                    if (listaToDoScadOggi != null && !listaToDoScadOggi.isEmpty()) {
                        ArrayList<Integer> idEvidenziati = new ArrayList<>();
                        for (ToDo t : listaToDoScadOggi)
                            idEvidenziati.add(t.getIdToDo());
                        aggiornaUI(idEvidenziati);
                        JOptionPane.showMessageDialog(frame, "I TODO IN SCADENZA OGGI SONO STATI EVIDENZIATI");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Nessun ToDo in scadenza oggi");
                        aggiornaUI(new ArrayList<>());
                    }
                } catch (Exception _){
                    JOptionPane.showMessageDialog(frame, "Non sono presenti ToDo nel sistema!");
                    aggiornaUI(new ArrayList<>());
                }
            }
        });
    }

    /**
     * Inizializza il listener per il pulsante "Scadenza Entro..." che chiede all'utente una data
     * e evidenzia tutti i ToDo con scadenza entro quella data.
     */
    private void initScadFissaBtn() {
        scadFissaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog(frame, "INSERISCI LA DATA ENTRO CUI DEVONO SCADERE I TODO. FORMATO dd-MM-yyyy");
                if (s != null) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate data = LocalDate.parse(s, formatter);
                        String sData = data.format(formatter);
                        ArrayList<ToDo> listaToDoScadFissa = controller.getToDoScadenzaFissa(data);
                        if (listaToDoScadFissa != null && !listaToDoScadFissa.isEmpty()) {
                            ArrayList<Integer> idEvidenziati = new ArrayList<>();
                            for (ToDo t : listaToDoScadFissa)
                                idEvidenziati.add(t.getIdToDo());
                            aggiornaUI(idEvidenziati);
                            JOptionPane.showMessageDialog(frame, "I TODO IN SCADENZA entro " + sData + " SONO STATI EVIDENZIATI");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Nessun ToDo in scadenza entro " + sData);
                            aggiornaUI(new ArrayList<>());
                        }
                    } catch (Exception _){
                        JOptionPane.showMessageDialog(frame, "FORMATO DATA NON VALIDO, FORMATO CORRETTO: dd-MM-yyyy");
                        aggiornaUI(new ArrayList<>());
                    }
                }
            }
        });
    }

    /**
     * Inizializza il listener per il pulsante "Cerca ToDo" che chiede all'utente il titolo
     * e evidenzia i ToDo corrispondenti.
     */
    private void initCercaToDoBtn() {
        cercaToDoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = JOptionPane.showInputDialog(frame, "INSERISCI IL TITOLO DEL TODO DA CERCARE");
                if (s != null) {
                    ArrayList<ToDo> lista = controller.cercaToDo(s);
                    if (lista != null && !lista.isEmpty()) {
                        ArrayList<Integer> idEvidenziati = new ArrayList<>();
                        for (ToDo t : lista)
                            idEvidenziati.add(t.getIdToDo());

                        aggiornaUI(idEvidenziati);

                        String msg = lista.size()==1 ? "IL TODO È STATO EVIDENZIATO!" : "I TODO SONO STATI EVIDENZIATI";
                        JOptionPane.showMessageDialog(frame, msg, "RICERCA TODO", JOptionPane.INFORMATION_MESSAGE);


                    } else
                        JOptionPane.showMessageDialog(frame, "IL TODO NON È PRESENTE NEL SISTEMA", "ERRORE", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }


    /**
     * Restituisce il frame principale della schermata iniziale.
     *
     * @return il {@code JFrame} della start page
     */
    public JFrame getFrame() {
        return frame;
    }
}
