import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PinballKeyListeners implements KeyListener {
    PinballController pinballController;

    public PinballKeyListeners(PinballController controller) {
        pinballController = controller;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_RIGHT){
            pinballController.rightFlipper();
        }
        else if (keyCode == KeyEvent.VK_LEFT){
            pinballController.leftFlipper();
        }
        else if (keyCode == KeyEvent.VK_UP){
            pinballController.ballPressure();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP){
            pinballController.releaseball();
        }
    }
}
