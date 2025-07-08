package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class VisualizzaImg {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel imgJL;
    private JButton tornaIndBtn;
    private Controller controller;

    public VisualizzaImg(Controller controller){
        this.frame = new JFrame(controller.getTitoloToDoCorrente());
        this.controller = controller;
        this.frame.setContentPane(mainPanel);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.pack();
        this.frame.setSize(800, 800);
        this.frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        initImg();
        initTornaIndBtn();
        this.frame.setVisible(true);
    }

    public void initImg(){

        final int MAX_WIDTH = 800;
        final int MAX_HEIGHT = 600;

        String pathImg = controller.getImgPath();
        Image img = controller.getIMGToDo();

        if (img==null){
            ImageIcon imageIcon = new ImageIcon(pathImg);
            img = imageIcon.getImage();
        }

        ImageIcon imageIcon = new ImageIcon(img);
        int imgWidth = imageIcon.getIconWidth();
        int imgHeight = imageIcon.getIconHeight();
        boolean ridimensionata = false;

        if (imgWidth > MAX_WIDTH || imgHeight > MAX_HEIGHT) {
            double scala = Math.min((double) MAX_WIDTH / imgWidth, (double) MAX_HEIGHT / imgHeight);
            int newWidth = (int) (imgWidth * scala);
            int newHeight = (int) (imgHeight * scala);
            Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImg);
            imgJL.setIcon(scaledIcon);
            ridimensionata = true;
        }
        else
            imgJL.setIcon(new ImageIcon(img));

        //imgJL.setIcon(new ImageIcon(img));

        if (ridimensionata)
            JOptionPane.showMessageDialog(frame, "L'immagine è stata ridimensionata per adattarsi alla finestra (max 800x600).", "Informazione", JOptionPane.INFORMATION_MESSAGE);
    }

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
     * Restituisce il frame.
     *
     * @return il frame
     */
    public JFrame getFrame() {
        return frame;
    }
}
