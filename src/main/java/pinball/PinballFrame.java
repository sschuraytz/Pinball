package pinball;

import javax.swing.*;
import java.awt.*;

public class PinballFrame extends JFrame {
    PinballComponent pinballComponent = new PinballComponent();
    PinballKeyListeners pinballKeyListeners;
    PinballController pinballController = new PinballController(pinballComponent);

    private PinballFrame()
    {
        setSize(1200, 1750);
        setTitle("Pinball");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pinballKeyListeners = new PinballKeyListeners(pinballController);
        this.addKeyListener(pinballKeyListeners);

        setLayout(new BorderLayout());
        add(pinballComponent, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        new PinballFrame().setVisible(true);
    }
}
