package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * La classe {@code StartPage} rappresenta la schermata iniziale dell'applicazione,
 * da cui l'utente può accedere alla registrazione o al login.
 */
public class StartPage {
    private static final JFrame frame = new JFrame("START PAGE");
    private JPanel mainPanel;
    private JTextField txt1;
    private JTextField txt2;
    private JButton lgbtn;
    private JButton rgbtn;
    private JPanel btnpanel;
    private JPanel titolopanel;
    private JPanel subpanel;
    private JTextField regiText;
    private JTextField accediText;
    private JPanel accedipanel;
    private JPanel registratipanel;
    private static Controller controller;


    /**
     * Il punto di ingresso dell'applicazione.
     *
     * @param args argomenti da linea di comando (non utilizzati)
     */
    public static void main(String[] args) {
        controller = new Controller();
        //imposto i JOptionPane con dei colori precisi
        UIManager.put("OptionPane.background", new ColorUIResource(224, 224, 224));
        UIManager.put("Panel.background", new ColorUIResource(224, 224, 224));
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(33, 33, 33));
        new StartPage();
    }

    /**
     * Costruttore della classe {@code StartPage}.
     * Inizializza il frame e i listener dei pulsanti.
     */
    public StartPage(){
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        initListeners();
    }

    /**
     * Inizializza i listener per i pulsanti di login e registrazione.
     */
    public void initListeners() {

        lgbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login(controller);
                frame.setVisible(false);
                login.getFrame().setVisible(true);
                frame.dispose();
            }
        });

        rgbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register register = new Register(controller);
                frame.setVisible(false);
                register.getFrame().setVisible(true);
                frame.dispose();
            }
        });

        accediText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                lgbtn.doClick();
            }
        });

        regiText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rgbtn.doClick();
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
