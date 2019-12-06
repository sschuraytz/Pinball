package test.schuraytz;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import pinball.Renderer;
import java.awt.Graphics2D;

public class RendererTest {

    @Test
    public void render() {
        //given
        // Can only mock a final class once manually activate the extension option -  https://antonioleiva.com/mockito-2-kotlin/
        World world = mock(World.class);
        Renderer renderer = new Renderer(world, 10);

        CircleShape circleShape = mock(CircleShape.class);
        Body body = mock(Body.class);
        Fixture fixture = mock(Fixture.class);
        Graphics2D graphics2D = mock(Graphics2D.class);
        Array<Fixture> fixtureArray = new Array<>();
        fixtureArray.add(fixture);

        doAnswer((invocation) -> {
            Array<Body> bodies = invocation.getArgument(0);
            bodies.add(body);
            return null;
        }).when(world).getBodies(any());

        doReturn(new Vector2(10, 10)).when(body).getPosition();
        doReturn((float) 8.0).when(circleShape).getRadius();
        doReturn(circleShape).when(fixture).getShape();
        doReturn(fixtureArray).when(body).getFixtureList();
        doReturn(Shape.Type.Circle).when(circleShape).getType();

        //when
        renderer.render(graphics2D);

        //then
        verify(graphics2D).fillOval(20, 20, 160, 160);
    }
}
