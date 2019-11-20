package pinball;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import javax.swing.JComponent;
import java.awt.*;

public class PinballComponent extends JComponent {

    private static final float WIDTH = 1000;
    private static final float HEIGHT = 1400;

    private static final float BOX_TO_SCREEN = 10f;
    private static final float SCREEN_TO_BOX = 1f / BOX_TO_SCREEN;

    private final World world;
    private final Body bottom, right, left, top, divider;

    PinballComponent()
    {
        world = new World(new Vector2(0, 9.8f), false);
        bottom = createWall(100f * SCREEN_TO_BOX, (HEIGHT + 100) * SCREEN_TO_BOX, WIDTH * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX);
        top = createWall(100f * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, WIDTH * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX);
        left = createWall(100f * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX, HEIGHT * SCREEN_TO_BOX);
        right = createWall((WIDTH + 100) * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX, HEIGHT * SCREEN_TO_BOX) ;
        divider = createWall(WIDTH * SCREEN_TO_BOX, 300f * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX, (HEIGHT - 200f) * SCREEN_TO_BOX);
    }

    private Body createWall(float vX, float vY, float hX, float hY)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(vX, vY));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body wall = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(hX, hY);
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1;
        wall.createFixture(fixtureDef);
        return wall;
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        graphics.fillRect((int)(bottom.getPosition().x * BOX_TO_SCREEN),
                (int)(bottom.getPosition().y * BOX_TO_SCREEN),
                (int)WIDTH, 1);

        graphics.fillRect((int)(top.getPosition().x * BOX_TO_SCREEN),
                (int)(top.getPosition().y * BOX_TO_SCREEN),
                (int)WIDTH, 1);

        graphics.fillRect((int)(right.getPosition().x * BOX_TO_SCREEN),
                (int)(right.getPosition().y * BOX_TO_SCREEN),
                1, (int)HEIGHT);

        graphics.fillRect((int)(left.getPosition().x * BOX_TO_SCREEN),
                (int)(left.getPosition().y * BOX_TO_SCREEN),
                1, (int)HEIGHT);

        graphics.fillRect((int)(divider.getPosition().x * BOX_TO_SCREEN),
                (int)(divider.getPosition().y * BOX_TO_SCREEN),
                1, (int)HEIGHT - 200);
    }
}
