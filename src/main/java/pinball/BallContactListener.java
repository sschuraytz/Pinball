package pinball;

import com.badlogic.gdx.physics.box2d.*;
import pinball.DTO.BodyDTO;

import java.awt.event.ContainerListener;

import static pinball.BodyType.BALL;
import static pinball.BodyType.FLOOR;

public class BallContactListener implements ContactListener {
    public BallContactListener(PinballComponent pinballComponent) {
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if (fa == null || fb == null){
            return;
        }
        BodyDTO userDataA = (BodyDTO) fa.getBody().getUserData();
        BodyDTO userDataB = (BodyDTO) fb.getBody().getUserData();
        if ( userDataA.getBodyType() == FLOOR && userDataB.getBodyType() == BALL
         || userDataA.getBodyType() == BALL && userDataB.getBodyType() == FLOOR)
        {
            hitBottom();
        }
        return;
        
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
    
    public void hitBottom (){
        System.out.println("got out");
    }
}
