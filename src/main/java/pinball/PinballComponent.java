package pinball;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.google.gson.Gson;
import pinball.DTO.BodiesDTO;
import pinball.DTO.BodyDTO;

import javax.swing.JComponent;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;


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
    private final Body topRightCorner, topLeftCorner, bottomRightCorner, bottomLeftCorner;
    private final Body rightFlipper, leftFlipper;
    private final Body ball;
    private ArrayList<Body> bodies = new ArrayList<>();


    PinballComponent() {
        world = new World(new Vector2(0, 9.8f), false);

        Gson gson = new Gson();

        try (Reader reader = new FileReader("bodies.json"))
        {
            BodiesDTO bodiesDTO = gson.fromJson(reader, BodiesDTO.class);
            for(BodyDTO bodyDTO : bodiesDTO.bodies)
            {
                bodies.add(createBody(bodyDTO));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //set up all horizontal and vertical boxes
        bottom = createWall(new float[] {100f, (HEIGHT + 100) },
                WIDTH,
                1f );
        top = createWall(new float[] {100f, 100f},
                WIDTH,
                1f);
        left = createWall(new float[] {100f, 100f},
                1f,
                HEIGHT);
        right = createWall(new float[]{(WIDTH + 100), 100f},
                1f,
                HEIGHT) ;
        divider = createWall(new float[] {1025, 300f},
                1f,
                (HEIGHT - 200f));

        //set up all diagonal lines
        topRightCorner = createDiagonalLine(new float[] {950f, 100f},
                CORNER_LENGTH,
                30);
        topLeftCorner = createDiagonalLine(new float[] {250f, 100f},
                CORNER_LENGTH,
                150);
        bottomRightCorner = createDiagonalLine(new float[] {1025, 1230f},
                BASE_LENGTH,
                145);
        bottomLeftCorner = createDiagonalLine(new float[] {100f, 1230f},
                BASE_LENGTH,
                35);

        //set up flippers (static for now, but soon to be dynamic and jointed)
        rightFlipper = createDiagonalLine(new float[]{735, 1435},
                FLIPPER_LENGTH,
                145);
        leftFlipper = createDiagonalLine(new float[] {390, 1435},
                FLIPPER_LENGTH,
                35);

        //cue the ball
        ball = createBall(new float[] {1060, 1565},
                radius);
    }
    private Body createBody(BodyDTO bodyDTO)
    {
        Body body = null;

        switch(bodyDTO.shapeType)
        {
            case BOX:
                body = createWall(bodyDTO.coordinates, bodyDTO.length, bodyDTO.height);
                break;
            case LINE:
                body = createDiagonalLine(bodyDTO.coordinates, bodyDTO.length, bodyDTO.angle);
                break;
            case CIRCLE:
                body = createBall(bodyDTO.coordinates, bodyDTO.radius);
                break;
        }
        return body;
    }

    private Body createWall(float[] coordinates, float length, float height)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(coordinates[0] * SCREEN_TO_BOX, coordinates[1] * SCREEN_TO_BOX));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body wall = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(length * SCREEN_TO_BOX, height * SCREEN_TO_BOX);
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1;
        wall.createFixture(fixtureDef);
        return wall;
    }

    private Body createDiagonalLine(float[] coordinates, float length, int angle)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(new Vector2(coordinates[0] * SCREEN_TO_BOX, coordinates[1] * SCREEN_TO_BOX));
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.angle = angle * (MathUtils.PI/180);
        Body line = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(length * SCREEN_TO_BOX, 1);
        fixtureDef.shape = shape;
        fixtureDef.restitution = 1;
        line.createFixture(fixtureDef);
        return line;
    }

    private Body createBall(float[] coordinates, float radius)
    {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(new Vector2(coordinates[0] * SCREEN_TO_BOX, coordinates[1] * SCREEN_TO_BOX));
        Body ball = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(radius * SCREEN_TO_BOX);
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

        Vector2 brbPos = bottomRightCorner.getPosition();
        graphics.drawLine((int)(brbPos.x * BOX_TO_SCREEN),
                (int)(brbPos.y * BOX_TO_SCREEN),
                (int)((brbPos.x * BOX_TO_SCREEN) + BASE_LENGTH * Math.cos(bottomRightCorner.getAngle())),
                (int)((brbPos.y * BOX_TO_SCREEN) + BASE_LENGTH * Math.sin(bottomRightCorner.getAngle())));

        Vector2 blbPos = bottomLeftCorner.getPosition();
        graphics.drawLine((int)(blbPos.x * BOX_TO_SCREEN),
                (int)(blbPos.y * BOX_TO_SCREEN),
                (int)((blbPos.x * BOX_TO_SCREEN) + BASE_LENGTH * Math.cos(bottomLeftCorner.getAngle())),
                (int)((blbPos.y * BOX_TO_SCREEN) + BASE_LENGTH * Math.sin(bottomLeftCorner.getAngle())));

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