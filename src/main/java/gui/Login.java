package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login e' la schermata principale e permette all'utente di fare l'accesso con i propri dati.
 */
public class Login {

    private JFrame frame;
    private JPanel MainPanel;
    private JLabel usernameJL;
    private JTextField txtusername;
    private JLabel passwordJL;
    private JPasswordField txtpassword;
    private JButton loginButton;
    private JButton registerButton;
    private JPanel btnpanel;
    private Home home;
    private Controller controller;


    /**
     * inizializza una nuova schermata di login.
     */
    public Login(JFrame frame, Controller controller) {
        this.frame = new JFrame("login");
        this.controller = controller;
        this.frame.setContentPane(MainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        initListeners(); // Avvia listener
    }

    public void initListeners (){
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtusername.getText();
                char[] pasw = txtpassword.getPassword();
                String psw = new String(pasw);
                if (!(username.isEmpty() || psw.isEmpty())){
                    if (controller.login(username, psw)){
                        Home home = new Home(frame, controller);
                        frame.setVisible(false);
                        home.getFrame().setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "CREDENZIALI ERRATE!!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(frame, "INSERIRE CORRETTAMENTE LE CREDENZIALI!!");
                }


            }

        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register register = new Register(frame, controller);
                frame.setVisible(false);
                register.getFrame().setVisible(true);
            }
        });

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


