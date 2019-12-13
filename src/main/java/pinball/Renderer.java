package pinball;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import java.awt.*;


public class Renderer {

    private final World world;
    private final float BOX2D_TO_SCREEN;

    public Renderer(World world, float box2DToScreenRatio) {
        this.world = world;
        BOX2D_TO_SCREEN = box2DToScreenRatio;
    }

    /**
     * render all bodies in the world
     * @param graphics2D
     */
    public void render(Graphics2D graphics2D) {
        Array<Body> bodies = new Array<Body>();
        world.getBodies(bodies);
        for (Body body : bodies) {
            render(graphics2D, body);
        }
    }

    private void render(Graphics2D graphics2D, Body body) {
        Vector2 position = body.getPosition();
        Shape shape = body.getFixtureList().get(0).getShape();
        Shape.Type type = shape.getType();
        float angle = body.getAngle();
        switch (type) {
            case Circle:
                renderCircle(graphics2D, position, (int) shape.getRadius());
                break;
            case Polygon:
                renderPolygon(graphics2D, (PolygonShape) shape, position, angle);
                break;
        }
    }

    /**
     * @param graphics2D
     * @param position the center of the circle
     * @param radius
     */
    private void renderCircle(Graphics2D graphics2D, Vector2 position, int radius) {
        int screenRadius = Math.round(radius * BOX2D_TO_SCREEN);
        int diameter = screenRadius * 2;
        graphics2D.fillOval(Math.round(position.x * BOX2D_TO_SCREEN) - screenRadius,
                Math.round(position.y * BOX2D_TO_SCREEN) - screenRadius,
                diameter,
                diameter);
    }

    /**
     * @param graphics2D
     * @param polygon the polygon to draw
     * @param position the center point of the polygon
     * @param angle the angle of rotation
     */
    private void renderPolygon(Graphics2D graphics2D, PolygonShape polygon, Vector2 position, float angle) {
        int vertices = polygon.getVertexCount();

        if (vertices > 0) {
            int[] xCoordinates = new int[vertices];
            int[] yCoordinates = new int[vertices];
            Vector2 vector = new Vector2();
            float centerX = position.x * BOX2D_TO_SCREEN;
            float centerY = position.y * BOX2D_TO_SCREEN;

            for (int vertex = 0; vertex < vertices; vertex++) {
                polygon.getVertex(vertex, vector);
                // vector.x = distance from center of polygon to side of polygon
                // vector.y = distance from center of polygon to top/bottom of polygon
                xCoordinates[vertex] = Math.round(vector.x * BOX2D_TO_SCREEN);
                yCoordinates[vertex] = Math.round(vector.y * BOX2D_TO_SCREEN);
            }

            graphics2D.translate(centerX, centerY);
            graphics2D.rotate(angle);
            graphics2D.drawPolygon(xCoordinates, yCoordinates, vertices);
            graphics2D.rotate(-angle);
            graphics2D.translate(-centerX, -centerY);
        }
    }
}