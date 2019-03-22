package mouse;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RightAndLeftThumbButtons extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                RightAndLeftThumbButtons ex = new RightAndLeftThumbButtons();
                ex.setVisible(true);
            }
        });
    }

    public RightAndLeftThumbButtons() {
        setTitle("Simple example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int button = e.getButton();
                int MaksForButton4 = MouseEvent.getMaskForButton(4);
                System.out.println("mask for button 4: " + MaksForButton4);
                System.out.println("mask for button 1: " + MouseEvent.BUTTON1_MASK);
                System.out.println("actual button    : " + button);
                if (button == MaksForButton4) {
                    System.out.println("gotcha with " + MaksForButton4);
                }
                System.out.println("mouse clicked");
            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("mouse pressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("mouse released");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("entered");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("exited");
            }
        });
    }
}
