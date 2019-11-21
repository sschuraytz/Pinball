import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PinballComponent extends JComponent implements KeyListener {
    public PinballComponent (){
        addKeyListener(this);
    }


    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);


    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_RIGHT){
            rightFlipper();
        }
        else if (keyCode == KeyEvent.VK_LEFT){
            leftFlipper();
        }
        else if (keyCode == KeyEvent.VK_UP){
            releaseball();
        }

    }

    private void releaseball() {
    }

    private void leftFlipper() {
    }

    private void rightFlipper() {
    }

    public void keyReleased(KeyEvent e) {

    }
    
}
