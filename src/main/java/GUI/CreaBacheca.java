package GUI;

import Controller.Controller;
import model.TitoloBacheca;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreaBacheca {
    private JFrame frame;
    private JPanel creaBachecaPanel;
    private JPanel comboPanel;
    private JComboBox<TitoloBacheca> comboBox1;
    private JPanel descPanel;
    private JButton buttonCreaBacheca;
    private JPanel buttonPanel;
    private JLabel combolabel;
    private JLabel desclabel;
    private JTextArea textArea1;
    private JScrollPane scrollPane;
    private JButton tornaIndietroButton;
    private Controller controller;


    public CreaBacheca(JFrame jframe, Controller controller){
        this.frame = new JFrame("CreaBacheca");
        this.controller = controller;
        this.frame.setContentPane(creaBachecaPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        this.frame.setAlwaysOnTop(true);
        this.frame.setVisible(true);
        comboBox1.addItem(TitoloBacheca.UNIVERSITA);
        comboBox1.addItem(TitoloBacheca.LAVORO);
        comboBox1.addItem(TitoloBacheca.TEMPO_LIBERO);

        initButtonListener();
    }

    public void initButtonListener(){
        buttonCreaBacheca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //bisogna effettuare i controlli sull'input
                try{
                    controller.creaBacheca(((TitoloBacheca) comboBox1.getSelectedItem()), textArea1.getText());
                    JOptionPane.showMessageDialog(frame, "Bacheca creata con successo!");
                    frame.dispose(); // chiude la finestra attuale
                    Home home = new Home(frame, controller);
                    frame.setVisible(false);
                    home.getFrame().setVisible(true);
                }catch (NullPointerException exception) {
                    JOptionPane.showMessageDialog(frame, "SELEZIONA UN TITOLO!");
                }
                catch (Exception exception){
                    JOptionPane.showMessageDialog(frame, exception.getMessage());
                }
            }
        });

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home home = new Home(frame, controller);
                frame.setVisible(false);
                home.getFrame().setVisible(true);
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }
}
