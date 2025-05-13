package GUI;

import javax.swing.*;

public class Home {
    private JButton UNIVERSITA;
    private JButton LAVORO;
    private JButton TEMPOLIBERO;
    private JPanel HomePanel;
    public JFrame frame;

public Home(JFrame frame)
{
    frame = new JFrame("Home");
    frame.setContentPane(HomePanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);

}

}
