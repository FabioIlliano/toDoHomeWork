package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login {

    static JFrame frame;
    private JPanel MainPanel;
    private JLabel usernameJL;
    private JTextField txtusername;
    private JLabel passwordJL;
    private JPasswordField passwordPasswordField;
    private JButton loginButton;
    private Home home;

    public static void main(String[] args) {

        frame = new JFrame("GUIFORM");
        frame.setContentPane(new login().MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    public login()
    {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home home = new Home(frame);
                frame.setVisible(false);
                home.frame.setVisible(true);
            }

        });


    }


}
