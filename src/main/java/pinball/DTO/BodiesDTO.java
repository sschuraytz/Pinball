package pinball.DTO;

public class BodiesDTO {

    public BodyDTO[] getBodies()
    {
        return bodies;
    }

    public void setBodies(BodyDTO[] bodies) {
        this.bodies = bodies;
    }

    BodyDTO[] bodies;
}
