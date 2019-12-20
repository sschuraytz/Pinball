package pinball;

public enum BodyType {
    LEFT_FLIPPER("LEFT_FLIPPER"),
    RIGHT_FLIPPER("RIGHT_FLIPPER"),
    STOPPER("STOPPER"),
    WALL("WALL"),
    BOTTOM_CORNER("BOTTOM_CORNER"),
    TOP_CORNER("TOP_CORNER"),
    BALL("BALL");




    private String bodyName;

    private BodyType(String bodyName) {
        this.bodyName = bodyName;
    }

    public String getBodyName() {
        return bodyName;
    }
}
