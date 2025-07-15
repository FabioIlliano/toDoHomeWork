package gui;

import controller.Controller;
import model.TitoloBacheca;
import model.Attivita;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Gestisci ToDo permette di inserire e modificare tutti i vari dati presenti in un ToDo.
 */
public class GestisciToDo {
    private final JFrame frame;
    private JPanel mainPanel;

    private JLabel titoloJL;
    private JTextField titoloTextField;
    private JLabel descrizioneJL;
    private JTextField descTextField;
    private JLabel scadenzaJL;
    private JTextField dataScadTextField;
    private JLabel urlJL;
    private JTextField urlTextField;

    private JPanel urlPanel;
    private JPanel titoloPanel;
    private JPanel dataScadPanel;
    private JPanel descPanel;
    private JPanel buttonpanel;
    private JPanel btnPanel2;

    private JButton eliminaButton;
    private JButton confermaButton;
    private JButton indietroButton;
    private JPanel imgPanel;
    private JLabel imgJLtext;
    private JPanel bgColorPanel;
    private JLabel bgColorJL;
    private JButton setCompletoButton;
    private JPanel completatoPanel;
    private JLabel imgCompletatoJL;
    private JLabel imgJL;
    private JButton bgColorButton;
    private JPanel btnPanel1;
    private JButton spostaToDobutton;
    private JButton scegliIMGButton;
    private JScrollPane checklistJSP;
    private JLabel checklistJL;
    private JPanel checklistJP;
    private JButton nuovaAttivitaButton;
    private JPanel attivitaPanel;
    private JPanel attibtnPanel;
    private JButton eliminaAttivitaButton;
    private JPanel condividiPanel;
    private JButton condividiBtn;
    private JButton eliminaCondivisioneBtn;
    private JButton mostraCondivisioneBtn;
    private JButton resetImgBtn;
    private JComboBox<TitoloBacheca> spostaJCB; //si può fare anche dinamica
    private JPanel panelSposta;
    private Color c;

    private JComboBox<String> condividiJCB;
    private JPanel panelCondivisione;

    private final Controller controller;

    private static final String ICON_NOT_COMPLETED = "/notCompleted64.png";
    private static final String ICON_COMPLETED = "/completed64.png";
    private static final String CONDIVISIONE = "CONDIVISIONE";

    /**
     * Istanzia una nuova schermata di gestione dei ToDo.
     *
     * @param controller il controller
     */
    public GestisciToDo(Controller controller) {
        this.frame = new JFrame(controller.getTitoloToDoCorrente());
        this.controller = controller;
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 800);
        this.frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        this.initListeners();
        this.initText();
        this.titoloTextField.setText(controller.getTitoloToDoCorrente());
        this.attivitaPanel.setLayout(new BoxLayout(attivitaPanel, BoxLayout.Y_AXIS));
        caricaAttivitaChecklist(true);
    }

    /**
     * Questo metodo chiama tutti i metodi che contengono i listener degli oggetti grafici presenti in GestisciToDo
     */
    public void initListeners() {

        this.initConfermaBtn();
        this.initEliminaBtn();
        this.initScegliImgBtn();
        this.initResetImgBtn();
        this.initImjJLBtn();
        this.initIndietroBtn();
        this.initBgColorBtn();
        this.initSetCompletoBtn();
        this.initSpostaToDoBtn();
        this.initNuovaAttivitaBtn();
        this.initEliminaAttivitaBtn();
        this.initCondividiAttivitaBtn();
        this.initEliminaCondivisioneBtn();
        this.initMostraCondivisioniBtn();

    }

    /**
     * inizializza tutti i campi del ToDo.
     */
    public void initText(){
        checklistJP.setLayout(new BoxLayout(checklistJP, BoxLayout.Y_AXIS));


        this.titoloTextField.setText(controller.getTitoloToDoCorrente());
        this.descTextField.setText(controller.getDescrizioneToDo());
        this.dataScadTextField.setText(controller.getDataScadToDo());
        this.urlTextField.setText(controller.getUrlToDo());

        this.c = controller.getColorBG();

        if (controller.getCompletoToDo())
            imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource(ICON_COMPLETED)));
        else
            imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource(ICON_NOT_COMPLETED)));

        Image imgRidimensionata;

        if (controller.getIMGToDo()!=null){
            imgRidimensionata = controller.getIMGToDo().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            imgJL.setIcon(new ImageIcon(imgRidimensionata));
        }
        else
            imgJL.setIcon(new ImageIcon(getClass().getResource("/no-image64.png")));

        spostaJCB = new JComboBox<>();
        if (controller.getTitoloBacheca().equals(TitoloBacheca.UNIVERSITA.toString())){
            spostaJCB.addItem(TitoloBacheca.LAVORO);
            spostaJCB.addItem(TitoloBacheca.TEMPO_LIBERO);
        }
        else if (controller.getTitoloBacheca().equals(TitoloBacheca.LAVORO.toString())){
            spostaJCB.addItem(TitoloBacheca.UNIVERSITA);
            spostaJCB.addItem(TitoloBacheca.TEMPO_LIBERO);
        } else {
            spostaJCB.addItem(TitoloBacheca.UNIVERSITA);
            spostaJCB.addItem(TitoloBacheca.LAVORO);
        }
    }

    /**
     * Carica tutte le attività presenti in una Checklist.
     */
    private void caricaAttivitaChecklist(boolean db) {
        ArrayList<Attivita> a;
        if (db){
             a = controller.getListaAttivita();
            controller.setListaAttivitaLocale(a);
        }
        else
            a = controller.getListaAttivitaLocale();

        attivitaPanel.removeAll();

        if(a==null || a.isEmpty()){
            System.out.println("VUOTA");
            return;
        }


        for (Attivita attivita : a) {

            JCheckBox checkBox = new JCheckBox(attivita.getNome());
            checkBox.setSelected(attivita.isStato());
            checkBox.addItemListener(new ItemListener() {
                @Override

                public void itemStateChanged(ItemEvent e) {
                    controller.setStatoLocale(attivita.getNome(), checkBox.isSelected());
                } });

            attivitaPanel.add(checkBox);
        }

        attivitaPanel.revalidate();
        attivitaPanel.repaint();
    }

    /**
     * Inizializza il pulsante di conferma per salvare le modifiche al ToDo.
     * Valida i dati inseriti, aggiorna i campi del ToDo e gestisce errori come formato data non valido
     * o attività non completate.
     */
    public void initConfermaBtn(){
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (titoloTextField.getText().trim().isEmpty())
                    JOptionPane.showMessageDialog(frame, "TITOLO OBBLIGATORIO!", "ERRORE", JOptionPane.ERROR_MESSAGE);
                else {
                    aggiornaCampiToDo();
                    try{
                        boolean stato = controller.getCompletoToDo();

                        aggiornaStatiAttivita();

                        controller.aggiornaToDo();

                        controller.aggiornaChecklist(controller.getListaAttivitaLocale());

                        controller.rimpiazzaToDoLocale();

                        boolean statoDopo = controller.getCompletoToDo();

                        initText();
                        caricaAttivitaChecklist(true);

                        if (stato!=statoDopo){
                            mostraMessaggioCambioStato(statoDopo);
                        }
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                        if (e1.getSQLState().equals("P0001")){
                            JOptionPane.showMessageDialog(frame, "BISOGNA COMPLETARE TUTTE LE ATTIVITA PRIMA DI COMPLETARE IL TODO", Register.MSG_ERRORE, JOptionPane.ERROR_MESSAGE);
                            imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource(ICON_NOT_COMPLETED)));
                            controller.setCompletoToDo(false);
                        }
                    }
                }
            }
        });
    }

    /**
     * Per ogni JCheckBox presente nel pannello delle attività, aggiorna lo stato dell'attività
     * corrispondente nel modello locale
     */
    private void aggiornaStatiAttivita() {
        for (Component comp : attivitaPanel.getComponents()) {
            if (comp instanceof JCheckBox checkBox) {//if per sicurezza anche se nel panel ci sono solo checkbox
                controller.setStatoLocale(checkBox.getText(), checkBox.isSelected());
            }
        }
    }

    /**
     * Mostra un messaggio quando il ToDo viene automaticamente segnato
     * come completato o non completato da un trigger, e aggiorna l'icona corrispondente.
     *
     * @param completato true se il ToDo è stato impostato come completato, false altrimenti.
     */
    public void mostraMessaggioCambioStato(boolean completato) {
        if (completato) {
            JOptionPane.showMessageDialog(frame,
                    "Il ToDo è stato automaticamente segnato come COMPLETATO.",
                    "Attenzione", JOptionPane.INFORMATION_MESSAGE);
            imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource(ICON_COMPLETED)));
            controller.setCompletoToDo(true);
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Il ToDo è stato automaticamente segnato come NON COMPLETATO.\nVerifica che tutte le attività siano corrette.",
                    "Attenzione", JOptionPane.INFORMATION_MESSAGE);
            imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource(ICON_NOT_COMPLETED)));
            controller.setCompletoToDo(false);
        }
    }

    /**
     * Aggiorna i campi del ToDo con i valori inseriti dall'utente.
     * Controlla anche la validità della data di scadenza.
     */
    private void aggiornaCampiToDo() {
        controller.cambiaTitoloToDo(titoloTextField.getText());
        controller.cambiaDescToDo(descTextField.getText());

        String dataScad = dataScadTextField.getText();
        if (!dataScad.isEmpty()) {
            if (!controller.cambiaDataScadToDo(dataScad)) {
                JOptionPane.showMessageDialog(frame, "FORMATO DATA NON VALIDO, FORMATO CORRETTO: dd-MM-yyyy");
            } else if (!controller.checkData(dataScad)) {
                JOptionPane.showMessageDialog(frame, "DATA PRECEDENTE AD OGGI!!");
                controller.cambiaDataScadToDo("");
            }
        } else
            controller.cambiaDataScadToDo("");

        controller.cambiaBgColorToDo(c);
        controller.cambiaURLToDo(urlTextField.getText());
    }

    /**
     * Inizializza il listener del pulsante "Elimina".
     * Mostra un dialogo di conferma e, in caso positivo, elimina il ToDo.
     */
    public void initEliminaBtn(){
        eliminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(frame,"SEI SICURO DI VOLER ELIMINARE IL TODO?", "ELIMINA TODO", JOptionPane.OK_CANCEL_OPTION);
                if (result==JOptionPane.OK_OPTION){
                    controller.eliminaToDo();
                    GestisciBacheca gestisciBacheca = new GestisciBacheca(controller);
                    gestisciBacheca.getFrame().setVisible(true);
                    frame.dispose();
                }

            }
        });
    }

    /**
     * Inizializza il listener per il pulsante "Scegli Immagine".
     * Permette all'utente di caricare un'immagine per il ToDo da file.
     */
    public void initScegliImgBtn(){
        scegliIMGButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(true);
                int r = fileChooser.showOpenDialog(frame);
                if (r == JFileChooser.APPROVE_OPTION) {

                    File file = fileChooser.getSelectedFile();
                    String nomeFile = file.getName();
                    if (nomeFile.endsWith(".png") || nomeFile.endsWith(".jpg") || nomeFile.endsWith(".jpeg") || nomeFile.endsWith(".gif")){
                        ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                        Image img = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                        imgJL.setIcon(new ImageIcon(img));

                        try {
                            byte[] imgBytes = Files.readAllBytes(file.toPath());
                            controller.setIMGToDo(imgBytes);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Errore nel caricamento dell'immagine");
                        }
                    }else
                        JOptionPane.showMessageDialog(frame, "SELEZIONE UN FILE IMMAGINE (PNG, JPG, JPEG, GIF)");
                }
            }
        });
    }

    /**
     * Inizializza il listener per il pulsante "Reset Immagine".
     * Rimuove l'immagine associata al ToDo e ripristina l'immagine di default.
     */
    public void initResetImgBtn(){
        resetImgBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (controller.getIMGToDo()==null)
                    JOptionPane.showMessageDialog(frame, "IMMAGINE GIÀ INESISTENTE ");
                else{
                    controller.setIMGToDo(null);
                    imgJL.setIcon(new ImageIcon(getClass().getResource("/no-image64.png")));
                    JOptionPane.showMessageDialog(frame, "IMMAGINE RESETTATA");
                }
            }
        });
    }

    /**
     * Inizializza il listener sull'immagine (imgJL).
     * Mostra l'immagine a schermo intero se disponibile, altrimenti avvisa l'utente.
     */
    public void initImjJLBtn(){
        imgJL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller.getIMGToDo() == null){
                    JOptionPane.showMessageDialog(frame, "NESSUN IMMAGINE NON ASSOCIATA A QUESTO TODO", Register.MSG_ERRORE, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                VisualizzaImg visualizzaImg = new VisualizzaImg(controller);
                visualizzaImg.getFrame().setVisible(true);
                frame.dispose();
            }
        });
    }

    /**
     * Inizializza il listener del pulsante "Indietro".
     * Torna alla schermata di gestione bacheche.
     */
    public void initIndietroBtn(){
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestisciBacheca b = new GestisciBacheca(controller);
                b.getFrame().setVisible(true);
                frame.dispose();
            }
        });
    }

    /**
     * Inizializza il listener del pulsante "Sfondo".
     * Permette all'utente di selezionare un colore di sfondo personalizzato per il ToDo.
     */
    public void initBgColorBtn(){
        bgColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(frame, "Scegli un colore", bgColorPanel.getBackground());
                if (color!=null)
                    c = color;
                else
                    c = controller.getBGColorToDo();
            }
        });
    }

    /**
     * Inizializza il listener del pulsante "Completa ToDo".
     * Inverte lo stato di completamento del ToDo e aggiorna l'icona corrispondente.
     */
    public void initSetCompletoBtn(){
        setCompletoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean b = controller.getCompletoToDo();
                controller.setCompletoToDo(!b);
                if (b)
                    imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource(ICON_NOT_COMPLETED)));
                else
                    imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource(ICON_COMPLETED)));
            }
        });
    }

    /**
     * Inizializza il listener del pulsante "Sposta ToDo".
     * Permette di spostare il ToDo in un'altra bacheca selezionata.
     */
    public void initSpostaToDoBtn(){
        spostaToDobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelSposta = new JPanel();
                panelSposta.removeAll();
                panelSposta.add(new JLabel("Seleziona la bacheca in cui spostare il TODO"));
                panelSposta.add(spostaJCB);
                int result = JOptionPane.showConfirmDialog(frame, panelSposta, "Scegli la bacheca", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION){
                    if (!controller.checkBacheca(spostaJCB.getSelectedItem().toString())){
                        JOptionPane.showMessageDialog(frame, "BACHECA INESISTENTE!");
                        return;
                    }
                    TitoloBacheca t = (TitoloBacheca) spostaJCB.getSelectedItem();
                    boolean b = controller.spostaToDo(t);
                    if (b){
                        JOptionPane.showMessageDialog(frame, "TODO SPOSTATO");
                        GestisciBacheca gestisciBacheca = new GestisciBacheca(controller);
                        gestisciBacheca.getFrame().setVisible(true);
                        frame.dispose();
                    }
                    else
                        JOptionPane.showMessageDialog(frame, "TODO NON SPOSTATO CORRETTAMENTE");

                }
            }
        });
    }

    /**
     * Inizializza il listener del pulsante "Nuova Attività".
     * Permette di creare una nuova attività nella checklist del ToDo.
     */
    public void initNuovaAttivitaBtn(){
        nuovaAttivitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeAttivita = JOptionPane.showInputDialog(frame, "Inserisci il nome dell'attività:");
                if (nomeAttivita != null && !nomeAttivita.trim().isEmpty()) {
                    if (controller.creaAttivita(nomeAttivita) == -1){
                        JOptionPane.showMessageDialog(frame, "CREAZIONE NON ANDATA A BUON FINE", Register.MSG_ERRORE, JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    aggiornaStatiAttivita();
                    caricaAttivitaChecklist(false);
                    initText();
                }
            }
        });
    }


    /**
     * Inizializza il listener del pulsante "Elimina Attività".
     * Permette di eliminare un'attività dalla checklist del ToDo.
     */
    public void initEliminaAttivitaBtn(){
        eliminaAttivitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeAttivita = JOptionPane.showInputDialog(frame, "Inserisci il nome dell'attività da eliminare, se esiste verrà eliminata direttamente.", "ELIMINA", JOptionPane.QUESTION_MESSAGE);
                if (nomeAttivita != null && !nomeAttivita.trim().isEmpty()) {
                    if (controller.getAttivita(nomeAttivita)!=null){
                        aggiornaStatiAttivita();
                        ArrayList<Attivita> listaLocale = controller.getListaAttivitaLocale();;
                        if (controller.eliminaAttivita(nomeAttivita) == 0) {
                            JOptionPane.showMessageDialog(frame, "Attività eliminata correttamente", "ATTIVITA' ELIMINATA", JOptionPane.INFORMATION_MESSAGE);
                            listaLocale.remove(controller.getAttivita(nomeAttivita));

                            if (controller.listeDiverse(listaLocale)){
                                controller.setListaAttivitaLocale(listaLocale);
                                caricaAttivitaChecklist(false);
                            }
                            else
                                caricaAttivitaChecklist(true);

                            initText();
                        }
                        else
                            JOptionPane.showMessageDialog(frame, "Attività non eliminata correttamente", "ATTIVITA' NON ELIMINATA", JOptionPane.INFORMATION_MESSAGE);

                    }
                    else
                        JOptionPane.showMessageDialog(frame, "ATTIVITA INESISTENTE", "", JOptionPane.ERROR_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(frame, "INSERIRE UN TITOLO", "", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Inizializza il listener del pulsante "Condividi".
     * Condivide il ToDo con un altro utente specificato.
     */
    public void initCondividiAttivitaBtn(){
        condividiBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String utenteDest = JOptionPane.showInputDialog(frame, "INSERISCI L'USERNAME DELL'UTENTE CON CUI CONDIVIDERE IL TODO", "CONDIVIDI TODO", JOptionPane.QUESTION_MESSAGE);
                if (utenteDest == null)
                    return;
                if (controller.checkUtente(utenteDest)){
                    int r = controller.condividiToDo(utenteDest);
                    if (r==0)
                        JOptionPane.showMessageDialog(frame, "TODO CONDIVISO CORRETTAMENTE", CONDIVISIONE, JOptionPane.INFORMATION_MESSAGE);
                    else
                        JOptionPane.showMessageDialog(frame, "TODO NON CONDIVISO CORRETTAMENTE", CONDIVISIONE, JOptionPane.ERROR_MESSAGE);
                }
                else
                    JOptionPane.showMessageDialog(frame, "UTENTE INESISTENTE!", CONDIVISIONE, JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /**
     * Inizializza il listener del pulsante "Rimuovi Condivisione".
     * Rimuove la condivisione del ToDo con uno degli utenti selezionati.
     */
    public void initEliminaCondivisioneBtn(){
        eliminaCondivisioneBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCondivisione = new JPanel();
                panelCondivisione.removeAll();
                panelCondivisione.add(new JLabel("SELEZIONA L'UTENTE CON CUI RIMUOVERE LA CONDIVISIONE"));
                ArrayList<String> listaUtenti = controller.getListaCondivisioni();
                condividiJCB = new JComboBox<>();

                for (String s : listaUtenti)
                    condividiJCB.addItem(s);

                if (listaUtenti.isEmpty()){
                    JOptionPane.showMessageDialog(frame, "TODO NON CONDIVISO CON NESSUNO", Register.MSG_ERRORE, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (listaUtenti.contains(controller.getUtente().getUsername())){
                    JOptionPane.showMessageDialog(frame, "QUESTO TODO È STATO CONDIVISO CON TE, NON PUOI RIMUOVERLO!", Register.MSG_ERRORE, JOptionPane.ERROR_MESSAGE);
                    return;
                }


                panelCondivisione.add(condividiJCB);
                int result = JOptionPane.showConfirmDialog(frame, panelCondivisione, "Scegli l'utente", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION){
                    int r = controller.rimuoviCondivisione(condividiJCB.getSelectedItem().toString());
                    if (r==0)
                        JOptionPane.showMessageDialog(frame, "CONDIVISIONE RIMOSSA!");
                    else
                        JOptionPane.showMessageDialog(frame, "CONDIVISIONE NON RIMOSSA", Register.MSG_ERRORE, JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    /**
     * Inizializza il listener del pulsante "Mostra Condivisioni".
     * Mostra una lista degli utenti con cui il ToDo è stato condiviso.
     */
    public void initMostraCondivisioniBtn(){
        mostraCondivisioneBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelCondivisione = new JPanel();
                panelCondivisione.removeAll();
                panelCondivisione.add(new JLabel("ECCO GLI UTENTI CHE HANNO ACCESSO AL TODO: "));
                ArrayList<String> listaUtenti = controller.getListaCondivisioni();

                if (listaUtenti.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "IL TODO NON È CONDIVISO CON UTENTI", "UTENTI", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                StringBuilder utenti = new StringBuilder("|--");

                for (String s : listaUtenti)
                    utenti.append(s).append("-");
                utenti.append("-|");

                panelCondivisione.add(new JLabel(utenti.toString()));
                JOptionPane.showMessageDialog(frame, panelCondivisione, "UTENTI", JOptionPane.INFORMATION_MESSAGE);
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
