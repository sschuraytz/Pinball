package pinball;

public enum BodyType {
    BOTTOM_WALL("BOTTOM_WALL"),
    LEFT_WALL("LEFT_WALL"),
    RIGHT_WALL("RIGHT_WALL"),
    TOP_WALL("TOP_WALL"),
    DIVIDER_WALL("DIVIDER_WALL"),
    TOP_RIGHT_CORNER("TOP_RIGHT_CORNER"),
    TOP_LEFT_CORNER("TOP_LEFT_CORNER"),
    BOTTOM_RIGHT_CORNER("BOTTOM_RIGHT_CORNER"),
    BOTTOM_LEFT_CORNER("BOTTOM_LEFT_CORNER"),
    RIGHT_FLIPPER("RIGHT_FLIPPER"),
    LEFT_FLIPPER("LEFT_FLIPPER"),
    BALL("BALL");


    private String bodyName;

    private BodyType(String bodyName) {
        this.bodyName = bodyName;
    }

    public String getBodyName() {
        return bodyName;
    }
}
