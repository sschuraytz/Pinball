package pinball;

import com.badlogic.gdx.physics.box2d.*;

import java.awt.event.ContainerListener;

public class MyContactListener implements ContactListener {
    public MyContactListener(PinballComponent pinballComponent) {
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        //System.out.println("contact");

        if (fa == null || fb == null){
            return;
        }
        if (fa.getBody().getUserData() != null && fa.getBody().getUserData()==  "is bottom"  ){
            hitBottom();
        }
        if (fb.getBody().getUserData() != null && fb.getBody().getUserData()==  "is bottom"  ){
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
