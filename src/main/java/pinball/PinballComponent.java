package pinball;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import javax.swing.JComponent;
import java.awt.*;

public class PinballComponent extends JComponent {

    private long time;

    private static final float WIDTH = 1000;
    private static final float HEIGHT = 1500;

    private static final float CORNER_LENGTH = 175;
    private static final float BASE_LENGTH = 350;
    private static final float FLIPPER_LENGTH = 120;

    private static final float BOX_TO_SCREEN = 10f;
    private static final float SCREEN_TO_BOX = 1f / BOX_TO_SCREEN;

    private final int radius = 30;

    private final World world;
    private final Body bottom, right, left, top, divider;
    private final Body topRightCorner, topLeftCorner, bottomRightBase, bottomLeftBase;
    private final Body rightFlipper, leftFlipper;
    private final Body ball;

    PinballComponent()
    {
        world = new World(new Vector2(0, 9.8f), false);

        //set up all horizontal and vertical lines
        bottom = createWall(100f * SCREEN_TO_BOX, (HEIGHT + 100) * SCREEN_TO_BOX, WIDTH * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX);
        top = createWall(100f * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, WIDTH * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX);
        left = createWall(100f * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX, HEIGHT * SCREEN_TO_BOX);
        right = createWall((WIDTH + 100) * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX, HEIGHT * SCREEN_TO_BOX) ;
        divider = createWall(1025 * SCREEN_TO_BOX, 300f * SCREEN_TO_BOX, 1f * SCREEN_TO_BOX, (HEIGHT - 200f) * SCREEN_TO_BOX);

        //set up all diagonal lines
        topRightCorner = createDiagonalLine(950f * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, CORNER_LENGTH * SCREEN_TO_BOX, 30);
        topLeftCorner = createDiagonalLine(250f * SCREEN_TO_BOX, 100f * SCREEN_TO_BOX, CORNER_LENGTH * SCREEN_TO_BOX, 150);
        bottomRightBase = createDiagonalLine(1025 * SCREEN_TO_BOX, 1230f * SCREEN_TO_BOX, BASE_LENGTH * SCREEN_TO_BOX, 145);
        bottomLeftBase = createDiagonalLine(100f * SCREEN_TO_BOX, 1230f * SCREEN_TO_BOX, BASE_LENGTH * SCREEN_TO_BOX, 35);

        //set up flippers (static for now, but soon to be dynamic and jointed)
        rightFlipper = createDiagonalLine(735 * SCREEN_TO_BOX, 1435 * SCREEN_TO_BOX, FLIPPER_LENGTH * SCREEN_TO_BOX, 145);
        leftFlipper = createDiagonalLine(390 * SCREEN_TO_BOX, 1435 * SCREEN_TO_BOX, FLIPPER_LENGTH * SCREEN_TO_BOX, 35);

        //cue the ball
        ball = createBall(1060 * SCREEN_TO_BOX, 1565 * SCREEN_TO_BOX, radius * SCREEN_TO_BOX);
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

    //All this drawing code is severely repetitive and deserves to be refactored.
    //But hey, once the renderer comes around, we won't need it anyway.
    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);

        long currentTime = System.currentTimeMillis();
        world.step((currentTime - time)/1000f, 6, 2);
        time = currentTime;

        //draw all 'walls'
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

        //draw all diagonal lines
        //formula for the end-point of a diagonal line:
        //(x2, y2) = (x1 + length ⋅ cos(angle), y1 + length ⋅ sin(angle))
        Vector2 trcPosition = topRightCorner.getPosition();
        graphics.drawLine((int)(trcPosition.x * BOX_TO_SCREEN),
                (int)(trcPosition.y * BOX_TO_SCREEN),
                (int)((trcPosition.x * BOX_TO_SCREEN) + CORNER_LENGTH * Math.cos(topRightCorner.getAngle())),
                (int)((trcPosition.y * BOX_TO_SCREEN) + CORNER_LENGTH * Math.sin(topRightCorner.getAngle())));

        Vector2 tlcPosition = topLeftCorner.getPosition();
        graphics.drawLine((int)(tlcPosition.x * BOX_TO_SCREEN),
                (int)(tlcPosition.y * BOX_TO_SCREEN),
                (int)((tlcPosition.x * BOX_TO_SCREEN) + CORNER_LENGTH * Math.cos(topLeftCorner.getAngle())),
                (int)((tlcPosition.y * BOX_TO_SCREEN) + CORNER_LENGTH * Math.sin(topLeftCorner.getAngle())));

        Vector2 brbPos = bottomRightBase.getPosition();
        graphics.drawLine((int)(brbPos.x * BOX_TO_SCREEN),
                (int)(brbPos.y * BOX_TO_SCREEN),
                (int)((brbPos.x * BOX_TO_SCREEN) + BASE_LENGTH * Math.cos(bottomRightBase.getAngle())),
                (int)((brbPos.y * BOX_TO_SCREEN) + BASE_LENGTH * Math.sin(bottomRightBase.getAngle())));

        Vector2 blbPos = bottomLeftBase.getPosition();
        graphics.drawLine((int)(blbPos.x * BOX_TO_SCREEN),
                (int)(blbPos.y * BOX_TO_SCREEN),
                (int)((blbPos.x * BOX_TO_SCREEN) + BASE_LENGTH * Math.cos(bottomLeftBase.getAngle())),
                (int)((blbPos.y * BOX_TO_SCREEN) + BASE_LENGTH * Math.sin(bottomLeftBase.getAngle())));

        //draw flippers
        Vector2 rFlipPos = rightFlipper.getPosition();
        graphics.drawLine((int)(rFlipPos.x * BOX_TO_SCREEN),
                (int)(rFlipPos.y * BOX_TO_SCREEN),
                (int)((rFlipPos.x * BOX_TO_SCREEN) + FLIPPER_LENGTH * Math.cos(rightFlipper.getAngle())),
                (int)((rFlipPos.y * BOX_TO_SCREEN) + FLIPPER_LENGTH * Math.sin(rightFlipper.getAngle())));

        Vector2 lFlipPos = leftFlipper.getPosition();
        graphics.drawLine((int)(lFlipPos.x * BOX_TO_SCREEN),
                (int)(lFlipPos.y * BOX_TO_SCREEN),
                (int)((lFlipPos.x * BOX_TO_SCREEN) + FLIPPER_LENGTH * Math.cos(leftFlipper.getAngle())),
                (int)((lFlipPos.y * BOX_TO_SCREEN) + FLIPPER_LENGTH * Math.sin(leftFlipper.getAngle())));

        //draw ball
        graphics.fillOval((int) (ball.getPosition().x * BOX_TO_SCREEN - radius),
                (int) (ball.getPosition().y * BOX_TO_SCREEN - radius),
                radius * 2, radius * 2);

        repaint();
    }
}
