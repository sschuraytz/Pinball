import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PinballFrame extends JFrame  implements KeyListener {

    public PinballFrame (){

        setTitle("Pinball");
        setSize(600,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.addKeyListener(this);
        JPanel root = new JPanel();
        PinballComponent pinballComponent = new PinballComponent();
        root.setLayout(new BorderLayout());
        root.add(pinballComponent, BorderLayout.CENTER);
        setContentPane(root);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    private void releaseball() {
        System.out.println("release ball");

    }

    private void leftFlipper() {
        System.out.println("left flipper");
    }

    private void rightFlipper() {
        System.out.println("right flipper");

    }
    private void ballPressure() {
        System.out.println("ball pressed");

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_RIGHT){
            rightFlipper();
        }
        else if (keyCode == KeyEvent.VK_LEFT){
            leftFlipper();
        }
        else if (keyCode == KeyEvent.VK_UP){
            ballPressure();
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP){
            releaseball();
        }

    }
}
