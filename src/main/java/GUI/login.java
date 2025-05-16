package GUI;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login {

    static JFrame frame;
    private JPanel MainPanel;
    private JLabel usernameJL;
    private JTextField txtusername;
    private JLabel passwordJL;
    private JPasswordField txtpassword;
    private JButton loginButton;
    private Home home;
    private static Controller controller;

    public static void main(String[] args) {
        controller = new Controller();
        frame = new JFrame("GUIFORM");
        frame.setContentPane(new login().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        //frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }

    public login()
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
                        home.frame.setVisible(true);
                    }
                    else{
                        JOptionPane.showMessageDialog(frame, "CREDENZIALI ERRATE!!");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(frame, "INSERIRE CORRETTAMENTE LE CREDENZIALI!!");
                }


                //Home home = new Home(frame);
                //frame.setVisible(false);
                //home.frame.setVisible(true);
            }

        });


    }

}
