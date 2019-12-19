package pinball;

public enum BodyType {
    FLIPPER("FLIPPER"),
    WALL("WALL"),
    BOTTOM_CORNER("BOTTOM_CORNER"),
    TOP_CORNER("TOP_CORNER"),
    BALL("BALL"),
    FLOOR ("FLOOR");




    private String bodyName;

    private BodyType(String bodyName) {
        this.bodyName = bodyName;
    }

    public String getBodyName() {
        return bodyName;
    }
}
