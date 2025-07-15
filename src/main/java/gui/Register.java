package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe che rappresenta l'interfaccia grafica per la registrazione di un nuovo utente.
 * Gestisce l'inserimento dell'username, delle password e la comunicazione con il {@code Controller}
 * per la creazione dell'account.
 */
public class Register {
    private final JFrame frame;

    private JPanel mainPanel;
    private JPanel textPanel;

    private JLabel usernameJLB;
    private JLabel passwordJLB;
    private JLabel password2JLB;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField passwordRipetuta;

    private JButton registratiButton;
    private JButton loginButton;
    private JPanel btnPanel;
    private JPanel accediPanel;
    private JPanel registratiPanel;
    private JPanel header;
    private JTextField txt1;
    private JPanel subHeader;
    private JTextField txt2;

    private final Controller controller;

    public static final String MSG_ERRORE = "ERRORE";

    /**
     * Costruttore che inizializza la finestra di registrazione e imposta i listener dei pulsanti.
     *
     * @param controller il controller utilizzato per gestire la logica applicativa
     */
    public Register(Controller controller) {
        this.frame = new JFrame("Registrazione");
        this.controller = controller;

        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);

        this.initListeners();
    }

    /**
     * Inizializza i listener dei pulsanti presenti nella schermata.
     */
    public void initListeners() {
        initLoginButton();
        initRegistratiButton();
    }

    /**
     * Inizializza il comportamento del pulsante di login.
     * Quando premuto, apre la schermata di login e chiude quella attuale.
     */
    public void initLoginButton(){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login(controller);
                frame.setVisible(false);
                login.getFrame().setVisible(true);
                frame.dispose();
            }
        });
    }


    /**
     * Inizializza il comportamento del pulsante di registrazione.
     * Quando premuto, valida i dati inseriti, verifica che le due password coincidano
     * e comunica con il controller per la creazione dell'utente.
     * In base al risultato, mostra un messaggio di errore o conferma e reindirizza alla StartPage.
     */
    public void initRegistratiButton(){
        registratiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] pasw = passwordField.getPassword();
                String psw = new String(pasw);
                char[] pasw2 = passwordRipetuta.getPassword();
                String psw2 = new String(pasw2);

                if(psw.contains(" ") || psw.length()<5 || username.contains(" ") || username.length()<5){
                    JOptionPane.showMessageDialog(frame, "USERNAME E/0 PASSWORD NON CORRETTI! \n" +
                            "USERNAME E PASSWORD DEVONO ESSERE DI ALMENO 5 CARATTERI E NON ACCETTANO SPAZI", MSG_ERRORE, JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (psw.equals(psw2)) {
                    int n = controller.creaUtente(username, psw);

                    switch (n) {
                        case 1:
                            JOptionPane.showMessageDialog(frame, "USERNAME GIA UTILIZZATO", MSG_ERRORE, JOptionPane.ERROR_MESSAGE);
                            break;
                        case 2:
                            JOptionPane.showMessageDialog(frame, "ERRORE DI CONNESSIONE AL DATABASE", MSG_ERRORE, JOptionPane.ERROR_MESSAGE);
                            break;
                        case -1:
                            JOptionPane.showMessageDialog(frame, "ERRORE, RIPROVARE", MSG_ERRORE, JOptionPane.ERROR_MESSAGE);
                            break;
                        case 0:
                            JOptionPane.showMessageDialog(frame, "UTENTE CREATO CORRETTAMENTE", "UTENTE REGISTRATO", JOptionPane.INFORMATION_MESSAGE);
                            StartPage s = new StartPage();
                            frame.setVisible(false);
                            s.getFrame().setVisible(true);
                            frame.dispose();
                            break;
                        default:
                            JOptionPane.showMessageDialog(frame, "ERRORE SCONOSCIUTO", MSG_ERRORE, JOptionPane.ERROR_MESSAGE);
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "LE PASSWORD NON COINCIDONO!");
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
