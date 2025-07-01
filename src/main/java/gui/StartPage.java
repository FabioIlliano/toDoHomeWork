package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class StartPage {
    private static JFrame frame;
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
     * il punto d'inizio dell applicazione.
     *
     * @param args gli input arguments
     */
    public static void main(String[] args) {
        controller = new Controller();
        //imposto i JOptionPane
        UIManager.put("OptionPane.background", new ColorUIResource(224, 224, 224));
        UIManager.put("Panel.background", new ColorUIResource(224, 224, 224));
        UIManager.put("OptionPane.messageForeground", new ColorUIResource(33, 33, 33));
        new StartPage();
    }

    public StartPage(){
        frame = new JFrame("login");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        initListeners(); // Avvia listener
    }

    public void initListeners(){

        lgbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login(frame, controller);
                frame.setVisible(false);
                login.getFrame().setVisible(true);
            }
        });

        rgbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register register = new Register(frame, controller);
                frame.setVisible(false);
                register.getFrame().setVisible(true);
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }
}
