package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login è la schermata principale e permette all'utente di fare l'accesso con i propri dati.
 */
public class Login {

    private final JFrame frame;
    private JPanel mainPanel;
    private JLabel usernameJL;
    private JTextField txtUsername;
    private JLabel passwordJL;
    private JPasswordField txtPassword;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel btnpanel;
    private JPanel headerPanel;
    private JTextField txt1;
    private JTextField txt2;
    private JPanel loginPanel;
    private JPanel subHeader;
    private final Controller controller;


    /**
     * Inizializza una nuova schermata di login.
     *
     * @param controller il controller utilizzato per la gestione della logica applicativa
     */
    public Login(Controller controller) {
        this.frame = new JFrame("login");
        this.controller = controller;
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        txt1.setBorder(null);
        txt2.setBorder(null);

        initListeners();
    }

    /**
     * Inizializza i listener per i pulsanti di login e registrazione.
     */
    public void initListeners (){

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                char[] psw1 = txtPassword.getPassword();
                String psw = new String(psw1);

                if (!(username.isEmpty() || psw.isEmpty())){

                    if (controller.login(username, psw)){

                        Home home = new Home(controller);
                        frame.setVisible(false);
                        home.getFrame().setVisible(true);
                        frame.dispose();
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "CREDENZIALI ERRATE!!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(frame, "INSERIRE CORRETTAMENTE LE CREDENZIALI!!");
                    txtUsername.setText("");
                    txtPassword.setText("");
                }


            }

        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register register = new Register(controller);
                frame.setVisible(false);
                register.getFrame().setVisible(true);
                frame.dispose();
            }
        });

    }

    /**
     * Restituisce il frame.
     *
     * @return il frame
     */
    public JFrame getFrame() {
        return frame;
    }
}


