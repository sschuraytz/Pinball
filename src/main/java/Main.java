import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;

public class Main {
    public static void main(String[] args) {
        Vec2 gravity = new Vec2(0.0f, -10.f);
        World world = new World(gravity);
        PinballFrame pinballFrame = new PinballFrame();
        pinballFrame.setVisible(true);
    }
}
