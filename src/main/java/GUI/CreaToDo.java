package GUI;

import Controller.Controller;
import model.TitoloBacheca;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * The type Crea to do.
 */
public class CreaToDo {
    private JFrame frame;
    private JPanel mainPanel;

    private JLabel titoloJL;
    private JTextField titoloTextField;
    private JLabel descrizioneJL;
    private JTextField descTextField;
    private JLabel ScadenzaJL;
    private JTextField dataScadTextField;
    private JLabel urlJL;
    private JTextField urlTextField;

    private JPanel urlPanel;
    private JPanel titoloPanel;
    private JPanel dataScadPanel;
    private JPanel descPanel;
    private JPanel buttonpanel;
    private JPanel btnPanel2;

    private JButton eliminaButton;
    private JButton confermaButton;
    private JButton indietroButton;
    private JPanel imgPanel;
    private JLabel imgJLtext;
    private JPanel bgColorPanel;
    private JLabel bgColorJL;
    private JButton setCompletoButton;
    private JPanel completatoPanel;
    private JLabel imgCompletatoJL;
    private JLabel imgJL;
    private JButton bgColorButton;
    private JPanel btnPanel1;
    private JButton spostaToDobutton;
    private JButton scegliIMGButton;
    private JComboBox spostaJCB;
    private JPanel panelSposta;
    private Color c;

    private Controller controller;

    /**
     * Instantiates a new Crea to do.
     *
     * @param frame      the frame
     * @param controller the controller
     */
    public CreaToDo(JFrame frame, Controller controller) {
        this.frame = new JFrame(controller.getTitoloToDoCorrente());
        this.controller = controller;
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 600);
        this.frame.setLocationRelativeTo(null);
        this.initListeners();
        this.initText();
        this.frame.setVisible(true);
        this.titoloTextField.setText(controller.getTitoloToDoCorrente());
        //spostaJCB.setSelectedItem(TitoloBacheca.valueOf(controller.getTitoloBacheca()));
    }

    /**
     * Init listeners.
     */
    public void initListeners() {
        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (titoloTextField.getText().trim().isEmpty())
                    JOptionPane.showMessageDialog(frame, "TITOLO OBBLIGATORIO!");
                else {
                    controller.cambiaTitoloToDo(titoloTextField.getText());
                    controller.cambiaDescToDo(descTextField.getText());
                    if (!dataScadTextField.getText().isEmpty()){
                        boolean b = controller.cambiaDataScadToDo(dataScadTextField.getText());
                        if (!b)
                            JOptionPane.showMessageDialog(frame, "FORMATO DATA NON VALIDO, FORMATO CORRETTO: dd-MM-yyyy");
                        else if (!controller.checkData(dataScadTextField.getText()))
                            JOptionPane.showMessageDialog(frame, "DATA PRECEDENTE AD OGGI!!");
                    }
                    controller.cambiaBgColorToDo(c);
                    controller.cambiaURLToDo(urlTextField.getText());
                    initText();


                    // non ha senso che col conferma torna indietro pittusto aggiorniamo i dati
                }
            }

        });

        /*spostaJCB.addActionListener(new ActionListener() {@Override
        public void actionPerformed(ActionEvent e) {
            TitoloBacheca nuovaBacheca = (TitoloBacheca) spostaJCB.getSelectedItem();

            try {
                controller.spostaToDo(nuovaBacheca);

                JOptionPane.showMessageDialog(null, "ToDo spostato in: " + nuovaBacheca);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                spostaJCB.setSelectedItem(controller.getTitoloBacheca()); // ripristina selezione vecchia
            }
        }});*/

        scegliIMGButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(true);
                int r = fileChooser.showOpenDialog(frame);
                if (r == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String nomeFile = file.getName();
                    if (nomeFile.endsWith(".png") || nomeFile.endsWith(".jpg") || nomeFile.endsWith(".jpeg") || nomeFile.endsWith(".gif")){
                        ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                        Image img = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
                        imgJL.setIcon(new ImageIcon(img));
                        controller.setIMGToDo(img);
                    }else
                        JOptionPane.showMessageDialog(frame, "SELEZIONE UN FILE IMMAGINE (PNG, JPG, JPEG, GIF)");
                }
            }
        });

        imgJL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //fare metodo che apre una nuova interfaccia e fa visualizzare la foto nella sua interezza
            }
        });

        eliminaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                controller.cambiaTitoloToDo(titoloTextField.getText());
                controller.eliminaToDo(titoloTextField.getText());
                BachecaGUI bachecagui = new BachecaGUI(frame, controller);
                frame.dispose();
                frame.setVisible(false);
                bachecagui.getFrame().setVisible(true);
            }
        });

        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BachecaGUI b = new BachecaGUI(frame, controller);
                frame.setVisible(false);
                b.getFrame().setVisible(true);
            }
        });

        bgColorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(frame, "Scegli un colore", bgColorPanel.getBackground());
                if (color!=null)
                    c = color;
                else
                    c = controller.getBGColorToDo();
            }
        });

        setCompletoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean b = controller.getCompletoToDo();
                controller.setCompletoToDo(!b);
                if (b)
                    imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource("/notCompleted64.png")));
                else
                    imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource("/completed64.png")));
            }
        });

        spostaToDobutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelSposta = new JPanel();
                panelSposta.removeAll();
                panelSposta.add(new JLabel("Seleziona la bacheca in cui spostare il TODO"));
                panelSposta.add(spostaJCB);
                int result = JOptionPane.showConfirmDialog(frame, panelSposta, "Scegli la bacheca", JOptionPane.OK_CANCEL_OPTION);
                if (result == JOptionPane.OK_OPTION){
                    if (!controller.checkBacheca(spostaJCB.getSelectedItem().toString())){
                        JOptionPane.showMessageDialog(frame, "BACHECA INESISTENTE!");
                        return;
                    }
                    TitoloBacheca t = (TitoloBacheca) spostaJCB.getSelectedItem();
                    boolean b = controller.spostaToDo(t);
                    if (b){
                        JOptionPane.showMessageDialog(frame, "TODO SPOSTATO");
                        BachecaGUI bachecagui = new BachecaGUI(frame, controller);
                        bachecagui.getFrame().setVisible(true);
                        frame.dispose();
                    }
                    else
                        JOptionPane.showMessageDialog(frame, "TODO NON SPOSTATO CORRETTAMENTE");

                }
            }
        });
    }

    /**
     * Init text.
     */
    public void initText(){
        c = controller.getBGColorToDo();
        this.titoloTextField.setText(controller.getTitoloToDoCorrente());
        this.descTextField.setText(controller.getDescrizioneToDo());
        this.dataScadTextField.setText(controller.getDataScadToDo());
        this.urlTextField.setText(controller.getUrlToDo());

        if (controller.getCompletoToDo())
            imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource("/completed64.png")));
        else
            imgCompletatoJL.setIcon(new ImageIcon(getClass().getResource("/notCompleted64.png")));

        if (controller.getIMGToDo()!=null)
            imgJL.setIcon(new ImageIcon(controller.getIMGToDo()));
        else
            imgJL.setIcon(new ImageIcon(getClass().getResource("/no-image64.png")));

        spostaJCB = new JComboBox();
        if (controller.getTitoloBacheca().equals(TitoloBacheca.UNIVERSITA.toString())){
            spostaJCB.addItem(TitoloBacheca.LAVORO);
            spostaJCB.addItem(TitoloBacheca.TEMPO_LIBERO);
        }
        else if (controller.getTitoloBacheca().equals(TitoloBacheca.LAVORO.toString())){
            spostaJCB.addItem(TitoloBacheca.UNIVERSITA);
            spostaJCB.addItem(TitoloBacheca.TEMPO_LIBERO);
        }else{
            spostaJCB.addItem(TitoloBacheca.UNIVERSITA);
            spostaJCB.addItem(TitoloBacheca.LAVORO);
        }
    }


    /**
     * Gets frame.
     *
     * @return the frame
     */
    public JFrame getFrame() {
        return frame;
    }
}
