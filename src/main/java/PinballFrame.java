import javax.swing.*;
import java.awt.*;

public class PinballFrame extends JFrame {

    public PinballFrame (){

        setTitle("Pinball");
        setSize(600,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel root = new JPanel();
        PinballComponent pinballComponent = new PinballComponent();
        root.setLayout(new BorderLayout());
        root.add(pinballComponent, BorderLayout.CENTER);
        setContentPane(root);
    }
}
