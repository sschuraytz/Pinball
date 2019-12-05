import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PinballFrame extends JFrame {
    PinballKeyListeners pinballKeyListeners;
    PinballController pinballController = new PinballController();

    public PinballFrame (){
        setTitle("Pinball");
        setSize(600,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pinballKeyListeners = new PinballKeyListeners(pinballController);
        this.addKeyListener(pinballKeyListeners);
        JPanel root = new JPanel();
        PinballComponent pinballComponent = new PinballComponent();
        root.setLayout(new BorderLayout());
        root.add(pinballComponent, BorderLayout.CENTER);
        setContentPane(root);

    }
}

