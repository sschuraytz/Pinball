package pinball;

import javax.swing.*;
import java.awt.*;

public class PinballFrame extends JFrame {
    JButton launchButton = new JButton("Launch!");
    PinballComponent pinballComponent = new PinballComponent(new JSONBodiesParser(), launchButton);
    PinballKeyListeners pinballKeyListeners;
    PinballController pinballController = new PinballController(pinballComponent);

    private PinballFrame()
    {
        setSize(1200, 800);
        setTitle("Pinball");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pinballKeyListeners = new PinballKeyListeners(pinballController);
        this.addKeyListener(pinballKeyListeners);

        setLayout(new BorderLayout());
        add(pinballComponent, BorderLayout.CENTER);
        add(launchButton, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        new PinballFrame().setVisible(true);
    }
}