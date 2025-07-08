package gui;

import controller.Controller;
import dao.BachecaDAO;
import implementazioniPostgresDAO.BachecaImplementazionePostgresDAO;
import model.TitoloBacheca;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * la classe bacheca permette di creare una bacheca avente un nome predefinito tra
 * UNIVERSITA
 * LAVORO
 * TEMPO_LIBERO
 * e ogni bacheca puo' contenere dei ToDo.
 */
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


    /**
     * instanzia una nuova bacheca.
     *
     * @param controller il controller
     */
    public CreaBacheca(Controller controller){
        this.frame = new JFrame("CreaBacheca");
        this.controller = controller;
        this.frame.setContentPane(creaBachecaPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        this.frame.setAlwaysOnTop(true);
        this.frame.setResizable(false);
        initComboBox();
        initButtonListener();
    }

    /**
     * Questo metodo contiene tutti i listener degli oggetti grafici presenti in CreaBacheca
     */
    public void initButtonListener(){

        buttonCreaBacheca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String s = textArea1.getText();
                    if (s.isEmpty())
                        s = null;

                    controller.creaBacheca(((TitoloBacheca) comboBox1.getSelectedItem()), s);
                    JOptionPane.showMessageDialog(frame, "Bacheca creata con successo!");
                    Home home = new Home(controller);
                    frame.setVisible(false);
                    home.getFrame().setVisible(true);
                    frame.dispose();
                }
                catch (Exception exception){
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(frame, exception.getMessage());
                }
            }
        });

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Home home = new Home(controller);
                frame.setVisible(false);
                home.getFrame().setVisible(true);
            }
        });
    }

    public void initComboBox(){
        comboBox1.removeAllItems();
        try{
            BachecaDAO b = new BachecaImplementazionePostgresDAO();
            ArrayList<String> a = b.getTitoliUtente(controller.getUtente().getUsername());
            if (a.isEmpty())
                throw new Exception();
            TitoloBacheca[] listaTitoli = {TitoloBacheca.UNIVERSITA, TitoloBacheca.LAVORO, TitoloBacheca.TEMPO_LIBERO};
            for (TitoloBacheca t : listaTitoli)
                if (!a.contains(t.toString()))
                    comboBox1.addItem(t);
        }
        catch (Exception e){
            comboBox1.addItem(TitoloBacheca.UNIVERSITA);
            comboBox1.addItem(TitoloBacheca.LAVORO);
            comboBox1.addItem(TitoloBacheca.TEMPO_LIBERO);
        }
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
