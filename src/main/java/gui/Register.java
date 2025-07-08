package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Register {
    private JFrame frame;

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

    private Controller controller;

    public Register(Controller controller) {
        this.frame = new JFrame("Registrazione");
        this.controller = controller;

        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);

        this.initListeners();
    }

    public void initListeners()
    {
        registratiButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String username = usernameField.getText();
                char[] pasw = passwordField.getPassword();
                String psw = new String(pasw);
                char[] pasw2 = passwordRipetuta.getPassword();
                String psw2 = new String(pasw2);

                if(psw.contains(" ") || psw.length()<5 || username.contains(" ") || username.length()<5){
                    JOptionPane.showMessageDialog(frame, "USERNAME E/0 PASSWORD NON CORRETTI! \n" +
                            "USERNAME E PASSWORD DEVONO ESSERE DI ALMENO 5 CARATTERI E NON ACCETTANO SPAZI", "ERRORE", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if(psw.equals(psw2))
                {
                    int n = controller.creaUtente(username, psw);
                    if (n==1)
                        JOptionPane.showMessageDialog(frame, "USERNAME GIA UTILIZZATO", "ERRORE", JOptionPane.ERROR_MESSAGE);
                    if (n==2)
                        JOptionPane.showMessageDialog(frame, "ERRORE DI CONNESSIONE AL DATABASE", "ERRORE", JOptionPane.ERROR_MESSAGE);
                    if (n==-1)
                        JOptionPane.showMessageDialog(frame, "ERRORE, RIPROVARE", "ERRORE", JOptionPane.ERROR_MESSAGE);
                    if (n==0){
                        JOptionPane.showMessageDialog(frame, "UTENTE CREATO CORRETTAMENTE", "UTENTE REGISTRATO", JOptionPane.INFORMATION_MESSAGE);
                        StartPage s = new StartPage();
                        frame.setVisible(false);
                        s.getFrame().setVisible(true);
                        frame.dispose();
                    }

                }
                else
                    JOptionPane.showMessageDialog(frame, "LE PASSWORD NON COINCIDONO!");
            }
        });

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
     * restituisce il frame.
     *
     * @return il frame
     */
    public JFrame getFrame() {
        return frame;
    }
}
