package gui;

import controller.Controller;
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
public class CreaBacheca extends JDialog{
    private JPanel creaBachecaPanel;
    private JPanel comboPanel;
    private JComboBox<TitoloBacheca> comboBox1;
    private JPanel descPanel;
    private JButton buttonCreaBacheca;
    private JPanel buttonPanel;
    private JLabel comboLabel;
    private JLabel descLabel;
    private JTextArea textArea1;
    private JScrollPane scrollPane;
    private JButton tornaIndietroButton;
    private final transient Controller controller;


    /**
     * Istanzia una finestra modale per la creazione di una nuova bacheca.
     *
     * @param parent      il frame principale da cui viene aperta la finestra.
     * @param controller  il controller che gestisce la logica dell'applicazione.
     */
    public CreaBacheca(JFrame parent, Controller controller) {
        super(parent, "Crea Bacheca", true);

        this.controller = controller;
        this.setContentPane(creaBachecaPanel);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setSize(500, 500);
        this.setLocationRelativeTo(parent);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        initComboBox();
        initButtonListener();
    }

    /**
     * Inizializza i listener dei pulsanti:
     * Il pulsante "Crea Bacheca" crea una nuova bacheca con il titolo selezionato e la descrizione inserita.
     * Il pulsante "Torna Indietro" torna alla schermata Home senza creare nulla.
     * Mostra eventuali messaggi di errore in caso di problemi durante la creazione.
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
                    JOptionPane.showMessageDialog(CreaBacheca.this, "Bacheca creata con successo!");
                    dispose();

                }
                catch (Exception exception){
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(CreaBacheca.this, exception.getMessage());
                }
            }
        });

        tornaIndietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    /**
     * Inizializza la comboBox con i titoli delle bacheche
     * confrontando il db per la disponibilità.
     */
    public void initComboBox(){
        comboBox1.removeAllItems();
        ArrayList<String> lista = controller.getTitoliUtente();
        comboBox1.addItem(TitoloBacheca.UNIVERSITA);
        comboBox1.addItem(TitoloBacheca.LAVORO);
        comboBox1.addItem(TitoloBacheca.TEMPO_LIBERO);
        if (lista != null && !lista.isEmpty()){
            for (String s : lista)
                comboBox1.removeItem(TitoloBacheca.valueOf(s));
        }
    }

}
