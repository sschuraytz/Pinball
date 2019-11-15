package pinball;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import javax.swing.JComponent;

public class PinballComponent extends JComponent {

    private final World world;

    public PinballComponent() {
        world = new World(new Vector2(0, 9.8f), false);
    }

}
