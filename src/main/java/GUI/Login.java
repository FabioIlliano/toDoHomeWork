package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Login e' la schermata principale e permette all'utente di fare l'accesso con i propri dati.
 */
public class Login {

    static private JFrame frame;
    private JPanel MainPanel;
    private JLabel usernameJL;
    private JTextField txtusername;
    private JLabel passwordJL;
    private JPasswordField txtpassword;
    private JButton loginButton;
    private Home home;
    private static Controller controller;

    /**
     * il punto d'inizio dell applicazione.
     *
     * @param args gli input arguments
     */
    public static void main(String[] args) {
        controller = new Controller();
        frame = new JFrame("GUIFORM");
        frame.setContentPane(new Login().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        //frame.setAlwaysOnTop(true);
        frame.setVisible(true);
        //frame.setResizable(false);
    }

    /**
     * inizializza una nuova schermata di login.
     */
    public Login()
    {
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


    }

    /**
     * restituisce il frame.
     *
     * @return il frame
     */
    public static JFrame getFrame() {
        return frame;
    }
}
