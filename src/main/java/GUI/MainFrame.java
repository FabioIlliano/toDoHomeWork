package GUI;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame(String titolo) {
        super(titolo);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}
