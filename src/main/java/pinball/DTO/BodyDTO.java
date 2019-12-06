package pinball.DTO;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import pinball.BodyType;
import pinball.ShapeType;

public class BodyDTO {
     BodyType bodyType;
     ShapeType shapeType;
     float[] coordinates;
     Float length;
     Float height;
     Integer angle;
     Float radius;

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public void setCoordinates(float[] coordinates) {
        this.coordinates = coordinates;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public void setAngle(Integer angle) {
        this.angle = angle;
    }

    public void setRadius(Float radius) {
        this.radius = radius;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }

    public float[] getCoordinates() {
        return coordinates;
    }

    public Float getLength() {
        return length;
    }

    public Float getHeight() {
        return height;
    }

    public Integer getAngle() {
        return angle;
    }

    public Float getRadius() {
        return radius;
    }
}
