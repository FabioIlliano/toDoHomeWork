package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe responsabile della visualizzazione dell'immagine associata a un ToDo.
 * L'immagine viene caricata e ridimensionata se necessario per adattarsi alla finestra.
 * È possibile tornare alla schermata di gestione del ToDo.
 */
public class VisualizzaImg {
    private final JFrame frame;
    private JPanel mainPanel;
    private JLabel imgJL;
    private JButton tornaIndBtn;
    private final Controller controller;

    /**
     * Costruttore della classe.
     * Crea la finestra per visualizzare l'immagine del ToDo corrente.
     *
     * @param controller il controller usato per ottenere l'immagine del ToDo
     */
    public VisualizzaImg(Controller controller){
        this.frame = new JFrame(controller.getTitoloToDoCorrente());
        this.controller = controller;
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 800);
        this.frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        initImg();
        initTornaIndBtn();
        this.frame.setVisible(true);
    }

    /**
     * Inizializza e visualizza l'immagine del ToDo corrente.
     * Se l'immagine supera le dimensioni massime consentite (800x600), viene ridimensionata automaticamente.
     * Mostra un messaggio informativo in caso di ridimensionamento.
     */
    public void initImg(){

        final int MAX_WIDTH = 800;
        final int MAX_HEIGHT = 600;

        Image img = controller.getIMGToDo();

        if (img==null){
            JOptionPane.showMessageDialog(frame, "NESSUN IMMAGINE DISPONIBILE", "ERRORE", JOptionPane.ERROR_MESSAGE);
            tornaIndBtn.doClick();
            return;
        }

        int imgWidth = img.getWidth(null);
        int imgHeight = img.getHeight(null);
        boolean ridimensionata = false;

        if (imgWidth > MAX_WIDTH || imgHeight > MAX_HEIGHT) {
            double scala = Math.min((double) MAX_WIDTH / imgWidth, (double) MAX_HEIGHT / imgHeight);
            int newWidth = (int) (imgWidth * scala);
            int newHeight = (int) (imgHeight * scala);
            img = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ridimensionata = true;
        }

        imgJL.setIcon(new ImageIcon(img));


        if (ridimensionata)
            JOptionPane.showMessageDialog(frame, "L'immagine è stata ridimensionata per adattarsi alla finestra (max 800x600).", "Informazione", JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Inizializza il pulsante "Torna Indietro" che permette di tornare
     * alla schermata di gestione del ToDo corrente.
     */
    public void initTornaIndBtn(){
        tornaIndBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestisciToDo gestisciToDo = new GestisciToDo(controller);
                gestisciToDo.getFrame().setVisible(true);
                frame.dispose();
            }
        });
    }

    /**
     * Restituisce il frame principale della schermata iniziale.
     *
     * @return il {@code JFrame} della start page
     */
    public JFrame getFrame() {
        return frame;
    }
}
