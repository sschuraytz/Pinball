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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

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
    private ArrayList<Body> bodies = new ArrayList<>();
    private BodiesDTO bodiesDTO;
    private final Renderer renderer;


    PinballComponent()
    {
        world = new World(new Vector2(0, 9.8f), false);
        renderer = new Renderer(world, BOX_TO_SCREEN);


        Gson gson = new Gson();

        try (Reader reader = new FileReader("bodies.json"))
        {
            bodiesDTO = gson.fromJson(reader, BodiesDTO.class);
            for(BodyDTO bodyDTO : bodiesDTO.getBodies())
            {
                bodies.add(createBody(bodyDTO));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private Body createBody(BodyDTO bodyDTO)
    {
        Body body = null;

        switch(bodyDTO.getShapeType())
        {
            case BOX:
                body = createWall(bodyDTO.getCoordinates(), bodyDTO.getLength(), bodyDTO.getHeight());
                break;
            case LINE:
                body = createDiagonalLine(bodyDTO.getCoordinates(), bodyDTO.getLength(), bodyDTO.getAngle());
                break;
            case CIRCLE:
                body = createBall(bodyDTO.getCoordinates(), bodyDTO.getRadius());
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

        renderer.render((Graphics2D) graphics);

        repaint();
    }
}
