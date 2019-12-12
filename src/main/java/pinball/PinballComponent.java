package pinball;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import javax.swing.JComponent;
import java.awt.*;

public class PinballComponent extends JComponent {

    private long time;

    private static final float WIDTH = 1000;
    private static final float HEIGHT = 500;

    private static final float CORNER_LENGTH = 175;
    private static final float BASE_LENGTH = 350;
    private static final float FLIPPER_LENGTH = 120;

    private static final float BOX_TO_SCREEN = 10f;
    private static final float SCREEN_TO_BOX = 1f / BOX_TO_SCREEN;

    private final int radius = 30;

    private final World world;
    private final Body bottom, right, left, top, divider;
    private final Body topRightCorner
            // , topLeftCorner, bottomRightBase, bottomLeftBase
            ;
    // private final Body rightFlipper, leftFlipper;
    // private final Body ball;
    private final Renderer renderer;

    PinballComponent()
    {
        world = new World(new Vector2(0, 9.8f), false);
        renderer = new Renderer(world, BOX_TO_SCREEN);

        //set up all horizontal and vertical lines
        bottom = createWall(100f * SCREEN_TO_BOX, (HEIGHT + 100) * SCREEN_TO_BOX, WIDTH * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX);
        top = createWall(100f * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, WIDTH * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX);
        left = createWall(100f * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX, HEIGHT * SCREEN_TO_BOX);
        right = createWall((WIDTH + 100) * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX, HEIGHT * SCREEN_TO_BOX) ;
        divider = createWall(1025 * SCREEN_TO_BOX, 300f * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX, (HEIGHT - 200f) * SCREEN_TO_BOX);

        //set up all diagonal lines
        topRightCorner = createDiagonalLine(950f * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, CORNER_LENGTH * SCREEN_TO_BOX, 30);
        // topLeftCorner = createDiagonalLine(250f * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, CORNER_LENGTH * SCREEN_TO_BOX, 150);
        // bottomRightBase = createDiagonalLine(1025 * SCREEN_TO_BOX, 1230f * SCREEN_TO_BOX, BASE_LENGTH * SCREEN_TO_BOX, 145);
        // bottomLeftBase = createDiagonalLine(100f * SCREEN_TO_BOX, 1230f * SCREEN_TO_BOX, BASE_LENGTH * SCREEN_TO_BOX, 35);

        //set up flippers (static for now, but soon to be dynamic and jointed)
        // rightFlipper = createDiagonalLine(735 * SCREEN_TO_BOX, 1435 * SCREEN_TO_BOX, FLIPPER_LENGTH * SCREEN_TO_BOX, 145);
        // leftFlipper = createDiagonalLine(390 * SCREEN_TO_BOX, 1435 * SCREEN_TO_BOX, FLIPPER_LENGTH * SCREEN_TO_BOX, 35);

        //cue the ball
        // ball = createBall(1060 * SCREEN_TO_BOX, 1565 * SCREEN_TO_BOX, radius * SCREEN_TO_BOX);
    }

    private Body createWall(float vX, float vY, float length, float height)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(vX, vY));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body wall = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(length, height);
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1;
        wall.createFixture(fixtureDef);
        return wall;
    }

    //This method is extremely similar to createWall(). It should probably be refactored into one thing.
    private Body createDiagonalLine(float vX, float vY, float length, int angle)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(vX, vY));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.angle = angle * (MathUtils.PI/180);
        Body line = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(length, 1);
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1;
        line.createFixture(fixtureDef);
        return line;
    }

    private Body createBall(float vX, float vY, float radius)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(vX, vY));
        Body ball = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius);
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1;
        ball.createFixture(fixtureDef);

        return ball;
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        long currentTime = System.currentTimeMillis();
        world.step((currentTime - time)/1000f, 6, 2);
        time = currentTime;

        renderer.render((Graphics2D) graphics);

        repaint();
    }
}
