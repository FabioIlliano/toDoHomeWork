package GUI;

import Controller.Controller;

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

    private Controller controller;

    public Register(JFrame frame, Controller controller) {
        this.frame = new JFrame("Registrazione");
        this.controller = controller;

        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true); // mostra registrazione

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

                if (!(username.isEmpty() || psw.isEmpty() || psw2.isEmpty()))
                {
                    if(psw.equals(psw2))
                    {
                        controller.creaUtente(username, psw);  //e' privato il metodo nel controller(?)
                        Home home = new Home(frame, controller);
                        frame.setVisible(false);
                        home.getFrame().setVisible(true);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame, "LE PASSWORD NON COINCIDONO!");
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "INSERISCI LE CREDENZIALI!");
                }
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                frame.setVisible(false);
                Login.getFrame().setVisible(true);
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
