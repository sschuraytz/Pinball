package test.schuraytz;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import pinball.Renderer;
import java.awt.Graphics2D;


public class RendererTest {

    @Test
    public void render_circle() {

        //given
        float BOX_TO_SCREEN = 10f;
        float SCREEN_TO_BOX = 1/BOX_TO_SCREEN;
        int radius = 10;
        World world = new World(new Vector2(0, 9.8f), false);
        Renderer renderer = new Renderer(world, BOX_TO_SCREEN);

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();

        CircleShape circle = new CircleShape();
        circle.setPosition(new Vector2(radius, radius));
        circle.setRadius(radius * SCREEN_TO_BOX);
        fixtureDef.shape = circle;
        
        Body circleBody = world.createBody(bodyDef);
        circleBody.createFixture(fixtureDef);

        //https://stackoverflow.com/questions/21069911/junit-testcase-for-a-method-with-graphics-parameter
        Graphics2D gMock = mock(Graphics2D.class);

        //when
        renderer.render(gMock);

        //then
        Mockito.verify(gMock).fillOval(Math.round(circleBody.getPosition().x * BOX_TO_SCREEN - radius),
                Math.round(circleBody.getPosition().y * BOX_TO_SCREEN - radius),
                radius * 2, radius * 2
                );



    }

}
