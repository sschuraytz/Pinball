package pinball;

public class PinballController {

    private PinballComponent pinballComponent;

    protected PinballController(PinballComponent pinballComponent)
    {
        this.pinballComponent = pinballComponent;
    }

    protected void releaseball() {
        System.out.println("release ball");
    }
    protected void leftFlipper()
    {
        pinballComponent.changeFlipper(true);
        System.out.println("left flipper");
    }
    protected void rightFlipper() {
        pinballComponent.changeFlipper(false);
        System.out.println("right flipper");
    }
    protected void ballPressure() {
        System.out.println("ball pressed");
    }
}
